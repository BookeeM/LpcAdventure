# LpcAdventure
Projet secondaire ISN Lycée Pape Clément 2019-2020

## SETUP

git clone https://github.com/BookeeM/LpcAdventure.git

Go to "local.properties" and enter your path to android SDK

Open a CMD in the project root and type "gradlew build"

Then you just have to import with gradle the project into your IDE

## LAUNCH

Find the DesktopLauncher class in desktop, right click on it and run as Java Application

If this is not working right click on DesktopLauncher > Run as ... > Run Configurations.
In the "Main" tab, search for project and select desktop project. Then, in Main Class, select DesktopLauncher.
It should be working

## BUILD

./gradlew desktop:dist -> Generate an executable jar in desktop/build/libs

You need to use InteliJ or Android studio to export it as APK, see:https://libgdx.badlogicgames.com/documentation/gettingstarted/Packaging.html#packaging-for-android
