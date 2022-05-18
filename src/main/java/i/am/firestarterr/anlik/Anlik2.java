package i.am.firestarterr.anlik;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Anlik2 {

    public static void main(String[] args) {
        List<Object> a = new ArrayList<>();
        a.add(new Object(1, "a"));
        a.add(new Object(2, "b"));
        a.add(new Object(3, "c"));
        a.add(new Object(4, "d"));
        a.add(new Object(5, "e"));
        a.add(new Object(6, "f"));
        a.add(new Object(7, "g"));
        a.add(new Object(8, "h"));

        a.removeIf(ehue -> ehue.getNumber() % 2 == 0);
        System.out.println(a.stream().map(Object::getX).collect(Collectors.joining(",")));
    }

    @Data
    @AllArgsConstructor
    static class Object {
        int number;
        String x;
    }

}
