#!/bin/sh

# -----------------------------------------------------------------------------
# Script/command line utility to reconcile assignments for status change
#
# Environment Variable Prequisites
#
#    JAVA_HOME     Must point to your JDK.
# -----------------------------------------------------------------------------

BASEDIR=../..
CLASSPATH=$BASEDIR/config

for file in `ls $BASEDIR/lib/*.jar`;
do
if [ "$CLASSPATH" != "" ]; then
   CLASSPATH=${CLASSPATH}:$file
else
   CLASSPATH=$file
fi
done

$JAVA_HOME/bin/java -cp $CLASSPATH edu.internet2.middleware.signet.util.AssignmentReconcile $1