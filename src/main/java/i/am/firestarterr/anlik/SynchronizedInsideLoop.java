package i.am.firestarterr.anlik;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedInsideLoop {

    private static final String GET_URL = "http://localhost:8002/secure/schedulers_old.xhtml";

    public static void main(String[] args) throws InterruptedException {
        SynchronizedInsideLoop loop = new SynchronizedInsideLoop();
        loop.syncLoop();
    }

    private List<Integer> generateIntegerList() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            integers.add(i);
        }
        return integers;
    }

    private void syncLoop() throws InterruptedException {
        List<Integer> integers = generateIntegerList();
        for (Integer integer : integers) {
            new Thread(() -> print(integer)).start();
            Thread.sleep(300);
        }
    }

    private void print(Integer i) {
        System.out.println(i);
    }

}