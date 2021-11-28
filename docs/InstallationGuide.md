# Requirements

This document provides a step-by-step on how to install and configure the
different tools required for building and running the application. In detail, one 
would need to install and configure the following:

- Java 
- Java EE Application Server WildFly (https://wildfly.org)
- Eclipse for Java EE developers

## Java 

[Oracle JDK v8u212-b10](https://github.com/frekele/oracle-java/releases/tag/8u212-b10)
- [Linux](https://github.com/frekele/oracle-java/releases/download/8u212-b10/jdk-8u212-linux-x64.tar.gz)
- [MacOS](https://github.com/frekele/oracle-java/releases/download/8u212-b10/jdk-8u212-macosx-x64.dmg)
- [Windows](https://github.com/frekele/oracle-java/releases/download/8u212-b10/jdk-8u212-windows-x64.exe)

### Installation

On Windows execute the `jdk-8u212-windows-x64.exe`.

On MacOS execute the `jdk-8u212-macosx-x64.dmg`.

On Unix-based Operating System (e.g., Linux) unpack the `jdk-8u212-linux-x64.tar.gz`.

### Configuration

On Unix-based Operating System (i.e., Linux, Solaris and Mac OS X) add the
unpacked distribution's directory to your user PATH environment variable by
executing the following commands:
```bash
# Unix / Linux
export JAVA_HOME=<__installed_distribution_directory__>
export PATH=$JAVA_HOME/bin:$PATH
java -version # expected to return javac 1.8.0_212

# MacOS
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_212.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH
java -version # expected to return javac 1.8.0_212
```
Note: the exported environment variables only live in the current session of
your terminal window. If you open a new terminal, restart the computer, etc, you
would need to export the variables again. To avoid that, you can copy the two
`export` commands to the bottom of your `~/.bashrc` file. Then, every time a new
terminal window is created, or even if you restart your computer, the Java
available to the system would be the one defined in the export command.

On Windows, add the distribution's directory to your user PATH environment
variable by opening up the system properties (WinKey + Pause), selecting the
"Advanced" tab, and the "Environment Variables" button, then adding to the PATH
variable in the system variables the value, e.g.,
`C:\<__installed_distribution_directory__>\jdk1.8.0_212.jdk\bin`. Make sure the
new entry is the first one in the list. Otherwise, another Java installation
might be loaded by Windows instead. Then, open a new command prompt (Winkey + R
then type cmd) and run `java -version` to verify the installation.



## WildFly

[WildFly](https://wildfly.org) is a flexible, lightweight, managed application
runtime that helps you build amazing applications.

### Installation

[Java EE Full & Web Distribution v10.0.0.Final](https://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.zip)

Unzip the downloaded file and navigate to the bin directory, e.g.,
`cd wildfly-10.0.0.Final/bin`.

### Configuration

- Start a server by either running `./standalone.sh` on Unix / Linux / MacOS or
  `standalone.bat` on Windows
- Open a web browser and go to `http://localhost:8080`. This page includes links
  to online documentation, quick start guides, forums and the administration
  console.


## Eclipse

[Eclipse Oxygen](https://www.eclipse.org/downloads/packages/release/oxygen/3a)
- [Unix / Linux](https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/oxygen/3a/eclipse-jee-oxygen-3a-linux-gtk-x86_64.tar.gz)
- [MacOS](https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/oxygen/3a/eclipse-jee-oxygen-3a-macosx-cocoa-x86_64.dmg)
- [Windows](https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/oxygen/3a/eclipse-jee-oxygen-3a-win32-x86_64.zip)


### Installation

On Windows unpack the `eclipse-jee-oxygen-3a-win32-x86_64.zip`.

On MacOS execute the `eclipse-jee-oxygen-3a-macosx-cocoa-x86_64.dmg`.

On Unix-based Operating System (e.g., Linux) unpack the `eclipse-jee-oxygen-3a-linux-gtk-x86_64.tar.gz`.


### JBoss Tools Plugin

[JBoss Tools Plugin](https://tools.jboss.org) provides, among others,
integration between Eclipse and WildFly. More info in
[here](https://www.baeldung.com/eclipse-wildfly-configuration).

#### Installation

- Open Eclipse
- Click on Help and then on Eclipse Marketplace
- Search for JBoss Tools
- Click Install next to the JBoss Tools solution, e.g., JBoss Tools 4.5.3.Final (~10 minutes)
- Follow the rest of the wizard

#### Configuration

- Open Eclipse
- Click on Window -> Show View -> Other
- Select on Server / Servers and then Open
- In the new Servers tab, click `No servers are available. Click this link to create a new server...`
- In the next step, expand the JBoss Community category and select the WildFly
  version that matches with the WildFly installation (i.e., "WildFly 10.x"). For
  the rest, simply follow the wizard.


### Dali Java Persistence Tools

[Dali Java Persistence Tools](https://www.eclipse.org/webtools/dali) provides
extensible frameworks and tools for the definition and editing of
Object-Relational (O/R) mappings for Java Persistence API (JPA) entities. JPA
mapping support focuses on minimizing the complexity of mapping by providing
entity generation wizards, design-time validation, and a rich UI for entity and
persistence unit configuration.

#### Installation

- Open Eclipse
- Click on Help and then on Install New Software...
- Add
  - Name: Releases
  - Location: http://download.eclipse.org/releases/oxygen
  - OK
- Search for `Dali`
- Select all `Dali Java Persistence Tools` and follow the rest of the wizard

Congratulations! All requirements have been properly installed and configured.

# Demo

This part of the document provides a step-by-step demonstration example to all software that was previously installed and configured.

On Eclipse, import the given project with, File -> Import -> Maven -> Existing Maven Projects 
and follow the rest of the wizard.

After importing the project, check the JDK version of `desporgest-web-client` and
and `desporgest-gui-client`. To do so, click on `desporgest-web-client` project
-> `Libraries` tab, and verify the `JRE System Library`. If it is not pointing
to the version installed and configured update it with the `Edit` option.
Repeat this check for `desporgest-gui-client`.


## Compile the main module and all sub-modules

After importing the project to Eclipse, all modules might not be compiled by
default (it depends on your configurations). To do so:

- Right click on the main module, i.e., `desporgest4ds` project
- Run As -> Maven clean
- Run As -> Maven build... and in Goals write "package" and then press Run
  (The stdout of this command is redirected to the `Console` tab. In case
  Eclipse managed to successfully compile all modules, you should see.)

Analyse the stdout of the last command in the `Console` tab and verify that the
compilation went well, look for the following message "BUILD SUCCESS" at the
end of the stdout.

### Maven vs Eclipse

Eclipse's workspace is not always synced with a maven build. I.e., it might
happen that `Run As -> Maven build...` managed to compile the project
successfully, but Eclipse's workspace complains that there are some compilation
errors. To force Eclipse to sync with the latest maven build, execute the
following set of steps:

- Right click on the main module, i.e., `desporgest4ds` project
- Maven -> Update Project
- Select all `desporgest4ds` modules and then OK

If maven does manage to compile the `desporgest` project and the Eclipse's
workspace keeps reporting compilation issues (even after executing the above
steps), remove the project and re-import it to Eclipse.


## WebClient

Once all projects compile with success, run the webclient on Eclipse:

- Right click on `desporgest-web-client` project
- Then Run As -> Run on Server

Note: The system uses the server database in our local network and you need to be either at FCUL or be connected through VPN.

Note: The first Eclipse asks to configure a server, more information on that
in [here (step-4)](https://www.baeldung.com/eclipse-wildfly-configuration#configuring-the-application-server-in-eclipse).
Select `WildFly 10x` as it is the version installed and configured as described
before.

If everything goes well, Eclipse should open a webview and load
http://localhost:8080/desporgest-web-client/.


## GUIClient

To run the `desporgest-gui-client` execute the following on the command line:

On Windows:
```
cd __path_to_WildFly_installation__\bin
appclient.bat __path_to_css_1819_meta5_project\desporgest-ear\target\desporgest-ear-1.0.ear#desporgest-gui-client.jar
```

On Unix-based Operating System (i.e., Linux, Solaris and Mac OS X):
```bash
cd __path_to_WildFly_installation__/bin
./appclient.sh __path_to_desporgest4ds_project__/desporgest-ear/target/desporgest-ear-1.0.ear#desporgest-gui-client.jar
```

A graphical user interface of the client should appear in your screen after a
few seconds. If not, try to understand the messages reported by the `appclient`
script executed in this last step and try to fix the issue.

