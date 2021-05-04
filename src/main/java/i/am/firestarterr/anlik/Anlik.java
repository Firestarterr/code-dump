package i.am.firestarterr.anlik;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Anlik {

    private static final String GET_URL = "http://localhost:8002/secure/schedulers_old.xhtml";

    public static void main(String[] args) throws IOException {
        Anlik anlik = new Anlik();
        for (int i = 0; i < 300; i++) {
            try {
                anlik.sendGET();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("ehue");
    }

    private void sendGET() throws IOException {
        URL url = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setInstanceFollowRedirects(true);
        int status = con.getResponseCode();
        System.out.println(status);
    }

    //    public static void main(String[] args) {
//        double islem = 24007.47 - 2900d - 18501.52 + 2250;
//        System.out.println(islem);
//        double islem2 = 23357.47 - 2250d;
//        System.out.println(islem2);
//        double islem3 = 10046.92 + 14.41;
//        System.out.println(islem3);
//    }
//    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        list.add(1043000);
//        list.add(1310000);
//        list.add(1143000);
//        list.add(1320000);
//        list.add(1243000);
//        for (Integer integer : list) {
//            System.out.println(integer * 95 / 100);
//        }
//
//    }
}
//finans anlık kapama 24.007,47
//enpara güncel borç 3.829,37
//miles güncel borç 9.819,23
//enparadaki anlık para 16.196,14
//işbank nakit 54.436,85
//işbank ek hesap borcu 3,75
//işbank eft çiçek 980
//işbank kart borcu 657,25
//yapıkredi nakit 73,80
//adios güncel borç 2.660,37
//finans nakit 55,61
//finans anlık kapama 58.925,36