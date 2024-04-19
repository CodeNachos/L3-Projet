
run: build
	java -cp out Jeu

build:
	javac -cp src -d out src/*.java src/*/*.java

