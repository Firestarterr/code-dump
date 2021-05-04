package i.am.firestarterr.groundzeroinvestment.service;

import i.am.firestarterr.groundzeroinvestment.model.Input;
import i.am.firestarterr.groundzeroinvestment.model.Operation;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public Calculator(String type) {
        this.type = type;
    }

    public void addInput(Input input) {
        if (input.getOperation().equals(Operation.sat)) {
            putToMap(true, input.getRate(), input.getAmount(), input.getCommission());
        } else if (input.getOperation().equals(Operation.kambiyo)) {
            if (buyMap.containsKey(input.getRate())) {
                buyMap.remove(input.getRate());
                putToMap(false, input.getRate() * 2, input.getAmount(), input.getCommission());
            } else {
                putToMap(false, input.getRate(), input.getAmount(), input.getCommission());
            }
        } else if (input.getOperation().equals(Operation.al)) {
            putToMap(false, input.getRate(), input.getAmount(), input.getCommission());
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

        System.out.println("satılan " + type + ":");
        for (Map.Entry<Double, Double> entry : sellMap.entrySet()) {
            System.out.println("kur: " + entry.getKey() + "\t miktar: " + entry.getValue() + "\t total: " + getPrice(entry.getKey(), entry.getValue()));
            totalSoldAmount += entry.getValue();
            totalSoldPrice += getPrice(entry.getKey(), entry.getValue());
        }
        averageSellRate = totalSoldPrice / totalSoldAmount;


        Double amount = totalSoldAmount;
        effectiveBoughtPrice = 0d;
        System.out.println("-------------------------");
        System.out.println("alınan " + type + ":");
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
        System.out.println("total alınan:\t " + totalBoughtAmount + "\t total tl tutar:\t " + totalBoughtPrice);
        System.out.println("total satılan:\t " + totalSoldAmount + "\t total tl tutar:\t " + totalSoldPrice);
        System.out.println("-------------------------");
        System.out.println("ortalama alış kur:\t " + averageBuyRate);
        System.out.println("ortalama satış kur:\t " + averageSellRate);
        System.out.println("ortalama efektif alış kur:\t " + effectiveBoughtRate);
        System.out.println("Henüz satılmamış miktar: \t " + (totalBoughtAmount - totalSoldAmount));
        System.out.println("-------------------------");
        remainingSellMap = remainingSellMap.entrySet().stream().sorted(comparingByKey()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        System.out.println("Kalan: " + type);
        for (Map.Entry<Double, Double> entry : remainingSellMap.entrySet()) {
            System.out.println("kur: " + entry.getKey() + "\t miktar: " + entry.getValue() + "\t total: " + getPrice(entry.getKey(), entry.getValue()));
            remainingSellAmount += entry.getValue();
            remainingSellPrice += getPrice(entry.getKey(), entry.getValue());
            totalRemainingAmount += entry.getValue();
        }
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

    public void printBuyRate() {
        System.out.println("ortalama " + type + " alış kuru: " + averageBuyRate + " \t anlık alış kuru: " + enparaBuy);
    }

    public void printSellRate() {
        System.out.println("ortalama " + type + " satış kuru: " + averageSellRate + " \t anlık satış kuru: " + enparaSell);
    }

    public void printEffectiveSellRate() {
        System.out.println(type + " satılması gereken kur: \t " + effectiveSellRate);
    }

    public void printSuggestions() {
        System.out.println(type + ": " + buySuggestion + "\t\t" + sellSuggestion);
    }

    public void printEffectiveSellInfo() {
        System.out.println(type + " enpara'daki canlı kur: \t " + enparaSell);
        System.out.println(type + " fark: " + (enparaSell - effectiveSellRate));
        System.out.println(type + " fark-makas: " + (enparaSell - (enparaBuy - enparaSell) - effectiveSellRate));
    }

    public void printRemainingTotal() {
        System.out.println(type + " satılması gereken miktar: \t " + totalRemainingAmount + " \t anlık ederi: " + (totalRemainingAmount * enparaSell));
    }

    public void printStanding() {
        System.out.println(type + " durum: " + currentStanding);
    }

    public Double printProfit() {
        System.out.println(type + " profit: " + profit);
        return profit;
    }

    public void printRateWeight() {
        System.out.println(type + " kazandırma ağırlığı: \t " + "%" + rateWeight + "\t işlem Hacmi: " + totalSoldAmount + "\t tl işlem hacmi: " + totalSoldPrice);
    }

    private Double getPrice(Double key, Double value) {
        return value == 0d ? key : key * value;
    }
}
