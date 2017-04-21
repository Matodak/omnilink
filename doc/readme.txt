HAI Omni-Link protocol implementation for the Java platform, version 3.0
////////////////////////////////////////////////////////////////////////

1- Changes since version 2.0
============================

- Added client implementation of the HAI OmniLink II Network Protocol (see the NetworkCommunication class and the ProtocolTypeEnum class for more details)
- Library fully tested against the Omni IIe and Aegis 2000 controllers.

2- Setup
========

Here are the steps to setup the build environment on the Windows and Linux platform:

   Windows
   -------

   Note that steps c) and d) are only required if using the Omni-Link protocol as a serial connection.
   
   a) Download and install the Java 2 Platform Standard Edition (J2SE) SDK version 1.4 or higher available at: 

         > http://java.sun.com/javase/downloads/index.jsp

   b) Set the JAVA_HOME environment variable to the Java SDK install directory. Here is an example:

         > set JAVA_HOME=C:\jdk1.6.0_12

   c) Download, compile, install and RXTX (version 2.1 or higher). RXTX is a set of serial and parallel libraries supporting Sun's 
      Java Communications API (version 2.0) available at:

         > http://rxtx.qbang.org/wiki/index.php/Download

   d) Make sure the following files have been installed to their proper location:

       %JAVA_HOME%\bin\javac.exe
       %JAVA_HOME%\bin\java.exe
       %JAVA_HOME%\jre\lib\ext\RXTXcomm.jar
       %JAVA_HOME%\jre\bin\rxtxSerial.dll

   Linux
   -----

   Note that steps c) and d) are only required if using the Omni-Link protocol as a serial connection.

   a) Download and install the Java 2 Platform Standard Edition (J2SE) SDK version 1.4 or higher available at: 

        > http://java.sun.com/javase/downloads/index.jsp

   b) Set the JAVA_HOME environment variable to the Java SDK install directory. Here is an example:
 
         > setenv JAVA_HOME /usr/java/jdk1.6.0_12

   c) Download, compile, install and RXTX (version 2.1 or higher). RXTX is a set of serial and parallel libraries supporting Sun's 
      Java Communications API (version 2.0) available at:

        > http://rxtx.qbang.org/wiki/index.php/Download

   d) Make sure the following files have been installed to their proper location:

       $JAVA_HOME/bin/javac
       $JAVA_HOME/bin/java
       $JAVA_HOME/jre/lib/ext/RXTXcomm.jar
       $JAVA_HOME/jre/lib/i386/librxtxSerial.so

3- Running the sample programs
==============================

