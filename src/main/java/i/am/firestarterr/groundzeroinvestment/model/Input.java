package i.am.firestarterr.groundzeroinvestment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Input {
    private Operation operation;
    private Double rate;
    private Double amount;
    private Double commission = 0d;

    public Input(String[] line) {
        if (line.length == 5) {
            commission = Double.parseDouble(line[4]);
        }
        this.operation = Operation.getOperation(line[1]);
        if (Operation.kambiyo.equals(operation)) {
            rate = Double.parseDouble(line[2]);
            this.amount = 0d;
        } else {
            this.rate = Double.parseDouble(line[2]);
            this.amount = Double.parseDouble(line[3]);
        }
    }

    @Override
    public String toString() {
        return "operation=" + operation +
                ", rate=" + rate +
                ", amount=" + amount;
    }
}
