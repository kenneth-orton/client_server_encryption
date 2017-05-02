# client_server_encryption
<p>This is a Client and Server app used to encrypt data using various crypto-algorithms
over a network connection. Handles files and text messages.
[Caesar Cipher, DES, 3DES, AES, AES192, AES256]</p>

## Installation 
<p>The <i>Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files</i><br>
   are required to encrypt messages using AES192 or AES256.<br><br>
   The installation of these policy files varies depending on the Operating System being used.<br> 
   A basic guide to installing the policy files is included in the installation.txt file</p>

<i>*Download location for the <b> <a href="http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html">
Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files</b></i></a><br>

### Windows 7 - Java 8
Unzip files and copy/paste files from UnlimitedJCEPolicyJDK8/ folder into C:\Program Files\Java\jdk1.8.0_101\lib\security\

### Linux - Java 8
Unzip files and copy/paste files from UnlimitedJCEPolicyJDK8/ folder into /home/user/jdk1.8.0_101/jre/lib/security/

<i>**directories and Java versions may differ depending on your installation</i>

#### To run the jar files the keys must be in the same directory as the jar. <br> To generate your own keys compile and run CryptographicAlgorithms.java

<p align="center">
   <img src="/img/server.PNG" width="800px" height="600px"/>
</p>
<p align="center">
  <img src="/img/client_server.PNG" width="800" height="600"/>
</p>

