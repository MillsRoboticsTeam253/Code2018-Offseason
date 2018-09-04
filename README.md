# Code 2018 Offseason
This functions as a test and development repo for team 253's 2018 offseason code. 

## Changes from Code2018
In preparation for the new 2019 WPILib control system, we have moved to using Intellij as an IDE and GradleRIO for robot code deployment. 

## Getting Started
Once you have the code on your machine (follow the instructions below this section), you can open the file named `Code2018.ipr` in IntelliJ. It should take a moment to import the Gradle project. Once it is complete, you can use the keyboard shortcut `Alt+1` to open the project window.

### Dependencies
- [JDK8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
- [IntelliJ](https://www.jetbrains.com/idea/)
- [FRC Update Suite](http://www.ni.com/download/first-robotics-software-2017/7183/en/) (If it asks for a password to unzip, `pLaY&4%R3aL!` should work. You do not need to activate anything to make this work so ignore that.


Install all dependencies before following any further instructions.

### Installing and Using Git
1. First, create a folder for this project. I personally use the one IntelliJ generates, located at `YOUR_USER_FOLDER/IdeaProjects`, (where `YOUR_USER_FOLDER` is your user directory).

2. Open your native command line processor. On MacOS this will be Terminal; and on Windows, either cmd or Powershell. 

3. Navigate to the folder you created in step 2 using the command line. Examples for Windows and MacOS below. If you need further assistance, you can look up the "change directory" command for your operating system.
	- **MacOS**: `cd \IdeaProjects\`
	- **Windows**: `cd C:\Users\YOUR_USER_FOLDER\IdeaProjects`

4. Finally, run: `git clone https://github.com/MillsRoboticsTeam253/Code2018-Offseason.git`. 
This should fetch the code from this repo and download it to the folder you navigated to in the previous step.

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

## Code Structure
#### The code is structured as follows:
- Sources root is src
    - Skips through empty folders until team253
    - Splits into:
    	- bobabots  - Reusable code that will be carried on to next year's robot
		- contains folders to organize code
	- robot - code that is specifically for this year's robot
		- subsystems - contains individual packages for each subsystem
			- inside each package is a constants class, a subsystem class, and a folder containing all associated commands
		- utilities - various utilities
		- Robot - the main robot class
		- OI - class to put code that deals with the operator interface
- Convention
	- All classes are in UpperCamelCase, except commands, because we're not one for following convention at 253.
