#! /bin/sh

# java -jar ../sufbo/build/libs/sufbo.jar extract -a dw-jdbc -v 0.4 -g groupid $1
java -cp target/bydi-1.0-SNAPSHOT.jar jp.ac.kyoto_su.ise.tamadalab.bydi.extractor.Main $1

