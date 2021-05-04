package i.am.firestarterr.groundzeroinvestment.service.factory;

import i.am.firestarterr.groundzeroinvestment.service.Calculator;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Getter
public class CalculatorFactory {
    private final Map<String, Calculator> calculatorMap = new HashMap<>();

    public Calculator getCalculator(String type) {
        Calculator calculator = calculatorMap.get(type);
        if (calculator == null) {
            calculator = new Calculator(type);
            calculatorMap.put(type, calculator);
        }
        return calculator;
    }

    public Collection<Calculator> getAllCalculators() {
        System.out.println("-------------------------");
        return calculatorMap.values();
    }
}
