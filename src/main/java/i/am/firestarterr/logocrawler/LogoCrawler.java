package i.am.firestarterr.logocrawler;

import i.am.firestarterr.excel.UserSunucuPivot;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogoCrawler {

    public static void main(String[] args) throws IOException {
        LogoCrawler crawler = new LogoCrawler();
        crawler.run();
    }

    public void run() throws IOException {
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(UserSunucuPivot.class.getClassLoader()
                    .getResource("companies.txt")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert path != null;
        try (Stream<String> stream = Files.lines(path)) {
            for (String line : stream.collect(Collectors.toList())) {
                System.out.println(line);
                if (line.endsWith(".")) {
                    line = line.substring(0, line.length() - 1);
                }
                String logoUrl = getFirstLogoUrl(line);
                URL url = new URL(logoUrl);
                try (InputStream in = new BufferedInputStream(url.openStream());
                     ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                    byte[] buf = new byte[1024];
                    int n;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    byte[] response = out.toByteArray();
                    try (FileOutputStream fos = new FileOutputStream("logos/" + line + ".jpg")) {
                        fos.write(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFirstLogoUrl(String key) {
        RestTemplate restTemplate = new RestTemplate();
        boolean found = false;
        while (!found) {
            String url = "https://autocomplete.clearbit.com/v1/companies/suggest?query=" + key;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            if (StringUtils.isNotBlank(response.getBody())) {
                System.out.println(response.getBody());
                found = true;
            }
        }
        return null;
    }

}

