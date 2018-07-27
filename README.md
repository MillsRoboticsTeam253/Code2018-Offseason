# Code 2018 Offseason
This functions as a test and development repo for team 253's 2018 offseason code. 

## Changes from Code2018
In preparation for the new 2019 WPILib control system, we have moved to using Intellij as an IDE and GradleRIO for robot code deployment. 

## Getting Started
Once you have a version of the code on your machine (follow the instructions below this section), you can install [IntelliJ](https://www.jetbrains.com/idea/). Open the file named `Code2018.ipr` in IntelliJ. It should take a moment to import the Gradle project. Once it is complete, you can use the keyboard shortcut `Alt+1` to open the project window.

### Dependencies
- **Java**: Your machine will need Java, specifically [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
- **Gradle**: Your machine will also need [Gradle](https://gradle.org/install/). 
- **Git**: While it is not necessary, you can also install a GUI for Git, such as GitKraken. If you wish to to so, you will still need to install Git. Instructions are below.
- **IntelliJ**: Other IDEs, such and Eclipse and VS Code will also work, but this code is targeted toward users of [IntelliJ](https://www.jetbrains.com/idea/).

### Installing and Using Git
To get started, you'll first have to install Git on your machine. Open [this link](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git), navigate to the instruction section for your machine's OS, and follow the instructions there. Restart your machine if you want to be safe.

Then, create a folder for this project. I personally use the one IntelliJ generates, located at `user/IdeaProjects`, (where `user` is your user directory) and create a folder inside named `Code2018`.

Finally, open your native command line processor. On MacOS this will be Terminal; and on Windows, cmd (I prefer Powershell but you can use either). 

Here is where things will get difficult. Due to the various places your project folder could be, I cannot give instructions that will work for everyone. I also do not have a Mac and cannot attest to the functionality of this next step. However, for the sake of consistency, I will assume you have your project folder in `user/IdeaProjects/Code2018` Run the command `cd /IdeaProjects/Code2018` on Mac, and `cd C:\Users\user\IdeaProjects\Code2018` on Windows.

Finally, run `git clone https://github.com/MillsRoboticsTeam253/Code2018-Offseason.git`. This should fetch the code from this repo and download it to the folder you navigated to in the previous step.

You should now have a (hopefully) functioning version of this year's code. Get coding!

### Building and Deploying to the Robot

#### Building
To test to see if your code compiles, navigate back to your project directory (you can find instructions for that above). Then, run the command `./gradlew build`. You can also create your own IntelliJ run configuration but that will not be covered here. If this is the first time you are running this on your machine, it should take a while to install dependencies. 

If it completes with the message `BUILD FAILED in # seconds`, your code does not compile and you have an error somewhere. Follow the stacktrace back to the faulty code or look through IntellJ for errors.

If it completes with the message `BUILD SUCCESSFUL in # seconds`, your code compiles. This means your code does not have any errors. However, code that compiles can still have errors at runtime, so make sure to test all your code.

#### Deploying
Before deploying, make sure your code builds with the instructions above.

To deploy your code, you will need access to the robot. Connect to the robot's router over WiFi from your machine, navigate to the project directory (again, instructions above), and run the command `./gradlew deploy`. It will connect to the robot and then deploy code. 

If you get a `BUILD FAILED in # seconds` error, most likely your computer has not yet connected to the robot. Wait a few seconds and try again. 

Once your code deploys to the robot, you are ready to test it. You will *need* a Windows machine or the team DriverStation, with the FRC Update Suite installed, to test.
