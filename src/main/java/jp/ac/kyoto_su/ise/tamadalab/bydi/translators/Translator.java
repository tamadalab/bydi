package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.io.IOException;

public class Translator {
    public void run(String[] args) throws IOException {
        Arguments arguments = new Arguments();
        arguments.parse(args);
        arguments.perform(arg -> perform(arg));
    }

    private void perform(Arguments args) {
        
    }

    public static void main(String[] args) throws IOException {
        Translator translator = new Translator();
        translator.run(args);
    }
}
