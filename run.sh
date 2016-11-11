#!/usr/bin/env bash

# example of the run script for running the fraud detection algorithm with a python file,
# but could be replaced with similar files from any major language

# I'll execute my programs, with the input directory paymo_input and output the files in the directory paymo_output
# python ./src/antifraud.py ./paymo_input/batch_payment.txt ./paymo_input/stream_payment.txt ./paymo_output/output1.txt ./paymo_output/output2.txt ./paymo_output/output3.txt

mkdir classes

javac -d classes src/sv/antifraud/*.java
java -classpath classes sv.antifraud.FeatureOne paymo_input/batch_payment.csv paymo_input/stream_payment.csv paymo_output/output1.txt
java -classpath classes sv.antifraud.FeatureTwo paymo_input/batch_payment.csv paymo_input/stream_payment.csv paymo_output/output1.txt
java -classpath classes sv.antifraud.FeatureThree paymo_input/batch_payment.csv paymo_input/stream_payment.csv paymo_output/output1.txt
