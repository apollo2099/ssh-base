@echo off
cd /d %~p0
del /S /F /Q *.jar
mvn clean dependency:copy-dependencies -DoutputDirectory=src/main/webapp/WEB-INF/lib -DincludeScope=runtime compile