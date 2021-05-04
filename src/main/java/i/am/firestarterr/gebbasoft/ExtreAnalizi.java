package i.am.firestarterr.gebbasoft;

import i.am.firestarterr.groundzeroinvestment.GroundZeroInvestment;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class ExtreAnalizi {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(GroundZeroInvestment.class.getClassLoader()
                    .getResource("sirketimYillik.2019")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert path != null;
        List<LogEntry> entries = new ArrayList<>();
        Map<Type, List<LogEntry>> byTypeMap = new HashMap<>();
        Map<DetailType, List<LogEntry>> byDetailTypeMap = new HashMap<>();
        try (Stream<String> stream = Files.lines(path)) {
            for (String line : stream.collect(Collectors.toList())) {
                line = line.replaceAll("\t\t", "\t");
                line = line.replaceAll("\t\t", "\t");
                line = line.replaceAll("\t\t", "\t");
                String[] params = line.split("\t");
                LogEntry entry = new LogEntry(params);
                entries.add(entry);
                byTypeMap.computeIfAbsent(entry.getType(), k -> new ArrayList<>(Collections.singletonList(entry)));
                byTypeMap.get(entry.getType()).add(entry);
                byDetailTypeMap.computeIfAbsent(entry.getDetailType(), k -> new ArrayList<>(Collections.singletonList(entry)));
                byDetailTypeMap.get(entry.getDetailType()).add(entry);
            }

            for (Type type : byTypeMap.keySet()) {
                double totalOfType = 0d;
                for (LogEntry entry : byTypeMap.get(type)) {
                    totalOfType += entry.getAmount();
                }
                System.out.println(type.getName() + ": " + totalOfType);
            }

            System.out.println("-------------------------");
            for (DetailType type : byDetailTypeMap.keySet()) {
                double totalOfType = 0d;
                for (LogEntry entry : byDetailTypeMap.get(type)) {
                    totalOfType += entry.getAmount();
                }
                System.out.println(type.getName() + ": " + totalOfType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Getter
    enum Type {
        HARCAMA("Encard Şirketim Harcaması"),
        FAIZ("Faiz Gideri"),
        GELEN("Gelen Transfer"),
        GIDEN("Giden Transfer"),
        ODEME("Ödeme"),
        PARA_CEKME("Para Çekme"),
        VERGI("Vergi Kesintisi"),
        NOT_DEFINED("ehue");

        final String name;

        Type(String name) {
            this.name = name;
        }

        private static Type getByName(String name) {
            for (Type value : values()) {
                if (value.getName().equals(name)) {
                    return value;
                }
            }
            return NOT_DEFINED;
        }
    }

    @Getter
    enum DetailType {
        BAGKUR("4B Bağkur Prim", false, true),
        GELIR_VERGISI("GELİR VERGİSİ", false, true),
        GELIR_GECICI_VERGISI("GELİR GEÇİCİ VERGİ", false, true),
        KDV("KATMA DEĞER VERGİSİ", false, true),
        MUHASEBE("Şenay Coşkuner", false, true),
        ERICSSON("PEGASUS DANIŞMANLIK", true, false),
        CMV("CMV TEKNOLOJİ", true, false),
        OTHER("ehue", false, false);

        final String name;
        final boolean gelir;
        final boolean isletmeGideri;

        DetailType(String name, boolean gelir, boolean isletmeGideri) {
            this.name = name;
            this.gelir = gelir;
            this.isletmeGideri = isletmeGideri;
        }

        private static DetailType getByName(String name) {
            for (DetailType value : values()) {
                if (name != null && !name.isEmpty()
                        && name.contains(value.getName())) {
                    return value;
                }
            }
            return OTHER;
        }
    }

    @Getter
    @Setter
    static class LogEntry {
        Date date;
        Type type;
        DetailType detailType;
        String detail;
        double amount;
        double bakiye;

        public LogEntry(String[] line) {
            try {
                this.date = sdf.parse(line[0]);
            } catch (ParseException e) {
                this.date = new Date();
            }
            this.type = Type.getByName(line[1]);
            this.detailType = DetailType.getByName(line[2]);
            this.detail = line[2];
            this.amount = Double.parseDouble(line[3].replaceAll(",", ""));
            this.bakiye = Double.parseDouble(line[4].replaceAll(",", ""));
        }
    }
}
