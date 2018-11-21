package jp.ac.kyoto_su.ise.tamadalab.bydi.entities;

import java.util.Objects;

public class MethodInfo {
    String className;
    String methodName;
    String signature;

    public MethodInfo(String className, String methodName, String signature) {
        this.className = className;
        this.methodName = methodName;
        this.signature = signature;
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

    public String toString() {
        return String.format("%s,%s,%s", className(), methodName(), signature());
    }

    public int hashCode() {
        return Objects.hash(className(), methodName(), signature(), getClass());
    }

    public boolean equals(Object other) {
        return Objects.equals(getClass(), other.getClass())
                && Objects.equals(className(), ((MethodInfo)other).className())
                && Objects.equals(methodName(), ((MethodInfo)other).methodName())
                && Objects.equals(signature(), ((MethodInfo)other).signature());
    }
}
