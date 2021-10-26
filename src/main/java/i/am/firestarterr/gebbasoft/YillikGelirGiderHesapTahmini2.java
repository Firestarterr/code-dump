package i.am.firestarterr.gebbasoft;

import i.am.firestarterr.groundzeroinvestment.service.EnparaLive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class YillikGelirGiderHesapTahmini2 {

    private static final double kdv1 = 1d;
    private static final double kdv8 = 8d;
    private static final double kdv18 = 18d;

    public static void main(String[] args) {
        DecimalFormat paraFormatter = new DecimalFormat("####,###,###.00");
        EnparaLive enparaLive = new EnparaLive();
        enparaLive.init();
        //inputs
        double aylikFatura = 28000d + 18000d;
        double aylikKdvsizFatura = 5200d * (enparaLive.getUsdBuy() + enparaLive.getUsdSell()) / 2;
        double aylik1Gider = 3000d;
        double aylik8Gider = 4000d;
        double aylik18Gider = 9000d;

        double aylikKdv = aylikFatura * kdv18 / 100;
        double aylikKazanc = aylikFatura + aylikKdv + aylikKdvsizFatura;
        double yillikFatura = (aylikFatura + aylikKdvsizFatura) * 12;
        double yillikKdv = aylikKdv * 12;
        double yillikKazanc = aylikKazanc * 12;

        List<GelirVergisiDilimi> dilimler = GibTarife.init();

        System.out.println("Yıllık Brüt: " + yillikKazanc);

        double aylik1Kdv = aylik1Gider * kdv1 / 100d;
        double aylik1KdvsizGider = aylik1Gider - aylik1Kdv;
        System.out.println("%1 lik kdv: " + aylik1Kdv + "\t%1 gider: " + aylik1KdvsizGider);

        double aylik8Kdv = aylik8Gider * kdv8 / 100d;
        double aylik8KdvsizGider = aylik8Gider - aylik8Kdv;
        System.out.println("%8 lik kdv: " + aylik8Kdv + "\t%8 gider: " + aylik8KdvsizGider);

        double aylik18Kdv = aylik18Gider * kdv18 / 100d;
        double aylik18KdvsizGider = aylik18Gider - aylik18Kdv;
        System.out.println("%18 lik kdv: " + aylik18Kdv + "\t%18 gider: " + aylik18KdvsizGider);

        double aylikKalanKdv = aylikKdv - aylik1Kdv - aylik8Kdv - aylik18Kdv;
        System.out.println("Aylık Ele Gecen: " + aylikKazanc + "\tAylik Kdv: " + aylikKdv);
        System.out.println("Aylık Odenecek Kdv: " + aylikKalanKdv);

        double vergisiOdenmemisGelir = 0d;
        double dusmedenVergisiOdenmemisGelir = 0d;
        double toplamGelir = 0d;
        double toplamOdenenGelirVergisi = 0d;
        double dusmedenToplamOdenenGelirVergisi = 0d;
        double toplamOdenenKdv = 0d;
        System.out.println("----- Aylik Hesap -----");
        for (int i = 1; i < 13; i++) {
            double gelir = aylikKazanc - aylik1Gider - aylik8Gider - aylik18Gider;
            gelir = gelir - aylikKdvsizFatura / 2;

            toplamGelir += gelir;
            System.out.println(i + " ay geliri: " + gelir);
            System.out.println(i + " ay toplam geliri: " + (toplamGelir + aylikKdvsizFatura / 2));

            System.out.println(i + " ay kdvsi: " + aylikKalanKdv);
            toplamOdenenKdv += aylikKalanKdv;

            vergisiOdenmemisGelir += gelir;
            dusmedenVergisiOdenmemisGelir += gelir;
            switch (i) {
                case 3:
                case 6:
                case 9:
                case 12:
                    double geciciGelirVergisi = vergisiOdenmemisGelir * dilimler.get(0).getOran();
                    double dusmedenGeciciGelirVergisi = dusmedenVergisiOdenmemisGelir * dilimler.get(0).getOran();
                    toplamOdenenGelirVergisi += geciciGelirVergisi;
                    dusmedenToplamOdenenGelirVergisi += dusmedenGeciciGelirVergisi;
                    System.out.println("*" + i + " ay gelir vergisi:" + geciciGelirVergisi);
                    vergisiOdenmemisGelir = 0d;
                    dusmedenVergisiOdenmemisGelir = 0d;
                    break;

            }
            System.out.println("-----");
        }
        System.out.println("inputlar");
        System.out.println(
                "Kesilen Fatura: " + aylikFatura + "\t" +
                        "Kesilen Kdvsiz Fatura: " + aylikKdvsizFatura + "\t" +
                        "Harcanan %1 lik :" + aylik1Gider + "\t" +
                        "Harcanan %8 lik :" + aylik8Gider + "\t" +
                        "Harcanan %18 lik :" + aylik18Gider
        );
        System.out.println("----- Sene Sonu Hesabı -----");
        double yillikGelirVergisi = 0d;
        double dusmedenYillikGelirVergisi = 0d;
        for (GelirVergisiDilimi dilim : dilimler) {
            if (!(toplamGelir > dilim.getNin())) {
                double kalanGelir = toplamGelir - dilim.getSi_icin();
                yillikGelirVergisi += dilim.getMiktar();
                yillikGelirVergisi += kalanGelir * dilim.getOran();
                System.out.println("Yıllık Gelir Vergisi (" + dilim.getOran() + "):" + paraFormatter.format(yillikGelirVergisi));
                System.out.println("Seneye ödenecek olan taksitler " + paraFormatter.format((yillikGelirVergisi - toplamOdenenGelirVergisi) / 2) + " x2");
                break;
            }
        }
        for (GelirVergisiDilimi dilim : dilimler) {
            if (!(yillikKazanc > dilim.getNin())) {
                double kalanGelir = yillikKazanc - dilim.getSi_icin();
                dusmedenYillikGelirVergisi += dilim.getMiktar();
                dusmedenYillikGelirVergisi += kalanGelir * dilim.getOran();
                break;
            }
        }

        yillikGelirVergisi -= toplamOdenenGelirVergisi;
        dusmedenYillikGelirVergisi -= dusmedenToplamOdenenGelirVergisi;

        toplamOdenenGelirVergisi += yillikGelirVergisi;
        dusmedenToplamOdenenGelirVergisi += dusmedenYillikGelirVergisi;

        System.out.println("----- Hesap -----");
        System.out.println("Toplam Kazanılan: " + paraFormatter.format(yillikKazanc));
        System.out.println("Toplam Gelir: " + paraFormatter.format(toplamGelir + (aylikKdvsizFatura / 2 * 12)) + "\t" + " Hiç Düşmezsen: " + paraFormatter.format(yillikFatura));
        System.out.println("Toplam Kdv: " + paraFormatter.format(toplamOdenenKdv) + "\t" + " Hiç Düşmezsen: " + paraFormatter.format(yillikKdv));
        System.out.println("Toplam Gelir Vergisi: " + paraFormatter.format(toplamOdenenGelirVergisi) + "\t" + " Hiç Düşmezsen: " + paraFormatter.format(dusmedenToplamOdenenGelirVergisi));
        double eldeKalanPara = yillikKazanc - toplamOdenenKdv - toplamOdenenGelirVergisi;
        System.out.println("Elde Kalan Para Yillik: " + paraFormatter.format(eldeKalanPara));
        System.out.println("Elde Kalan Para Aylik: " + paraFormatter.format(eldeKalanPara / 12));
    }

    @Data
    @AllArgsConstructor
    public static class GelirVergisiDilimi {
        private double nin;
        private double si_icin;
        private double miktar;
        private double oran;
    }

    public static class GibTarife {
        public static List<GelirVergisiDilimi> init() {
            List<GelirVergisiDilimi> gelirVergisiDilimiList = new ArrayList<>();
            gelirVergisiDilimiList.add(new GelirVergisiDilimi(22000d, 0d, 0d, 0.15d));
            gelirVergisiDilimiList.add(new GelirVergisiDilimi(49000d, 22000d, 3300d, 0.20d));
            gelirVergisiDilimiList.add(new GelirVergisiDilimi(120000d, 49000d, 8700d, 0.27d));
            gelirVergisiDilimiList.add(new GelirVergisiDilimi(600000d, 120000d, 27870d, 0.35d));
            gelirVergisiDilimiList.add(new GelirVergisiDilimi(999999999d, 600000d, 195870d, 0.40d));
            return gelirVergisiDilimiList;
        }
    }
}