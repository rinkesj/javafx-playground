# Java FX Hello with OS Runtimes

## Prerequisities
1. JavaFX jmods
2. JDK jmods
3. set properties in pom.xml

## Build
Maven profiles are used to build different runtimes. Active profile is done based on os.family.
To build other profile you need to deactive activeProfile and run other profile

**mvn clean javafx:jlink && mvn javafx:jlink -P '!linux,windows'**

