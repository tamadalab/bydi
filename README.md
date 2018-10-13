# bydi

The product aims to understand how to obfuscate the bytecode.
Therefore, `bydi` prints Java bytecode differences by levenshtein distance.


At first, we should extract bytecodes list from jar file, like below.
After typing the below command, the product print bytecode list of each method to standard output by the csv formats.

```sh
$ java -cp target/bydi-1.0-SNAPSHOT.jar jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.Main <JARFILE>
```

Then, we obtain two csv files (typically, before and after obfuscation).
The differences are printed by the following command.

```sh
$ java -jar target/bydi-1.0-SNAPSHOT.jar <CSV1> <CSV2>
```

`bydi` prints the bytecode distance between the two methods.
The two methods are identified by the class name, method name, and method signatures.
If the identifier renaming method was applied, we should restore to the old name by some method.

## Requirements

* Runtime
    * Java 11 and after
* Compile
    * Maven 3
* Libraries
    * [Apache commons BCEL 6.2](http://commons.apache.org/proper/commons-bcel/)
    * JUnit 4.8.1 for Test
