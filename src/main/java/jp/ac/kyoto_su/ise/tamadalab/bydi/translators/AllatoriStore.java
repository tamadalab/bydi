package jp.ac.kyoto_su.ise.tamadalab.bydi.translators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.bcel.generic.Type;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import jp.ac.kyoto_su.ise.tamadalab.bydi.comparators.StoreMapper;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.MethodInfo;
import jp.ac.kyoto_su.ise.tamadalab.bydi.entities.Pair;
import jp.ac.kyoto_su.ise.tamadalab.bydi.utils.TypeUtils;

public class AllatoriStore implements StoreMapper {
    private Map<NamePair, List<MethodInfoPair>> map = new HashMap<>();
    private List<String> lines = new ArrayList<>();
    private Map<String, String> classNameMap = new HashMap<>();

    @Override
    public void start() {
    }

    @Override
    public void storeItem(String line, boolean flag) {
        if(line.startsWith("<class ")) {
            Pair<String, String> pair = parseNamePair(line);
            pair.perform((before, after) -> classNameMap.put(before, after));
        }
        lines.add(line);
    }

    Pair<String, String> parseNamePair(String line) {
        String oldName = extract(line, "old=\"", "\"");
        String newName = extract(line, "new=\"", "\"");
        return new Pair<>(oldName, newName);
    }

    private String extract(String line, String start, String end) {
        int startIndex = line.indexOf(start);
        int endIndex = line.indexOf(end, startIndex + start.length() + 1);
        return line.substring(startIndex + start.length(), endIndex);
    }

    @Override
    public void done() {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(constructStream(lines), new AllatoriMapping(map));
            lines.clear();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new InternalError(e);
        }
        System.out.printf("done: %d%n", map.size());
    }

    private InputStream constructStream(List<String> lines) {
        return new ByteArrayInputStream(lines.stream()
                .collect(Collectors.joining("\r\n"))
                .getBytes());
    }

    @Override
    public Stream<MethodInfoPair> stream() {
        return map.values().stream()
                .flatMap(list -> list.stream());
    }

    @Override
    public Optional<MethodInfo> map(MethodInfo info) {
        return map.entrySet().stream()
                .filter(entry -> match(entry.getKey(), info.className()))
                .findFirst()
                .flatMap(entry -> filter(info, entry.getValue()));
    }

    private Optional<MethodInfo> filter(MethodInfo info, List<MethodInfoPair> list) {
        return list.stream()
                .filter(pair -> pair.matchBefore(info))
                .map(pair -> pair.map((before, after) -> after))
                .findFirst();
    }

    private boolean match(NamePair pair, String className) {
        return pair.test((before, after) -> Objects.equals(before, className));
    }

    private NamePair constructPair(Attributes attributes) {
        return new NamePair(attributes.getValue("old"), attributes.getValue("new"));
    }

    private MethodInfoPair constructMethodInfoPair(NamePair pair, Attributes attributes) {
        String beforeName = attributes.getValue("old");
        String methodName = beforeName.substring(0, beforeName.indexOf('('));
        String signature = beforeName.substring(beforeName.indexOf('('));
        MethodInfo before = new MethodInfo(pair.map((b, a) -> b), methodName, signature);
        MethodInfo after = new MethodInfo(pair.map((b, a) -> a), attributes.getValue("new"), updateSignatures(signature));
        return new MethodInfoPair(before, after);
    }

    private String updateSignatures(String signature) {
        Type[] types = Type.getArgumentTypes(signature);
        String returnType = Type.getReturnType(signature).toString();

        return TypeUtils.toDescriptor(classNameMap.getOrDefault(returnType, returnType),
                Arrays.stream(types).map(type -> type.toString())
                .map(type -> classNameMap.getOrDefault(type, type)).toArray(size -> new String[size]));
    }

    private class AllatoriMapping extends DefaultHandler {
        private NamePair pair;
        private List<MethodInfoPair> list = new ArrayList<>();
        private Map<NamePair, List<MethodInfoPair>> map;

        private AllatoriMapping(Map<NamePair, List<MethodInfoPair>> map) {
            this.map = map;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if(qName.equals("class")) {
                pair = constructPair(attributes);
            }
            else if(qName.equals("method")) {
                list.add(constructMethodInfoPair(pair, attributes));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if(qName.equals("class")) {
                map.put(pair, list);
                list = new ArrayList<>();
            }
        }
    }
}
