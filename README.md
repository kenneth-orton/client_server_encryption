# client_server_encryption
<p>This is a Client and Server app used to encrypt data using various crypto-algorithms
over a network connection. Handles files and text messages.
[Caesar Cipher, DES, 3DES, AES, AES192, AES256]</p>

## Installation 
<p>The <i>Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files</i><br>
   are required to encrypt messages using AES192 or AES256.<br><br>
   The installation of these policy files varies depending on the Operating System being used.<br> 
   A basic guide to installing the policy files is included in the installation.txt file</p>

## Windows 7
<i>*Download location for the <b> <a href="http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
>Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files</b></i></a><br>
<p>To run the jar files the keys must be in the same directory as the jar.</p>
<p>To generate your own keys compile and run CryptographicAlgorithms.java</p>

<p align="center">
   <img src="/img/server_listen.PNG" width="350px" height="425px"/> <img src="/img/ip_addr.PNG" width="300px" height="200px"/>
</p>
<p align="center">
  <img src="/img/encrypt_send.PNG" width="800" height="550"/>
</p>

