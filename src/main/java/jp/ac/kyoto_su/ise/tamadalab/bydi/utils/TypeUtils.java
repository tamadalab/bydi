package jp.ac.kyoto_su.ise.tamadalab.bydi.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;

public class TypeUtils {
    private static final TypeUtils INSTANCE = new TypeUtils();

    private TypeUtils(){
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
        if(Objects.equals("int", sourceForm))
            return "I";
        else if(Objects.equals("double", sourceForm))
            return "D";
        else if(Objects.equals("float", sourceForm))
            return "F";
        else if(Objects.equals("boolean", sourceForm))
            return "Z";
        else if(Objects.equals("long", sourceForm))
            return "J";
        else if(Objects.equals("byte", sourceForm))
            return "B";
        else if(Objects.equals("char", sourceForm))
            return "C";
        else if(Objects.equals("short", sourceForm))
            return "S";

        StringBuilder sb = new StringBuilder();
        sb.append("L");
        sb.append(sourceForm.replace('.', '/'));
        sb.append(";");
        return new String(sb);
    }

    private String dimension(int dimension){
        StringBuilder sb = new StringBuilder();
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
