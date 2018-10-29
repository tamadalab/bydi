package jp.ac.kyoto_su.ise.tamadalab.bydi.algorithms;

import java.util.Arrays;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;
import jp.ac.kyoto_su.ise.tamadalab.bydi.utils.StreamUtils;

public class ManhattanDistanceCalculator implements DistanceCalculator {
    @Override
    public double calculate(int[] data1, int[] data2) {
        Stream<Integer> stream1 = StreamUtils.infinitStream(intArrayToStream(data1), () -> 0);
        Stream<Integer> stream2 = StreamUtils.infinitStream(intArrayToStream(data2), () -> 0);
        return calculate(StreamUtils.zip(stream1, stream2)
                .limit(Math.max(data1.length, data2.length)));
    }

    private int calculate(Stream<Pair<Integer, Integer>> stream) {
        return stream.mapToInt(pair -> pair.map((v1, v2) -> Math.abs(v1 - v2)))
                .sum();
    }

    private Stream<Integer> intArrayToStream(int[] data) {
        return Arrays.stream(data)
                .mapToObj(value -> Integer.valueOf(value));
    }
}
