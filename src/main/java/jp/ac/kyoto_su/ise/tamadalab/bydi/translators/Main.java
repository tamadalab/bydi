package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {
    public void run(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);
        arguments.perform(arg -> perform(arg));
    }

    private void perform(Arguments args) {
        Optional<Translator> translator = new TranslatorBuilder().get(args.type());
        translator.ifPresent(t -> perform(t, args));
    }

    private void perform(Translator translator, Arguments args) {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        args.stream().forEach(item -> translate(item, translator, out));
    }

    private void translate(String item, Translator translator, PrintWriter out) {
        try(Stream<String> stream = Files.lines(Paths.get(item))){
            translator.translate(stream, out);
        } catch(IOException e) {
            throw new InternalError(e);
        }
    }

    public static void main(String[] args) throws IOException {
        Main translator = new Main();
        translator.run(args);
    }
}
