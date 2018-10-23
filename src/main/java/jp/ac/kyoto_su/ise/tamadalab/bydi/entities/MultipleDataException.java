package jp.ac.kyoto_su.ise.tamadalab.bydi.entities;

public class MultipleDataException extends RuntimeException {
    private static final long serialVersionUID = 5211619721015309118L;

    public MultipleDataException(String className, String methodName, String signature) {
        super(String.format("method duplicate: %s#%s(%s)", className, methodName, signature));
    }

    public MultipleDataException(Method m) {
        this(m.className, m.methodName, m.signature);
    }
}
