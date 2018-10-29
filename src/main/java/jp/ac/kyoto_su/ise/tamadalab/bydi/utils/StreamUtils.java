package jp.ac.kyoto_su.ise.tamadalab.bydi.utils;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;

public class StreamUtils {
    public static <T> Stream<T> infinitStream(Stream<T> stream, Supplier<T> supplier){
        return Stream.concat(stream, Stream.generate(supplier));
    }

    public static <L, R> Stream<Pair<L, R>> zip(Stream<? extends L> stream1, Stream<? extends R> stream2) {
        checkArguments(stream1, stream2);
        return zipImpl(stream1, stream2);
    }

    private static <L, R> Stream<Pair<L, R>> zipImpl(Stream<? extends L> stream1, Stream<? extends R> stream2) {
        PairIterator<L, R> iterator = new PairIterator<>(stream1.iterator(), stream2.iterator(), Pair::new);
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        iterator, Spliterator.IMMUTABLE | Spliterator.NONNULL), false);
    }

    private static <L, R> void checkArguments(Stream<? extends L> stream1, Stream<? extends R> stream2) {
        Objects.requireNonNull(stream1);
        Objects.requireNonNull(stream2);
        if(stream1 == stream2)
            throw new IllegalArgumentException("require different stream.");
    }

    private static class PairIterator<L, R> implements Iterator<Pair<L, R>> {
        private Iterator<? extends L> iterator1;
        private Iterator<? extends R> iterator2;
        private BiFunction<L, R, Pair<L, R>> mapper;

        public PairIterator(Iterator<? extends L> iterator1, Iterator<? extends R> iterator2, BiFunction<L, R, Pair<L, R>> mapper) {
            this.iterator1 = iterator1;
            this.iterator2 = iterator2;
            this.mapper = mapper;
        }

        @Override
        public boolean hasNext() {
            return iterator1.hasNext() && iterator2.hasNext();
        }
        @Override
        public Pair<L, R> next() {
            return mapper.apply(iterator1.next(), iterator2.next());
        }
    }
}