Once the setup portion of this document is complete, you are ready to run the sample program:

   1- Configure and run the test driver program:

      a) Serial connection:
      
         > cd samples
         > cd testdriver
         > cd serial
         > edit SerialTestDriver.java: Change the values for the following variables:
               - SYSTEM_TYPE: Choose one of the following: AEGIS, HAI_OMNI, HAI_OMNI_PRO, HAI_OMNI_PRO_II, HAI_OMNI_LT, HAI_OMNI_II, HAI_OMNI_IIE
               - LOGIN_CODE: Put the 4 digit code that allows you to login to your Omni/Aegis system.
               - COM_PORT: Serial port on your PC that is connected to your Omni/Aegis system.
               - INIT_STRING and CONNECT_STRING: Change only if you use a modem to connect to your Omni/Aegis system
         > run

      b) Network connection:

         > cd samples
         > cd testdriver
         > cd network
         > edit NetworkTestDriver.java: Change the values for the following variables:
               - SYSTEM_TYPE: Choose one of the following: AEGIS, HAI_OMNI, HAI_OMNI_PRO, HAI_OMNI_PRO_II, HAI_OMNI_LT, HAI_OMNI_II, HAI_OMNI_IIE
               - LOGIN_CODE: Put the 4 digit code that allows you to login to your Omni/Aegis system.
               - IP_ADDRESS: Put the network IP address configured on your Omni/Aegis system.
               - PORT: Put the network port configured on your Omni/Aegis system.
               - PRIVATE_KEY: 16-bit private key configured on your Omni/Aegis system.
               - PROTOCOL_TYPE: Select the Omni-Link protocol: HAI_OMNI_LINK or HAI_OMNI_LINK_II
         > run

   2- Configure and run the console program:

      a) Serial connection:
       
         > cd samples
         > cd console
         > cd serial
         > edit SerialMain.java: Change the values for the following variables:
               - SYSTEM_TYPE: Choose one of the following: AEGIS, HAI_OMNI, HAI_OMNI_PRO, HAI_OMNI_PRO_II, HAI_OMNI_LT, HAI_OMNI_II, HAI_OMNI_IIE
               - LOGIN_CODE: Put the 4 digit code that allows you to login to your Omni/Aegis system.
               - COM_PORT: serial port on your PC that is connected to your Omni/Aegis system.
               - INIT_STRING and CONNECT_STRING: change only if you use a modem to connect to your Omni/Aegis system
         > run
             You can type 'help' to get the list of available commands. Here are a few examples of commands you can use:
               - Open a session and login to the system:
                    > open
                    > login 1234
               - Show the system status
                    > getsystemstatus  
               - Turn on unit #6
                    > setunit 6 on
               - Get status of zones 1-5:
                    > getzone 1 5         
               - Logout and close the current session
                    > logout
                    > close
               - Terminate the console program
                    > exit

      b) Network connection:
       
         > cd samples
         > cd console
         > cd network
         > edit NetworkMain.java: Change the values for the following variables:
               - SYSTEM_TYPE: Choose one of the following: AEGIS, HAI_OMNI, HAI_OMNI_PRO, HAI_OMNI_PRO_II, HAI_OMNI_LT, HAI_OMNI_II, HAI_OMNI_IIE
               - LOGIN_CODE: Put the 4 digit code that allows you to login to your Omni/Aegis system.
               - IP_ADDRESS: Put the network IP address configured on your Omni/Aegis system.
               - PORT: Put the network port configured on your Omni/Aegis system.
               - PRIVATE_KEY: 16-bit private key configured on your Omni/Aegis system.
               - PROTOCOL_TYPE: Select the Omni-Link protocol: HAI_OMNI_LINK or HAI_OMNI_LINK_II
         > run
             You can type 'help' to get the list of available commands. Here are a few examples of commands you can use:
               - Open a session and login to the system:
                    > open
                    > login 1234 (command not required/supported on Omni-Link II)
               - Show the system status
                    > getsystemstatus  
               - Turn on unit #6
                    > setunit 6 on
               - Get status of zones 1-5:
                    > getzone 1 5     
               - Logout and close the current session
                    > logout (command not required/supported on Omni-Link II)
                    > close
               - Terminate the console program
                    > exit

4- NOTES
========

   - The library is located under lib\omnilink.jar. It implements the Omni-Link specification document 
     available under doc\Omni-Link.pdf. 
   - The library also implements the Omni-Link II specification document available under doc\Omni-Link_II_Rev_3.0.pdf.
   - The javadoc is available under doc\api. As you will notice, the documentation is pretty slim for now. 
     For the time being, you can get by using both the javadoc and the specification document. 
   - There is a wrapper class that allows you to create the communication object as a RMI service:
     net.homeip.mleclerc.omnilink.CommunicationService. The remote interface is located in: 
     net.homeip.mleclerc.omnilink.MessageManagerRemote.
   - All the message classes you can send to the Omni/Aegis system are located under:
     net.homeip.mleclerc.omnilink.message. Please refer to the specification documentation for more information
     on those messages.
   - There are a few messages that I have not implemented yet. Let me know if you need any of those implemented.
 
Please let me know if you have problems setting up your development environment, running the test driver, using the library, etc...

Enjoy!

Martin Leclerc (martin.leclerc@matodak.com) 
