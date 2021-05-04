package i.am.firestarterr.byteutil;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UnsignedSignedByte {

    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

    public static void main(String[] args) throws IOException {
        String fileName = "C:\\Users\\Gebbasoft\\Desktop\\workspace\\ground-zero-investment\\src\\main\\java\\com\\firestarterr\\decodewriter\\stringfile";
        File file = new File(fileName);
        InputStream is = new FileInputStream(file);
        byte[] barr = IOUtils.toByteArray(is);
        StringBuilder str = new StringBuilder();
        for (byte b : barr) {
            str.append(str).append(unsignedToBytes(b));
        }
    }
}
