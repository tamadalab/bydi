package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.util.Optional;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.comparators.StoreMapper;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;

public class YGuardStore implements StoreMapper {

    @Override
    public Optional<MethodInfo> map(MethodInfo info) {
        return null;
    }

    @Override
    public void storeItem(String line, boolean memberFlag) {
        // TODO Auto-generated method stub

    }

    @Override
    public void done() {
        // TODO Auto-generated method stub

    }

    @Override
    public Stream<MethodInfoPair> stream() {
        // TODO Auto-generated method stub
        return null;
    }

}
