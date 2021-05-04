package i.am.firestarterr.krediCalculator;

import java.util.UUID;

public class KrediCalculator {

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());

        double ziraatAylik = 2781.11;
        System.out.println(ziraatAylik * 48);
        System.out.println((ziraatAylik * 48 - 90000));

        System.out.println("-----------------");

        double finansAylik = 2879.51;
        System.out.println(finansAylik * 48);
        System.out.println((finansAylik * 48 - 90000));
        System.out.println(((finansAylik * 48 - 90000) * 80 / 100));


        double finansIhtiyac = 2879.51;
        System.out.println(finansIhtiyac * 48);
        System.out.println((finansIhtiyac * 48 - 90000));
        System.out.println(((finansIhtiyac * 48 - 90000) * 80 / 100));
    }
}
