# coc-manager
A JavaFX Clash of Clan, Clan manager

# Developers 

Install javafx in maven local repository.
- Get the javafx version in your JRE : 
  	cat /Library/Java/JavaVirtualMachines/jdk1.8.0_51.jdk/Contents/Home/jre/lib/javafx.properties
- Locate javafxrt.jar on your system (/Library/Java/JavaVirtualMachines/jdk1.8.0_51.jdk/Contents/Home/jre/lib/ext on mac)
- Install the file:
	mvn install:install-file -Dfile=jfxrt.jar -DgroupId=com.oracle -DartifactId=javafx -Dpackaging=jar -Dversion=8.0.51
- Build with:
	mvn package
- Launch with
	java -jar target/coc-manager-*.jar



