package i.am.firestarterr.decodewriter;

import i.am.firestarterr.groundzeroinvestment.GroundZeroInvestment;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SapContentBinPrinter {

    private static final String filePath = "C:\\Users\\Gebbasoft\\Desktop\\workspace\\ground-zero-investment\\src\\main\\java\\i\\am\\firestarterr\\decodewriter\\sapfiles\\";

    public static void main(String[] args) {
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(GroundZeroInvestment.class.getClassLoader()
                    .getResource("sap_file_infos.txt")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert path != null;
        List<byte[]> bytes;
        try (Stream<String> stream = Files.lines(path)) {
            for (String line : stream.collect(Collectors.toList())) {
                String fileName = line.substring(line.indexOf("\"FILE_NAME\":\""), line.indexOf("\",\"MIMETYPE\":\"")).substring("\"FILE_NAME\":\"".length());
                bytes = getContentBins(filePath + fileName);

                File file = new File(filePath + fileName + "_request.json");
                FileOutputStream fos = new FileOutputStream(file);
                BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(fos));
                String contentBin = allToString(bytes);
                line = line.replace("CONTENTOBINTO123123", contentBin);
                fileWriter.write(line);
                fileWriter.close();
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getBytesOfInputPart(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        return IOUtils.toByteArray(inputStream);
    }

    private static List<byte[]> getContentBins(String fileName) throws IOException {
        byte[] inputPartBytes = getBytesOfInputPart(fileName);
        int lineSize = 1022;
        int bytesRead = 0;
        List<byte[]> contentBins = new ArrayList<>();
        while (true) {
            if (inputPartBytes.length - bytesRead < lineSize) {
                contentBins.add(Arrays.copyOfRange(inputPartBytes, bytesRead, inputPartBytes.length));
                break;
            }
            contentBins.add(Arrays.copyOfRange(inputPartBytes, bytesRead, bytesRead + lineSize));
            bytesRead = bytesRead + lineSize;
            if (bytesRead == inputPartBytes.length) {
                break;
            }
        }
        return contentBins;
    }

    public static String oneLineToString(byte[] byteaGuid) {
        return new String(Base64.getEncoder().encode(byteaGuid));
    }

    public static String allToString(List<byte[]> byteaList) {
        StringJoiner joiner = new StringJoiner("\",\"");
        for (byte[] bite : byteaList) {
            joiner.add(oneLineToString(bite));
        }
        return "\"" + joiner + "\"";
    }
}
