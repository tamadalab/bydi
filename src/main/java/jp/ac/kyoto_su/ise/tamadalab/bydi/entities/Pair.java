package jp.ac.kyoto_su.ise.tamadalab.bydi.entities;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class Pair<L, R> {
    private L left;
    private R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public <T> T map(BiFunction<L, R, T> mapper) {
        return mapper.apply(left, right);
    }

    public void perform(BiConsumer<L, R> action) {
        action.accept(left, right);
    }

    public boolean test(BiPredicate<L, R> predicate) {
        return predicate.test(left, right);
    }
}
