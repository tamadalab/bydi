package jp.ac.kyoto_su.ise.tamadalab.bydi.algorithms;

import java.util.HashMap;
import java.util.Map;

public class DistanceCalculatorBuilder {
    private Map<String, DistanceCalculator> calculators = new HashMap<>();

    public DistanceCalculatorBuilder() {
        calculators.put("edit", new EditDistanceCalculator());
        calculators.put("manhattan", new ManhattanDistanceCalculator());
        calculators.put("euclidean", new EuclideanDistanceCalculator());
    }

    public DistanceCalculator build(String algorithm) {
        return calculators.get(algorithm);
    }
}
