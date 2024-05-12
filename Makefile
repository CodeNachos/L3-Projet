

run: build
	java -cp out:Onitama/libs/* Onitama.src.Main

build:
	javac -sourcepath ./ ./Onitama/src/Main.java -cp ./Onitama/libs/*.jar -d out

jar:
	jar cfe Onitama.jar Onitama.src.Main -C out .

run-jar: jar
	java -jar Onitama.jar -classpath ./Onitama/libs/json-simple-1.1.1.jar


clean:
	rm -rf out Onitama.jar

list:
	jar tvf Onitama.jar 