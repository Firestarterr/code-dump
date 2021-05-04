package i.am.firestarterr.groundzeroinvestment.service;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;

@Getter
public class EnparaLive {

    private double usdSell;
    private double usdBuy;
    private double eurSell;
    private double eurBuy;
    private double gauSell;
    private double gauBuy;
    private double parSell;
    private double parBuy;

    public void init() {
        BufferedReader br = null;
        System.out.println("------ Enpara Live ------");
        try {
            URL url = new URL("https://www.qnbfinansbank.enpara.com/hesaplar/doviz-ve-altin-kurlari");
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            String pageSource = sb.toString();
            usdSell = Double.parseDouble(readBetweenTags(pageSource, "USD ($)", false));
            usdBuy = Double.parseDouble(readBetweenTags(pageSource, "USD ($)", true));
            eurSell = Double.parseDouble(readBetweenTags(pageSource, "EUR (€)", false));
            eurBuy = Double.parseDouble(readBetweenTags(pageSource, "EUR (€)", true));
            gauSell = Double.parseDouble(readBetweenTags(pageSource, "Altın (gram)", false));
            gauBuy = Double.parseDouble(readBetweenTags(pageSource, "Altın (gram)", true));
            parSell = Double.parseDouble(readBetweenTags(pageSource, "EUR/USD Parite", false));
            parBuy = Double.parseDouble(readBetweenTags(pageSource, "EUR/USD Parite", true));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String readBetweenTags(String pageSource, String str, boolean isSecond) {
        str = "<span>" + str + "</span><span>";
        int strIndex = pageSource.indexOf(str);
        String source = pageSource.substring(strIndex + str.length());
        int startIndex;
        if (isSecond) {
            startIndex = source.indexOf("<span>");
            source = source.substring(startIndex + "<span>".length()).replaceAll(",", ".");
        }
        int endIndex = source.indexOf("</span>");
        return source.substring(0, endIndex).split(" ")[0].replaceAll(",", ".");
    }

    public void print() {
        prettyPrintValues(usdSell, usdBuy, "usd   ");
        prettyPrintValues(eurSell, eurBuy, "eur   ");
        prettyPrintValues(parSell, parBuy, "par   ");
        prettyPrintValues(gauSell, gauBuy, "gau ");
    }

    private void prettyPrintValues(Double sell, Double buy, String extraSpace) {
        double diff = buy - sell;
        double diffOverBuy = diff / buy;
        System.out.println(extraSpace +
                BigDecimal.valueOf(buy).setScale(6, BigDecimal.ROUND_HALF_UP) +
                " / " +
                extraSpace +
                BigDecimal.valueOf(sell).setScale(6, BigDecimal.ROUND_HALF_UP) +
                "\t\t : \t makas: " +
                BigDecimal.valueOf(diff).setScale(6, BigDecimal.ROUND_HALF_UP) +
                "\t makas bölü alım: " +
                BigDecimal.valueOf(diffOverBuy).setScale(6, BigDecimal.ROUND_HALF_UP)
        );
    }
}