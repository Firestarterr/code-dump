package i.am.firestarterr.anlik;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class Anlik {

    private static final String GET_URL = "http://localhost:8002/secure/schedulers_old.xhtml";

    public static void main(String[] args) {
        DecimalFormat paraFormatter = new DecimalFormat("####,###,###.00");
        double startWith = 250;
        double oran = 1.09d;
        for (int i = 0; i < 34; i++) {
            System.out.println("kredi:" + paraFormatter.format(startWith) +
                    " Ödemesi:" + paraFormatter.format(startWith * oran) +
                    " 12 Aylık taksit: " + paraFormatter.format(startWith * oran / 12) +
                    " Toplam Para:" + paraFormatter.format(310 + startWith));
            startWith = startWith + 10;
        }
        System.out.println("ehue");
    }

//    public static void main(String[] args) throws IOException {
//        Anlik anlik = new Anlik();
//        anlik.sendGETImg();
//        System.out.println("ehue");
//    }

    private void sendGET() throws IOException {
        URL url = new URL("https://logo.clearbit.com/testout.com");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setInstanceFollowRedirects(true);
        int status = con.getResponseCode();
        System.out.println(status);
    }

    private void sendGETImg() throws IOException {
        URL url = new URL("https://logo.clearbit.com/testout.com");
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();
        FileOutputStream fos = new FileOutputStream("out2.jpg");
        fos.write(response);
        fos.close();
    }

}
