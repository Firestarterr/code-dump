package i.am.firestarterr.groundzeroinvestment.model;

import lombok.Getter;

@Getter
public enum Operation {
    al,
    sat,
    kambiyo;

    public static Operation getOperation(String input) {
        for (Operation type : values()) {
            if (type.name().equals(input)) {
                return type;
            }
        }
        return null;
    }
}
