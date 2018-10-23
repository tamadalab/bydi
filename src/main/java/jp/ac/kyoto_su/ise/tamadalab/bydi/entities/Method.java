package jp.ac.kyoto_su.ise.tamadalab.bydi.entities;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Method {
    String className;
    String methodName;
    String signature;
    Optional<int[]> opcodes;

    public Method(String className, String methodName, String signature, Optional<int[]> opcodes) {
        this.className = className;
        this.methodName = methodName;
        this.signature = signature;
        this.opcodes = opcodes;
    }

    public boolean match(Method otherMethod) {
        return match(otherMethod.methodName,
                otherMethod.signature);
    }

    public boolean match(String givenMethodName, String givenSignature) {
        return Objects.equals(this.methodName, givenMethodName)
                && Objects.equals(signature, givenSignature);
    }

    public int[] opcodes() {
        return opcodes.orElseGet(() -> new int[0]);
    }

    public String className() {
        return className;
    }

    public String methodName() {
        return methodName;
    }

    public String signature() {
        return signature;
    }

    public int opcodeLength() {
        return opcodes.map(array -> array.length)
                .orElse(0);
    }

    public String toString() {
        return String.format("%s,%s,%s,%d,%s",
                className, methodName, signature, opcodeLength(), opcodesString());
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
