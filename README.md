# Get Most Active Cookies
Get Most Active Cookies from the command line:

```bash
Usage: MostActiveCookies [-hV] [-d=<date>] [-f=<logFile>]
Find most active cookies from your command line
  -d, --d=<date>      Input Date to search Active Cookies
  -f, --f=<logFile>   logfile path
  -h, --help          Show this help message and exit.
  -V, --version       Print version information and exit.
```

### Prerequisites for CLI tool
Maven
GraalVM installed  and configured

### Build the app / Testing with JAR file
The app uses maven as build tool. To build the app, simply run the command below:
```bash
$ mvn clean package
```
This will generate an uber-jar that contains all its dependencies:
```bash
$  java -jar  most-active-cookie-1.0-SNAPSHOT.jar --help
Usage: MostActiveCookies [-hV] [-d=<date>] [-f=<logFile>]
Find most active cookies from your command line
  -d, --d=<date>      Input Date to search Active Cookies
  -f, --f=<logFile>   logfile path
  -h, --help          Show this help message and exit.
  -V, --version       Print version information and exit.
```

### Generate native binary

Complete build step before this step.

We use [Graal VM](https://www.graalvm.org/) [native-image tool](https://www.graalvm.org/docs/reference-manual/aot-compilation/) to compile our application into compiled into a native image.
Make sure that you are using GraalVM as your `JAVA_HOME`, and the `native image` tool is installed on your machine. Then run:
```bash
$ mvn clean verify
```
It will take a couple of second to generate the native executable. Once it finished, you can run the exectable without having a JVM:
```bash
$ ./most-active-cookie --help                                                                                                                                                                                       ✔ │ 10:00:47 pm
 Usage: most-active-cookie [-hV] [-d=<date>] [-f=<logFile>]
 Find most active cookies from your command line
  -d, --d=<date>      Input Date to search Active Cookies
  -f, --f=<logFile>   logfile path
  -h, --help          Show this help message and exit.
  -V, --version       Print version information and exit.
```

### Notes
1) I used only 2 external libraries 1) Junit 2) picocli
2) Even though it is not common to generate cli tool out of java, I have used GraalVM, picoCli to get cli tool out of java.

It is built using java, [picocli](https://picocli.info/), with support to generate a native binary using [graal VM](https://www.graalvm.org/).

### Limitations
Since GraalVM is not supporting log4J, I had to comment Log4J code.
