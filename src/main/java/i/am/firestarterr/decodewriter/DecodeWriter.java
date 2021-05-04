package i.am.firestarterr.decodewriter;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DecodeWriter {

//    public static void main(String[] args) throws IOException {
//        String fileName = "C:\\Users\\Gebbasoft\\Desktop\\workspace\\ground-zero-investment\\src\\main\\java\\com\\firestarterr\\decodewriter\\stringfile2";
//        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
//            String line = br.readLine();
//            byte[] decoded = Base64.getDecoder().decode(line);
//            FileOutputStream fileOuputStream = new FileOutputStream("C:\\Users\\Gebbasoft\\Desktop\\dl.jpg");
//            fileOuputStream.write(decoded);
//        }
//    }

//    public static void main(String[] args) {
//        System.out.println(ehue.getBytes());
//        String encoded = Base64.encodeBase64String(ehue.getBytes());
//        System.out.println(encoded);
//        System.out.println(Base64.decodeBase64(encoded));
//    }

//    public static void main(String[] args) {
//        String fileName = "C:\\Users\\Gebbasoft\\Desktop\\workspace\\ground-zero-investment\\src\\main\\java\\com\\firestarterr\\decodewriter\\ehue.jpg";
//        File file = new File(fileName);
//        byte[] encoded = new byte[0];
//        try {
//            encoded = Base64.encodeBase64(Files.readAllBytes(file.toPath()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String enc = new String(encoded, StandardCharsets.UTF_8);
//        System.out.println(enc);
//        FileOutputStream fileOuputStream = null;
//        try {
//            fileOuputStream = new FileOutputStream("C:\\Users\\Gebbasoft\\Desktop\\ehue.jpg");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            fileOuputStream.write(Base64.decodeBase64(enc));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\Gebbasoft\\Desktop\\workspace\\ground-zero-investment\\src\\main\\java\\com\\firestarterr\\decodewriter\\download.jpg";
        File file = new File(fileName);
        InputStream is = new FileInputStream(file);
        byte[] decoded = Base64.getEncoder().encode(IOUtils.toByteArray(is));
        System.out.println(new String(decoded, StandardCharsets.UTF_8));
    }
}
