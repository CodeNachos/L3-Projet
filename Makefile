

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
	mkdir -p temp
	cp -r Onitama/libs/json-simple-1.1.1.jar temp/
	cd temp && jar xf json-simple-1.1.1.jar
	jar cfm Onitama.jar MANIFEST.MF -C out . -C temp .
	rm -rf temp

run-jar: jar
	# java -jar Onitama.jar -classpath ./Onitama/libs/json-simple-1.1.1.jar
	java -jar Onitama.jar
add-jar:
	jar uf L3-Projet.jar -C . Onitama/res/background
	jar uf L3-Projet.jar -C . Onitama/res/background/tower.jpeg
	jar uf L3-Projet.jar -C . Onitama/res/Sprites/Title_byMayaShieda.png


clean:
	rm -rf out Onitama.jar

list:
	jar tvf Onitama.jar 