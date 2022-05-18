package i.am.firestarterr.metadataReport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import i.am.firestarterr.groundzeroinvestment.GroundZeroInvestment;
import i.am.firestarterr.metadataReport.model.AttachmentMetadataJsonDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetadataSMUReportProducer {

    public static ObjectMapper getObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    public static void main(String[] args) {
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(GroundZeroInvestment.class.getClassLoader()
                    .getResource("select_metadatajson_from_attachment_meta.txt")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert path != null;
        Map<String, AttachmentMetadataJsonDto> latestByWorkOrder = new HashMap<>();
        Set<String> ignoreOrders = new HashSet<>();
        try (Stream<String> stream = Files.lines(path)) {
            for (String line : stream.collect(Collectors.toList())) {
                try {
                    AttachmentMetadataJsonDto parsed = getObjectMapper().readValue(line, AttachmentMetadataJsonDto.class);
                    if (StringUtils.isBlank(parsed.getWorkOrderNo())
                            || StringUtils.isBlank(parsed.getWorkHours())
                            || StringUtils.isBlank(parsed.getFormTime())) {
                        continue;
                    }
                    if (!parsed.getFormTime().startsWith("2022-")) {
                        ignoreOrders.add(parsed.getWorkOrderNo());
                    } else {
                        if (latestByWorkOrder.get(parsed.getWorkOrderNo()) != null) {
                            AttachmentMetadataJsonDto old = latestByWorkOrder.get(parsed.getWorkOrderNo());
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            if (sdf.parse(parsed.getFormTime()).after(sdf.parse(old.getFormTime()))) {
                                latestByWorkOrder.put(parsed.getWorkOrderNo(), parsed);
                            }
                        } else {
                            latestByWorkOrder.put(parsed.getWorkOrderNo(), parsed);
                        }
                    }
                } catch (Exception e) {
//                    System.out.println("unparseable value found--" + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done" + latestByWorkOrder.size());
        try {
            File fout = new File("metadata_report.csv");
            FileOutputStream fos = new FileOutputStream(fout);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (Map.Entry<String, AttachmentMetadataJsonDto> entry : latestByWorkOrder.entrySet()) {
                AttachmentMetadataJsonDto val = entry.getValue();
                String techName = val.getSims() != null && val.getSims().getTechnician() != null ? val.getSims().getTechnician() :
                        !CollectionUtils.isEmpty(val.getTechnicianList()) ? val.getTechnicianList().get(0) : "BULUNAMADI";
                bw.write(val.getWorkOrderNo() + "\t" + val.getFormTime() + "\t"
                        + techName + "\t" + val.getWorkHours());
                bw.newLine();
            }

            bw.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
