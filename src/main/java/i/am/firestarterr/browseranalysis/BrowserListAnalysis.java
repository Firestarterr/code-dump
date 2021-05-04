package i.am.firestarterr.browseranalysis;

import com.blueconic.browscap.Capabilities;
import com.blueconic.browscap.ParseException;
import com.blueconic.browscap.UserAgentParser;
import com.blueconic.browscap.UserAgentService;
import i.am.firestarterr.groundzeroinvestment.GroundZeroInvestment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrowserListAnalysis {

    public static void main(String[] args) {
        Path path = null;
        try {
//            command :
//            more /var/log/weking/portal.log | grep ' Browser: ' >> browserlist.txt
            path = Paths.get(Objects.requireNonNull(GroundZeroInvestment.class.getClassLoader()
                    .getResource("browserlist.txt")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert path != null;
        Map<String, String> userBrowserMap = new HashMap<>();
        try (Stream<String> stream = Files.lines(path)) {
            for (String line : stream.collect(Collectors.toList())) {
                String date = line.split(" ")[0];
                line = line.substring(line.indexOf(" - User: ")).substring(9);
                String user = line.split(" ")[0];
                if (userBrowserMap.containsKey(user)) {
                    continue;
                }
                String browser = line.substring(line.indexOf(" Browser: ")).substring(10);
                userBrowserMap.put(user, date + " ---- " + browser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            final UserAgentParser parser = new UserAgentService().loadParser();
            for (Map.Entry<String, String> entry : userBrowserMap.entrySet()) {
                final String date = entry.getValue().split(" ---- ")[0];
                final String userAgent = entry.getValue().substring(entry.getValue().indexOf("---- ")).substring(5);
                final Capabilities capabilities = parser.parse(userAgent);
                final String browser = capabilities.getBrowser();
                final String browserType = capabilities.getBrowserType();
                final String browserMajorVersion = capabilities.getBrowserMajorVersion();
                final String deviceType = capabilities.getDeviceType();
                final String platform = capabilities.getPlatform();
                final String platformVersion = capabilities.getPlatformVersion();
                if ((browser.equals("Chrome") && Integer.parseInt(browserMajorVersion) == 79)
                        || (browser.equals("Chrome") && Integer.parseInt(browserMajorVersion) == 0)
                        || (browser.equals("IE") && Integer.parseInt(browserMajorVersion) == 11)) {
                    continue;
                }
                System.out.println(
                        date + " - " +
                                entry.getKey() + " - " +
                                browser + " - " +
                                browserMajorVersion + " - " +
                                platform + " - " +
                                platformVersion
                );
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}
