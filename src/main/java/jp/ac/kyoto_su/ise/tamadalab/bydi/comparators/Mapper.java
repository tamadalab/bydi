package jp.ac.kyoto_su.ise.tamadalab.bydi.comparators;

import java.util.Optional;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Method;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;
import jp.ac.kyoto_su.ise.tamadalab.bydi.extractors.DataPool;

public interface Mapper {
    Optional<MethodInfo> map(MethodInfo info);

    default Optional<MethodInfo> map(String className, String methodName, String signature) {
        return map(new MethodInfo(className, methodName, signature));
    }

    default Optional<Method> find(MethodInfo info, DataPool pool) {
        Optional<MethodInfo> target = map(info);
        return target.flatMap(mappedInfo -> pool.find(mappedInfo));
    }
}
