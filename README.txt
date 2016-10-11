  ==========================================================================
          Beginning Java SE 6 Platform: From Novice to Professional

                    by Jeff Friesen (jeff@javajeff.mb.ca)

  Instructions for using build.xml to build the book's code with Apache Ant.
  ==========================================================================

This archive contains the book's source and resource files. It also contains a
build.xml file that is used with Apache Ant to automate the build process for
most of the book's code, except for the following files in Appendixes B and E:

* Appendix B

Stack.java is not compiled so that you can compile this source file and view
the result of @SuppressWarnings("unchecked").

Calculator.java is not compiled so that you can compile this source file via
javac -processor StubAnnotationProcessor Calculator.java

* Appendix E

WhoIs.java is not compiled because it depends on AppFramework-0.43.jar, which
is not included in this archive to avoid potential licensing problems.

Build Instructions
------------------

Follow these steps to build all of the code:

1) Make sure the directory containing build.xml is current.
2) Invoke ant by itself at the command line

A bin directory divided into subdirectories for the chapters and appendixes is
created in the current directory.

Follow these steps to build a single chapter's or appendix's code:

1) Make sure the directory containing build.xml is current.
2) Invoke ant followed by buildx (where x is either 01, 02, ... 10, XB, XC, or
   XD) at the command line. For example, ant build10 builds Chapter 10's code.

Follow these steps to delete bin and all of its subdirectories:

1) Make sure the directory containing build.xml is current.
2) Invoke ant cleanup at the command line.

Notes
-----

1) I successfully tested build.xml with Apache Ant 1.6.5 on a Windows XP SP2
platform. No proxy server was needed to access the Internet. All of the code
was successfully built.

2) During the build process, I noticed a few warning messages resulting from
compiling the SkyView application in both Chapter 10 and Appendix D. These
warning messages have to do with non-standard web service ports (which are not
accessed by SkyView). They are not a problem.

3) To build Chapter 10's TestConverter application, it is necessary for ant to
spawn an external copy of the java tool. This copy starts the Converter web
service, which must be running when you run TestConverter. You will need to
manually kill this java tool when you are finished with TestConverter: Consult
your operating system documentation for details on how to do this. For
example, I use the Windows Task Manager to kill java on Windows XP.

Although Notes 2 and 3 might also apply to Linux and Solaris, the notes below
definitely apply to Linux and Solaris:

4) build.xml was tested with Apache Ant 1.7.0 on the Linux Ubuntu 7.04 and
Solaris 10 5/07 platforms. Java SE 6 1.6.0_02-b05 was running on Linux; Java
SE 6 1.6.0-b105 was running on Solaris. Each platform accessed the Internet
via a proxy server.

5) An early attempt to compile Chapter 5's RemoveAccents.java source code
resulted in four "unmappable character" messages on each platform. These
messages were due to a UTF-8/ISO-8859-1 encoding mismatch between the Windows
editor used to enter the source code, and the javac compiler's assumption of a
UTF-8 encoding.

These warning messages were eliminated by supplying an encoding attribute to
the <javac> task, as follows:

<javac srcdir="${src}/Chapter05/RemoveAccents"
       destdir="${dest}/Chapter05/RemoveAccents"
       encoding="iso-8859-1"/>

The javac compiler tool is given explicit information about the encoding. As a
result, the warning messages disappear on the Linux and Solaris platforms. The
RemoveAccents application runs properly on Windows XP, Linux, and Solaris with
the encoding attribute as specified.

Bug 5071879 "unmappable character for encoding UTF-8 error while using german
umlauts" (http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=5071879) in Sun's
Bug Database provides more information on this issue.

6) The wsimport tool was unable to access the 
http://casjobs.scss.org/ImgCutoutDR5/ImgCutout.asmx?wsdl WSDL URL, with or
without wsimport's -httpproxy option. (Although the JDK 6 documentation states
that -httpproxy has been removed, this is not true.) An attempt to use ant's
<setproxy> task failed as well. As a result, it was not possible to build
Chapter 10's and Appendix D's SkyView application on the Linux and Solaris
platforms to which I had access. However, you might not run into this problem
on your Linux or Solaris platform.

If you have any questions about the build process, or if you run into problems
building the code on Windows, Linux, or Solaris, please contact me at my email
address: jeff@javajeff.mb.ca.
