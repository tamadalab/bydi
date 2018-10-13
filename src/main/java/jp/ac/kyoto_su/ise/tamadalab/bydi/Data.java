package jp.ac.kyoto_su.ise.tamadalab.bydi;

public class Data {
    private String className;
    private String methodName;
    private String parameters;
    private int[] data;

    public Data(String className, String methodName, String parameters, int[] data) {
        this.className = className;
        this.methodName = methodName;
        this.parameters = parameters;
        this.data = data;
    }

    public String getName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getParameters() {
        return parameters;
    }

    public int[] getData() {
        return data;
    }
}
