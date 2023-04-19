@echo off
javac app.java
java -cp ".;sqlite-jdbc-3.41.0.0.jar" app
timeout 10