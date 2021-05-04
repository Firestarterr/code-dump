package i.am.firestarterr.saveCalculator;

import i.am.firestarterr.groundzeroinvestment.GroundZeroInvestment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SaveTimeCalculator {

    public static void main(String[] args) {
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(GroundZeroInvestment.class.getClassLoader()
                    .getResource("saveTimes.txt")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert path != null;
        double time = 0d;
        int i = 0;
        try (Stream<String> stream = Files.lines(path)) {
            for (String line : stream.collect(Collectors.toList())) {
                String[] params = line.split(".save takes ");
                Double individual = new Double(params[1].substring(0, params[1].length() - 3));
                time += individual;
                i++;
            }
            System.out.println("Ortalama=" + (time / i));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
