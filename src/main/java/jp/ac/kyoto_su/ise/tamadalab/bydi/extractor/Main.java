package jp.ac.kyoto_su.ise.tamadalab.bydi.extractor;

import java.io.IOException;
import java.util.Arrays;
import java.util.jar.JarFile;

public class Main {
    public void run(String[] args) {
        Arrays.stream(args)
        .forEach(this::extract);
    }

    private void extract(String path) {
        try {
            extractImpl(path);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void extractImpl(String path) throws IOException {
        try(JarFile jarfile = new JarFile(path)){
            OpcodeExtractor extractor = new OpcodeExtractor();
            jarfile.stream()
            .map(entry -> entry.getName())
            .filter(name -> name.endsWith(".class"))
            .forEach(className -> extractor.extract(path, className));
        }
    }

    public static void main(String[] args) {
        Main extractor = new Main();
        extractor.run(args);
    }
}
