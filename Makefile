

run: build
	java -cp out:Onitama/libs/* Onitama.src.Main

build:
	javac -sourcepath ./ ./Onitama/src/Main.java -cp ./Onitama/libs/*.jar -d out

# https://stackoverflow.com/questions/12357136/reference-jars-inside-a-jar
# Conclusion for jar: this won't work because we can't embed a jar inside a
# jar, unless using some obscur stuff

# Currently, the jar method will only work if jar libs are in the current directory
# when calling java
jar:
	# jar cfe Onitama.jar Onitama.src.Main -C out .
	jar cfm Onitama.jar MANIFEST.MF -C out . -C Onitama/libs .

run-jar: jar
	# java -jar Onitama.jar -classpath ./Onitama/libs/json-simple-1.1.1.jar
	java -jar Onitama.jar


clean:
	rm -rf out Onitama.jar

list:
	jar tvf Onitama.jar 