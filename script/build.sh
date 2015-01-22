#!/bin/bash

# This script was created by Milan Dojchinovski <http://dojchinovski.mk>
# Contact me by an email <dojcinovski.milan@gmail.com> or on Twitter at @mici
#
# Feel free to use and adapt it to your needs!

############## Variables ################

echo "Compiling the plugin ..."
cd ..
mvn package

echo "Copying compiled jar ..."
cp target/Entityclassifier.eu_NER-LIGHT-1.0.jar Entityclassifier.eu_NER-LIGHT-1.0.jar 

rm file.xml
echo "Creating creole.xml document."
printf '%s\n' '<?xml version="1.0"?>' >> creole.xml
printf '%s\n' '<CREOLE-DIRECTORY>' >> creole.xml
printf '%s\n' '    <JAR scan="true">Entityclassifier.eu_NER-LIGHT-1.0.jar</JAR>' >> creole.xml
printf '%s\n' '</CREOLE-DIRECTORY>' >> creole.xml


echo "The plugin is prepared for use!"

