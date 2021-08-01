package i.am.firestarterr.excel;

import i.am.firestarterr.groundzeroinvestment.GroundZeroInvestment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComponentCodeInsertGenerator {

    public static void main(String[] args) {
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(GroundZeroInvestment.class.getClassLoader()
                    .getResource("componentCodes.txt")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert path != null;
        try (Stream<String> stream = Files.lines(path)) {
            for (String line : stream.collect(Collectors.toList())) {
                String[] split = line.split("\t");
                String code = split[0];
                String message = split[1];
                System.out.println("INSERT INTO component_code (id, code, language, value) VALUES ('" + UUID.randomUUID() +
                        "', '" + code + "', 'en', '" + message + "');");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
