package jp.ac.kyoto_su.ise.tamadalab.bydi;

import java.util.stream.IntStream;

public class EditDistanceCalculator {
    public int calculate(int[] data1, int[] data2) {
        Table table = init(data1, data2);
        for(int i = 1; i <= data1.length; i++) {
            for(int j = 1; j <= data2.length; j++) {
                int cost = 1;
                if(data1[i - 1] == data2[j - 1]) cost = 0;
                int d1 = table.get(i - 1, j    ) + 1;
                int d2 = table.get(i    , j - 1) + 1;
                int d3 = table.get(i - 1, j - 1) + cost;
                table.set(minimum(d1, d2, d3), i, j);
            }
        }
        return table.get(data1.length, data2.length);
    }

    private int minimum(int d1, int d2, int d3) {
        return Math.min(Math.min(d1, d2), d3);
    }

    private Table init(int[] data1, int[] data2) {
        Table table = new Table(data1.length + 1, data2.length + 2);
        IntStream.rangeClosed(0, data1.length)
        .forEach(index -> table.set(index, index, 0));
        IntStream.rangeClosed(0, data2.length)
        .forEach(index -> table.set(index, 0, index));
        return table;
    }
}
