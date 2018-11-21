package jp.ac.kyoto_su.ise.tamadalab.bydi.entities;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Method {
    MethodInfo info;
    Optional<int[]> opcodes;

    public Method(MethodInfo info, Optional<int[]> opcodes) {
        this.info = info;
        this.opcodes = opcodes;
    }

    public boolean match(Method otherMethod) {
        return match(otherMethod.info);
    }

    public boolean match(MethodInfo givenInfo) {
        return Objects.equals(info, givenInfo);
    }

    public int[] opcodes() {
        return opcodes.orElseGet(() -> new int[0]);
    }

    public MethodInfo info() {
        return info;
    }

    public String className() {
        return info.className();
    }

    public String methodName() {
        return info.methodName();
    }

    public String signature() {
        return info.signature();
    }

    public int opcodeLength() {
        return opcodes.map(array -> array.length)
                .orElse(0);
    }

    public String toString() {
        return String.format("%s,%d,%s", info, opcodeLength(), opcodesString());
    }

    public String opcodesString() {
        return opcodes.map(data -> opcodesToString(data))
                .orElse("");
    }

    private String opcodesToString(int[] data) {
        return Arrays.stream(data)
                .mapToObj(d -> String.format("%02x", d))
                .collect(Collectors.joining());
    }
}
