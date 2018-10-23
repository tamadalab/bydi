package jp.ac.kyoto_su.ise.tamadalab.bydi.extractor;

import java.util.Optional;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Method;

public interface DataPool {
    default void pour(String className, String methodName,
            String signature, Optional<int[]> opcodes) {
        pour(new Method(className, methodName, signature, opcodes));
    }

    void pour(Method method);

    default Optional<Method> find(Method method){
        return find(method.className(), method.methodName(), method.signature());
    }

    Optional<Method> find(String className, String methodName, String signature);

    Stream<Method> stream();
}
