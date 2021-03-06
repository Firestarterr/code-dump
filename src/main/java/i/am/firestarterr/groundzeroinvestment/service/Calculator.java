package i.am.firestarterr.groundzeroinvestment.service;

import i.am.firestarterr.groundzeroinvestment.model.Input;
import i.am.firestarterr.groundzeroinvestment.model.Operation;
import lombok.Getter;

import java.util.*;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

@Getter
public class Calculator {
    private final String type;
    private Map<Double, Double> buyMap = new HashMap<>();
    private Map<Double, Double> sellMap = new HashMap<>();
    private double enparaBuy;
    private double enparaSell;

    //input data calculations
    private Double averageBuyRate = 0d;
    private Double averageSellRate = 0d;
    private Double totalBoughtAmount = 0d;
    private Double totalSoldAmount = 0d;
    private Double totalBoughtPrice = 0d;
    private Double totalSoldPrice = 0d;
    //calculated investment amount list
    private final LinkedList<String> remainingInvestment = new LinkedList<>();
    //derivative calculations
    private Map<Double, Double> remainingSellMap = new HashMap<>();
    private Double effectiveBoughtRate = 0d;
    private Double effectiveBoughtPrice = 0d;
    private Double remainingSellAmount = 0d;
    private Double remainingSellPrice = 0d;
    private Double totalRemainingAmount = 0d;
    private Double effectiveSellRate = 0d;
    private String sellSuggestion;
    private String buySuggestion;
    private Double rateWeight = 0d;
    private Double profit = 0d;
    private Double currentStanding = 0d;
    private final List<String> remainingInvestments = new ArrayList<>();

    public Calculator(String type) {
        this.type = type;
    }

    public void addInput(Input input) {
        Double amount = input.getAmount();
        if (input.getOperation().equals(Operation.sat)) {
            putToMap(true, input.getRate(), amount, input.getCommission());
            while (amount > 0) {
                if (remainingInvestment.isEmpty()) return;
                String investmentJoined = remainingInvestment.removeFirst();
                double investmentAmount = Double.parseDouble(investmentJoined.split(":")[0]);
                double investmentPrice = Double.parseDouble(investmentJoined.split(":")[1]);
                if (investmentAmount > amount) {
                    investmentPrice = investmentPrice * ((investmentAmount - amount) / investmentAmount);
                    remainingInvestment.addFirst((investmentAmount - amount) + ":" + investmentPrice);
                }
                amount = amount - investmentAmount;
            }
        } else if (input.getOperation().equals(Operation.kambiyo)) {
            if (buyMap.containsKey(input.getRate())) {
                buyMap.remove(input.getRate());
                putToMap(false, input.getRate() * 2, amount, input.getCommission());
            } else {
                putToMap(false, input.getRate(), amount, input.getCommission());
            }
            String investmentJoined = remainingInvestment.removeLast();
            double investmentAmount = Double.parseDouble(investmentJoined.split(":")[0]);
            double investmentPrice = Double.parseDouble(investmentJoined.split(":")[1]);
            investmentPrice = investmentPrice + input.getRate();
            remainingInvestment.addLast(investmentAmount + ":" + investmentPrice);

        } else if (input.getOperation().equals(Operation.al)) {
            putToMap(false, input.getRate(), amount, input.getCommission());
            remainingInvestment.addLast(amount + ":" + (input.getRate() * amount));
        } else {
            System.out.println(input + " ignored.");
        }
    }

    private void putToMap(boolean isSell, Double key, Double value, Double commission) {
        if (isSell) {
            sellMap.merge(key + (key * commission), value, Double::sum);
        } else {
            buyMap.merge(key, (value - (value * commission)), Double::sum);
        }
    }

