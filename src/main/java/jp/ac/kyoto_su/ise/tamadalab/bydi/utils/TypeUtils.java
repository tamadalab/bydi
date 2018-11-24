package jp.ac.kyoto_su.ise.tamadalab.bydi.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TypeUtils {
    private static final TypeUtils INSTANCE = new TypeUtils();
    private Map<String, String> TYPEMAP = new HashMap<>();

    private TypeUtils(){
        TYPEMAP.put("boolean", "Z");
        TYPEMAP.put("byte",    "B");
        TYPEMAP.put("char",    "C");
        TYPEMAP.put("short",   "S");
        TYPEMAP.put("int",     "I");
        TYPEMAP.put("long",    "J");
        TYPEMAP.put("double",  "D");
        TYPEMAP.put("float",   "F");
        TYPEMAP.put("void",    "V");
        TYPEMAP.put("",        "");
    }

    public static String toDescriptor(String sourceFormOfReturnType, String[] sourceFormOfArguments){
        StringBuilder sb = new StringBuilder();
        return new String(sb.append("(").append(INSTANCE.toSignature(sourceFormOfArguments))
                          .append(")").append(toDescriptor(sourceFormOfReturnType)));
    }

    public static String toDescriptor(String sourceForm){
        int arrayDimension = INSTANCE.countArrayDimension(sourceForm);
        sourceForm = sourceForm.replace("[]", "");
        return new String(new StringBuilder(INSTANCE.dimension(arrayDimension))
                          .append(INSTANCE.convert(sourceForm)));
    }

    private String toSignature(String[] arguments){
        return Arrays.stream(arguments)
            .map(argument -> toDescriptor(argument))
            .collect(Collectors.joining());
    }

    private String convert(String sourceForm){
        return TYPEMAP.getOrDefault(sourceForm, toClassForm(sourceForm));
    }

    private String toClassForm(String className) {
        StringBuilder sb = new StringBuilder();
        sb.append("L");
        sb.append(className.replace('.', '/'));
        sb.append(";");
        return new String(sb);
    }

    private String dimension(int dimension){
        return IntStream.range(0, dimension)
            .mapToObj(index -> "[")
            .collect(Collectors.joining());
    }

    private int countArrayDimension(String sourceForm){
        int arrayDimension = 0;
        int index = -1;
        while((index = sourceForm.indexOf("[]", index + 1)) > 0){
            arrayDimension++;
        }
        return arrayDimension;
    }
}
