#!/bin/sh
rm -rf ./*.class
rm ModelSim.jar
javac ./*.java
jar -cfe ModelSim.jar Main ./*.class
java -jar ModelSim.jar
