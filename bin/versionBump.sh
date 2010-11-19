#!/bin/bash

CURRENT_VERSION=$(xmlstarlet sel -N pom="http://maven.apache.org/POM/4.0.0" -t -v "/pom:project/pom:version" pom.xml)

if [ $# -ne 1 ]
then
  echo "Usage: `basename $0` <newversion>"
  echo "Current version is $CURRENT_VERSION"
  exit 1
fi

NEW_VERSION=$1

echo "Bumping $CURRENT_VERSION to $NEW_VERSION."
perl -pi -e "s/$CURRENT_VERSION/$NEW_VERSION/g" `find . -name \pom.xml`