    public void calculate(Double liveBuy, Double liveSell) {
        sellMap = sellMap.entrySet().stream().sorted(Collections.reverseOrder(comparingByKey())).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        buyMap = buyMap.entrySet().stream().sorted(comparingByKey()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
        enparaBuy = liveBuy;
        enparaSell = liveSell;

        System.out.println("sat??lan " + type + ":");
        sellMap.forEach((key, value) -> {
            System.out.println("kur: " + key + "\t miktar: " + value + "\t total: " + getPrice(key, value));
            totalSoldAmount += value;
            totalSoldPrice += getPrice(key, value);
        });
        averageSellRate = totalSoldPrice / totalSoldAmount;


        Double amount = totalSoldAmount;
        effectiveBoughtPrice = 0d;
        System.out.println("-------------------------");
        System.out.println("al??nan " + type + ":");
        for (Map.Entry<Double, Double> entry : buyMap.entrySet()) {
            System.out.println("kur: " + entry.getKey() + "\t miktar: " + entry.getValue() + "\t total: " + getPrice(entry.getKey(), entry.getValue()));
            if (entry.getValue() == 0d) {
                effectiveBoughtPrice += getPrice(entry.getKey(), entry.getValue());
            } else if (amount == -1d) {
                remainingSellMap.merge(entry.getKey(), entry.getValue(), Double::sum);
            } else if (amount - entry.getValue() < 0) {
                effectiveBoughtPrice += entry.getKey() * amount;
                if (entry.getValue() - amount > 0) {
                    remainingSellMap.merge(entry.getKey(), entry.getValue() - amount, Double::sum);
                    amount = -1d;
                }
            } else {
                effectiveBoughtPrice += getPrice(entry.getKey(), entry.getValue());
                amount -= entry.getValue();
            }
            totalBoughtAmount += entry.getValue();
            totalBoughtPrice += getPrice(entry.getKey(), entry.getValue());
        }
        averageBuyRate = totalBoughtPrice / totalBoughtAmount;
        effectiveBoughtRate = effectiveBoughtPrice / totalSoldAmount;
        System.out.println("-------------------------");
        System.out.println("total al??nan:\t " + totalBoughtAmount + "\t total tl tutar:\t " + totalBoughtPrice);
        System.out.println("total sat??lan:\t " + totalSoldAmount + "\t total tl tutar:\t " + totalSoldPrice);
        System.out.println("-------------------------");
        System.out.println("ortalama al???? kur:\t " + averageBuyRate);
        System.out.println("ortalama sat???? kur:\t " + averageSellRate);
        System.out.println("ortalama efektif al???? kur:\t " + effectiveBoughtRate);
        System.out.println("Hen??z sat??lmam???? miktar: \t " + (totalBoughtAmount - totalSoldAmount));
        System.out.println("-------------------------");
        remainingSellMap = remainingSellMap.entrySet().stream().sorted(comparingByKey()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println("Kalan: " + type);
        remainingSellMap.forEach((key, value) -> {
            String kalanStr = "kur: " + key + "\t miktar: " + value + "\t total: " + getPrice(key, value);
            remainingInvestments.add(kalanStr);
            System.out.println(kalanStr);
            remainingSellAmount += value;
            remainingSellPrice += getPrice(key, value);
            totalRemainingAmount += value;
        });
        effectiveSellRate = remainingSellPrice / remainingSellAmount;
        try {
            sellSuggestion = (enparaSell - (enparaBuy - enparaSell) - effectiveSellRate) > 0 ? "sat" : "satma";
            buySuggestion = (effectiveBoughtRate - enparaBuy - (enparaBuy - enparaSell)) > 0 ? "al" : "alma";
        } catch (Exception ignored) {
        }
        profit = totalSoldPrice - effectiveBoughtPrice;
        currentStanding = totalSoldPrice - totalBoughtPrice;
        rateWeight = 100 * profit / totalSoldPrice;
        System.out.println("-------------------------");
        System.out.println("-------------------------");
    }

    public void printRemainingInvestments() {
        System.out.println("Kalan: " + type);
        remainingInvestments.forEach(System.out::println);
    }

    public void printBuyRate() {
        System.out.println("ortalama " + type + " al???? kuru: " + averageBuyRate + " \t anl??k al???? kuru: " + enparaBuy
                + " \t y??zde fark: " + 100 * Math.abs(averageBuyRate - enparaBuy) / averageBuyRate);
    }

    public void printSellRate() {
        System.out.println("ortalama " + type + " sat???? kuru: " + averageSellRate + " \t anl??k sat???? kuru: " + enparaSell
                + " \t y??zde fark: " + 100 * Math.abs(averageSellRate - enparaSell) / averageSellRate);
    }

    public void printEffectiveSellRate() {
        System.out.println(type + " sat??lmas?? gereken kur: \t " + effectiveSellRate + " \t anl??k sat???? kuru: " + enparaSell
                + " \t y??zde fark: " + 100 * Math.abs(effectiveSellRate - enparaSell) / effectiveSellRate);
    }

    public void printSuggestions() {
        System.out.println(type + ": " + buySuggestion + "\t\t" + sellSuggestion);
    }

    public void printEffectiveSellInfo() {
        System.out.println(type + " enpara'daki canl?? kur: \t " + enparaSell);
        System.out.println(type + " fark: " + (enparaSell - effectiveSellRate));
        System.out.println(type + " fark-makas: " + (enparaSell - (enparaBuy - enparaSell) - effectiveSellRate));
    }

    public void printRemainingTotal() {
        System.out.println(type + " sat??lmas?? gereken miktar: \t " + totalRemainingAmount + " \t anl??k ederi: " + (totalRemainingAmount * enparaSell));
    }

    public void printStanding() {
        System.out.println(type + " durum: " + currentStanding);
    }

    public Double printProfit() {
        System.out.println(type + " profit: " + profit);
        return profit;
    }

    public void printRateWeight() {
        System.out.println(type + " kazand??rma a????rl??????: \t " + "%" + rateWeight + "\t i??lem Hacmi: " + totalSoldAmount + "\t tl i??lem hacmi: " + totalSoldPrice);
    }

    private Double getPrice(Double key, Double value) {
        return value == 0d ? key : key * value;
    }
}
