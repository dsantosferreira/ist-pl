#!/bin/sh

rm *.class ./**/*.class
javacc ParserL0.jj
javac *.java
