package i.am.firestarterr.groundzeroinvestment;

import i.am.firestarterr.groundzeroinvestment.model.Input;
import i.am.firestarterr.groundzeroinvestment.service.Calculator;
import i.am.firestarterr.groundzeroinvestment.service.EnparaLiveJsoup;
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
                if (line.startsWith("#") || line.startsWith("/"))
                    continue;
                String[] params = line.split(":");
                Input input = new Input(params);
                String type = params[0];
                Calculator calculator = calculatorFactory.getCalculator(type);
                calculator.addInput(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Oyun başlangıcı : 03/11/2020");
        EnparaLiveJsoup enparaLive = new EnparaLiveJsoup();
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

        double totalRemainingInvestment = 0d;
        double totalProfit = 0d;
        double anlikEderi = 0d;
        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            totalProfit += calculator.printProfit();
            anlikEderi += (calculator.getTotalRemainingAmount() * calculator.getEnparaSell());
            totalRemainingInvestment += calculator.getRemainingInvestment().stream().map(investmentJoined -> Double.valueOf(investmentJoined.split(":")[1])).mapToDouble(Double::doubleValue).sum();
        }

        System.out.println("-------------------------");
        System.out.println("INVESTMENT TOTAL: " + totalRemainingInvestment);
        System.out.println("INVESTMENT WORTH: " + anlikEderi + " diff: " + (anlikEderi - totalRemainingInvestment));
        System.out.println("-------------------------");
        System.out.println("TOTAL PROFIT: " + totalProfit);
        System.out.println("-------------------------");

        for (Calculator calculator : calculatorFactory.getAllCalculators()) {
            calculator.printRemainingInvestments();
        }
        enparaLive.print();
    }

}
