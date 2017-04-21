@ECHO OFF

REM === Set the location of the SDK ===
IF NOT "%JAVA_HOME%" == "" GOTO init
SET JAVA_HOME=C:\jdk1.6.0_12

:init
REM === Root directory of this product ===
SET PRODUCT_HOME=..\..\..\

REM === Make sure all the files are properly installed ===
IF NOT EXIST %JAVA_HOME%\bin\javac.exe GOTO nojavac
IF NOT EXIST %JAVA_HOME%\bin\java.exe GOTO nojava
IF NOT EXIST %JAVA_HOME%\jre\bin\rxtxSerial.dll GOTO nodll
IF NOT EXIST %JAVA_HOME%\jre\lib\ext\RXTXcomm.jar GOTO norxtx
IF NOT EXIST %PRODUCT_HOME%\lib\omnilink.jar GOTO nolib

REM === Set the CLASSPATH ===
SET CLASS_PATH=%PRODUCT_HOME%\lib\omnilink.jar

REM === Compile the test driver code ===
%JAVA_HOME%\bin\javac -classpath %CLASS_PATH% SerialTestDriver.java

REM === Execute the test driver ====
%JAVA_HOME%\bin\java -classpath .;%CLASS_PATH% SerialTestDriver

GOTO END

:nojavac
ECHO Error: %JAVA_HOME%\bin\javac.exe not found
GOTO END

:nojava
ECHO Error: %JAVA_HOME%\bin\java.exe not found
GOTO END

:nodll
ECHO Error: %JAVA_HOME%\jre\bin\rxtxSerial.dll not found
GOTO END

:norxtx
ECHO Error: %JAVA_HOME%\jre\lib\ext\RXTXcomm.jar not found
GOTO END

:nolib
ECHO Error: %PRODUCT_HOME%\lib\omnilink.jar not found
GOTO END

:END
SET PRODUCT_HOME=
SET CLASS_PATH=

