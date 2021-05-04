package i.am.firestarterr.groundzeroinvestment;

import i.am.firestarterr.groundzeroinvestment.model.Input;
import i.am.firestarterr.groundzeroinvestment.service.Calculator;
import i.am.firestarterr.groundzeroinvestment.service.EnparaLive;
import i.am.firestarterr.groundzeroinvestment.service.factory.CalculatorFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroundZeroInvestment {

    public static void main(String[] args) {
        double anapara = 0d;

        CalculatorFactory calculatorFactory = new CalculatorFactory();

        Path path = null;
        try {
            path = Paths.get(Objects.requireNonNull(GroundZeroInvestment.class.getClassLoader()
                    .getResource("inputs.txt")).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        assert path != null;
        try (Stream<String> stream = Files.lines(path)) {
            for (String line : stream.collect(Collectors.toList())) {
                line = line.replaceAll("altin", "altın").replaceAll(",", ".");
                String[] params = line.split(":");
                if (params[0].equalsIgnoreCase("anapara")) {
                    anapara += Double.parseDouble(params[1]);
                    continue;
                }
                Input input = new Input(params);
                String type = params[0];
                Calculator calculator = calculatorFactory.getCalculator(type);
                calculator.addInput(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Oyun başlangıcı : 03/11/2020");
        EnparaLive enparaLive = new EnparaLive();
        enparaLive.init();

        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            double liveBuy = 0d;
            double liveSell = 0d;
            switch (calculator.getType()) {
                case "altın":
                    liveBuy = enparaLive.getGauBuy();
                    liveSell = enparaLive.getGauSell();
                    break;
                case "dolar":
                    liveBuy = enparaLive.getUsdBuy();
                    liveSell = enparaLive.getUsdSell();
                    break;
                case "euro":
                    liveBuy = enparaLive.getEurBuy();
                    liveSell = enparaLive.getEurSell();
                    break;
            }
            calculator.calculate(liveBuy, liveSell);
        }

        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            calculator.printSellRate();
        }

        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            calculator.printBuyRate();
        }

        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            calculator.printRemainingTotal();
        }

        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            calculator.printEffectiveSellRate();
        }

        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            calculator.printSuggestions();
        }

        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            calculator.printEffectiveSellInfo();
        }

        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            calculator.printRateWeight();
        }

        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            calculator.printStanding();
        }

        double totalProfit = 0d;
        double anlikEderi = 0d;
        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            totalProfit += calculator.printProfit();
            anlikEderi += (calculator.getTotalRemainingAmount() * calculator.getEnparaSell());
        }

        System.out.println("-------------------------");
        System.out.println("INVESTMENT TOTAL: " + anapara);
        System.out.println("INVESTMENT WORTH: " + anlikEderi + " diff: " + (anlikEderi - anapara));
        System.out.println("-------------------------");
        System.out.println("TOTAL PROFIT: " + totalProfit);
        System.out.println("-------------------------");

        enparaLive.print();
        System.out.println("-------------------------");
    }

}
