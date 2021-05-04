package i.am.firestarterr.scheduleranalysis;

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

public class DispatcherOrg {
    public static void main(String[] args) {
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(GroundZeroInvestment.class.getClassLoader()
                    .getResource("tech.csv")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert path != null;
        Map<String, Map<String, Map<String, Integer>>> dispatcherOrganizationSectionCountMap = new HashMap<>();
        try (Stream<String> stream = Files.lines(path)) {
            for (String line : stream.collect(Collectors.toList())) {
                String[] params = line.split(",");
                if (dispatcherOrganizationSectionCountMap.get(params[0]) == null) {
                    Map<String, Integer> sectionCountMap = new HashMap<>();
                    sectionCountMap.put(params[2], 1);
                    Map<String, Map<String, Integer>> organizationSectionCountMap = new HashMap<>();
                    organizationSectionCountMap.put(params[1], sectionCountMap);
                    dispatcherOrganizationSectionCountMap.put(params[0], organizationSectionCountMap);
                } else {
                    if (dispatcherOrganizationSectionCountMap.get(params[0]).get(params[1]) == null) {
                        Map<String, Integer> sectionCountMap = new HashMap<>();
                        sectionCountMap.put(params[2], 1);
                        Map<String, Map<String, Integer>> organizationSectionCountMap = dispatcherOrganizationSectionCountMap.get(params[0]);
                        organizationSectionCountMap.put(params[1], sectionCountMap);
                        dispatcherOrganizationSectionCountMap.put(params[0], organizationSectionCountMap);
                    } else {
                        Map<String, Integer> sectionCountMap = dispatcherOrganizationSectionCountMap.get(params[0]).get(params[1]);
                        if (sectionCountMap.get(params[2]) == null) {
                            sectionCountMap.put(params[2], 1);
                        } else {
                            Integer count = sectionCountMap.get(params[2]);
                            if (count == null) {
                                sectionCountMap.put(params[2], 1);
                            } else {
                                sectionCountMap.put(params[2], ++count);
                            }
                        }
                        dispatcherOrganizationSectionCountMap.get(params[0]).put(params[1], sectionCountMap);
                    }
                }
            }
            for (Map.Entry<String, Map<String, Map<String, Integer>>> stringMapEntry : dispatcherOrganizationSectionCountMap.entrySet()) {
                String dispatcher = stringMapEntry.getKey();
                for (Map.Entry<String, Map<String, Integer>> mapEntry : stringMapEntry.getValue().entrySet()) {
                    String org = mapEntry.getKey();
                    for (Map.Entry<String, Integer> stringIntegerEntry : mapEntry.getValue().entrySet()) {
                        System.out.println(dispatcher + "\t" + org + "\t" + stringIntegerEntry.getKey() + "\t" + stringIntegerEntry.getValue());
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }


}
