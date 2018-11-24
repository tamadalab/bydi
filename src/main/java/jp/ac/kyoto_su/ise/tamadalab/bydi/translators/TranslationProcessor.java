package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Arguments;
import jp.ac.kyoto_su.ise.tamadalab.bydi.cli.Processor;

public class TranslationProcessor implements Processor {
    @Override
    public Arguments buildArguments() {
        return new TranslatorArguments();
    }

    @Override
    public void perform(Arguments args) {
        Optional<Translator> translator = args.get("format")
                .flatMap(type -> new TranslatorBuilder().get(type));
        translator.ifPresent(t -> perform(t, args));
    }

    private void perform(Translator translator, Arguments args) {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        args.stream().forEach(item -> translate(item, translator, out));
    }

    private void translate(String item, Translator translator, PrintWriter out) {
        try(Stream<String> stream = Files.lines(Paths.get(item))){
            translator.translate(stream, out);
            out.flush();
        } catch(IOException e) {
            throw new InternalError(e);
        }
    }

    public static void main(String[] args) throws IOException {
        TranslationProcessor translator = new TranslationProcessor();
        translator.run(args);
    }

}
