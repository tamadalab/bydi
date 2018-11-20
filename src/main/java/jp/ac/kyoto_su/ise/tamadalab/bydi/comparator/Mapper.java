package jp.ac.kyoto_su.ise.tamadalab.bydi.comparator;

import java.util.Optional;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Method;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;
import jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.DataPool;

public interface Mapper {
    MethodInfo map(MethodInfo info);

    default MethodInfo map(String className, String methodName, String signature) {
        return map(new MethodInfo(className, methodName, signature));
    }

    default Optional<Method> find(MethodInfo info, DataPool pool) {
        MethodInfo target = map(info);
        return pool.find(target);
    }
}
