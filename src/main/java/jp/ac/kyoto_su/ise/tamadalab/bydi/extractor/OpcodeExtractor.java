package jp.ac.kyoto_su.ise.tamadalab.bydi.extractor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.jar.JarFile;
import java.util.stream.StreamSupport;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.InstructionList;

import jp.ac.kyoto_su.ise.tamadalab.bydi.comparator.DataPoolBuilder;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.DataStore;

public class OpcodeExtractor implements DataPoolBuilder {
    @Override
    public Optional<DataPool> extract(String path) {
        try {
            DataPool pool = new DataStore();
            extractImpl(path, pool);
            return Optional.of(pool);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private void extractImpl(String path, DataPool dest) throws IOException {
        try(JarFile jarfile = new JarFile(path)){
            jarfile.stream()
            .map(entry -> entry.getName())
            .filter(name -> name.endsWith(".class"))
            .forEach(className -> extract(path, className, dest));
        }
    }

    private void extract(String jarFile, String className, DataPool dest) {
        try {
            ClassParser parser = new ClassParser(jarFile, className);
            JavaClass jc = parser.parse();
            pourClass(jarFile, jc, dest);
        } catch (ClassFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private void pourClass(String source, JavaClass jc, DataPool dest) {
        Arrays.stream(jc.getMethods())
        .forEach(method -> pourMethod(jc.getClassName(), method, dest));
    }

    private void pourMethod(String className, Method method, DataPool dest) {
        Optional<Code> code = Optional.ofNullable(method.getCode());
        dest.pour(className, method.getName(), method.getSignature(), 
                  code.map(c -> codeToOpcodes(c)));
    }

    private int[] codeToOpcodes(Code code) {
        InstructionList list = new InstructionList(code.getCode());
        return StreamSupport.stream(list.spliterator(), false)
                .map(handle -> handle.getInstruction())
                .mapToInt(instruction -> instruction.getOpcode())
                .toArray();
    }
}
