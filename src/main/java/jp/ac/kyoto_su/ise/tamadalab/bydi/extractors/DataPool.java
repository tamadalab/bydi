package jp.ac.kyoto_su.ise.tamadalab.bydi.extractors;

import java.util.Optional;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Method;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;

public interface DataPool {
    default void pour(String className, String methodName,
            String signature, Optional<int[]> opcodes) {
        this.pour(new MethodInfo(className, methodName, signature), opcodes);
    }

    default void pour(MethodInfo info, Optional<int[]> opcodes) {
        this.pour(new Method(info, opcodes));
    }

    void pour(Method method);

    default Optional<Method> find(Method method){
        return find(method.info());
    }

    default Optional<Method> find(MethodInfo info) {
        return find(info.className(), info.methodName(), info.signature());
    }

    Optional<Method> find(String className, String methodName, String signature);

    Stream<Method> stream();
}
