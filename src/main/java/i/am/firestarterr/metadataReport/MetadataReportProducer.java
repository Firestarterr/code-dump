package i.am.firestarterr.metadataReport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import i.am.firestarterr.groundzeroinvestment.GroundZeroInvestment;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MetadataReportProducer {

    public static ObjectMapper getObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    //data picker
//    select w.ordernumber, t.fullname, o.displayname, am.metadatajson
//    from attachment_metadata am
//    left join scheduler s on am.scheduler_id = s.id
//    left join technician t on am.technician_bpno = t.bpno
//    left join order_adm_kalem oak on s.orderadmkalem_id = oak.id
//    left join workorder w on oak.workorder_ordernumber = w.ordernumber
//    left join organization o on w.organization_orgunit = o.orgunit
//    order by s.end_date desc ;

    public static void main(String[] args) {
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(GroundZeroInvestment.class.getClassLoader()
                    .getResource("metadata.txt")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert path != null;
        String seperator = ",";
        ArrayList<ArrayList<LinkedHashMap<String, Object>>> metaDataList = new ArrayList<>();
        Set<String> entryKeySet = new HashSet<>();
        Set<String> typeSet = new HashSet<>();
        List<String> metaDataInfoList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(path)) {
            for (String line : stream.collect(Collectors.toList())) {
                int i = line.indexOf(',', 1 + line.indexOf(',', 1 + line.indexOf(',')));

                String metaDataInfo = line.substring(0, i);
                String jsonPart = line.substring(i + 1);
                jsonPart = jsonPart.substring(1, jsonPart.length() - 1);
                metaDataInfoList.add(metaDataInfo);
                jsonPart = jsonPart.replace("\"\"", "\"");
                ArrayList<LinkedHashMap<String, Object>> parsed = getObjectMapper().readValue(jsonPart, ArrayList.class);
                for (LinkedHashMap<String, Object> entry : parsed) {
                    entryKeySet.addAll(entry.keySet());
                    if (entry.get("type") != null || entry.get("type") != "") {
                        typeSet.add(String.valueOf(entry.get("type")));
                    }
                }
                metaDataList.add(parsed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> headerSet = new HashSet<>();
        //gather headers
        for (ArrayList<LinkedHashMap<String, Object>> metaData : metaDataList) {
            for (LinkedHashMap<String, Object> meta : metaData) {
                String type = String.valueOf(meta.get("type"));
                for (String key : meta.keySet()) {
                    headerSet.add(type + "." + key);
                    if (meta.get(key) instanceof LinkedHashMap) {
                        LinkedHashMap<String, Object> subMeta = (LinkedHashMap<String, Object>) meta.get(key);
                        for (String sub : subMeta.keySet()) {
                            headerSet.add(type + "." + key + "." + sub);
                        }
                    }
                }
            }
        }
        List<String> sortedHeaders = new ArrayList<>(headerSet);
        sortedHeaders.sort(String::compareTo);
        //fill datas
        List<String> lines = new ArrayList<>();
        StringBuilder headerLineBuilder = new StringBuilder();
        headerLineBuilder.append("Work Order Number").append(seperator);
        headerLineBuilder.append("Technician").append(seperator);
        headerLineBuilder.append("Organization").append(seperator);
        for (String header : sortedHeaders) {
            headerLineBuilder.append(header).append(seperator);
        }
        String headerLine = headerLineBuilder.toString();
        lines.add(headerLine.substring(0, headerLine.length() - 1));
        for (int i = 0; i < metaDataList.size(); i++) {
            ArrayList<LinkedHashMap<String, Object>> metaData = metaDataList.get(i);
            StringBuilder lineBuilder = new StringBuilder();
            lineBuilder.append(metaDataInfoList.get(i));
            for (String sortedHeader : sortedHeaders) {
                String[] headerCount = sortedHeader.split("\\.");
                String type = headerCount[0];
                for (LinkedHashMap<String, Object> data : metaData) {
                    if (String.valueOf(data.get("type")).equals(type)) {
                        if (data.get(headerCount[1]) != null) {
                            if (headerCount.length == 2) {
                                lineBuilder.append(data.get(headerCount[1])).append(seperator);
                            } else if (headerCount.length == 3) {
                                LinkedHashMap<String, Object> subData;
                                try {
                                    subData = (LinkedHashMap<String, Object>) data.get(headerCount[1]);
                                    if (subData.get(headerCount[2]) != null) {
                                        lineBuilder.append(subData.get(headerCount[2])).append(seperator);
                                    } else {
                                        lineBuilder.append(seperator);
                                    }
                                } catch (Exception e) {
                                    lineBuilder.append(seperator);
                                }
                            } else {
                                lineBuilder.append(seperator);
                            }
                        }
                    }

                }
            }
            if (lineBuilder.toString().equals("")) {
                continue;
            }
            String line = lineBuilder.toString();
            lines.add(line.substring(0, line.length() - 1));
        }
        try {
            File fout = new File("metadata_report.csv");
            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

            for (String line : lines) {
                bw.write(line);
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
