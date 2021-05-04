package i.am.firestarterr.hepsiburada;

import java.util.HashMap;
import java.util.Map;

public class TaksitHesapla {
    public static void main(String[] args) {
        Map<Integer, Double> taksiteGoreTutar = new HashMap<>();
        taksiteGoreTutar.put(1, 4290.00);
        taksiteGoreTutar.put(2, 4366.36);
        taksiteGoreTutar.put(3, 4413.55);
        taksiteGoreTutar.put(4, 4429.85);
        taksiteGoreTutar.put(5, 4439.29);
        taksiteGoreTutar.put(9, 4676.10);
        taksiteGoreTutar.put(12, 4804.80);
        taksiteGoreTutar.put(15, 5072.93);
        taksiteGoreTutar.put(18, 5222.22);
        for (Map.Entry<Integer, Double> entry : taksiteGoreTutar.entrySet()) {
            double fark = entry.getValue() - taksiteGoreTutar.get(1);
            double aylikFark = fark / entry.getKey();
            System.out.println(entry.getKey() + " - " + fark + " - " + aylikFark);
        }
    }

//    public static void main(String[] args) {
//        String a1 = "V 1.7.3";
//        String a2 = "V 1.7.4";
//        String a3 = "V 1.8.4";
//        String a4 = "V 2.8.4";
//        System.out.println(a1.compareTo(a2));
//        System.out.println(a1.compareTo(a3));
//        System.out.println(a1.compareTo(a4));
//        System.out.println(a2.compareTo(a1));
//        System.out.println(a2.compareTo(a3));
//        System.out.println(a2.compareTo(a4));
//        System.out.println(a3.compareTo(a1));
//        System.out.println(a3.compareTo(a2));
//        System.out.println(a3.compareTo(a4));
//        System.out.println(a4.compareTo(a1));
//        System.out.println(a4.compareTo(a2));
//        System.out.println(a4.compareTo(a3));
//    }
}