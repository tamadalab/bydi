package jp.ac.kyoto_su.ise.tamadalab.bydi.extractor;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;

public class OpcodeExtractor {
    public void extract(String jarFile, String className) {
        try {
            System.err.printf("jar: %s of %s%n", jarFile, className);
            ClassParser parser = new ClassParser(jarFile, className);
            JavaClass jc = parser.parse();
            printAll(jarFile, jc);
        } catch (ClassFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    private void printAll(String source, JavaClass jc) {
        Arrays.stream(jc.getMethods())
        .forEach(method -> printMethod(jc.getClassName(), method));
    }

    private void printMethod(String className, Method method) {
        Optional<Code> code = Optional.ofNullable(method.getCode());
        System.out.printf("artifactid,groupid,version,%s,%s,%s,%d,,%s%n",
                className, method.getName(), method.getSignature(),
                code.map(Code::getLength).orElse(0), 
                code.map(this::opcodes).orElse(""));
    }

    private String opcodes(Code code) {
        InstructionList list = new InstructionList(code.getCode());
        StringBuilder sb = new StringBuilder();
        for(InstructionHandle handle: list) {
            Instruction i = handle.getInstruction();
            sb.append(String.format("%02x", i.getOpcode()));
        }
        return new String(sb);
    }
}
