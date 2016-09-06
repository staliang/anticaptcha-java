You need a Java 1.8. 
For IntelliJ IDEA users: open a project that is in IDEA_and_Eclipse_compatible_project dir.

For command line users:

1. Make sure you are using Java 1.8 (To switch to 1.8 Mac users may run next command: "export JAVA_HOME=`/usr/libexec/java_home -v 1.8`", if you have installed Java 1.8 before)
2. Go to command_line_project dir: "cd command_line_project"
3. Compile:
	javac -cp "./*" Main.java AnticaptchaTask.java AnticaptchaResult.java AnticaptchaApiWrapper.java AntigateHttpRequest.java AntigateHttpResponse.java AntigateHttpHelper.java

4. Run:
	java -cp "./*" Main
