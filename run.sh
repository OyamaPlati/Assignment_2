#!/bin/bash

javac DamBSTApp.java
javac DamAVLApp.java

for ((i=0;i<=212;i++))
do
	echo "Output iteration $i"
	head -n $i Dam_Levels_Individual_Nov2015-Mar2016-1.csv > results.csv

	tail -n +2 results.csv | while read p; do 
	
		IFS=',' read -r -a array <<< "$p"

		java DamBSTApp ${array[2]}
		java DamAVLApp ${array[2]}

	done
done

echo "Experiment done"


function average () {
	echo $2 
	count=0;
	total=0;

	awk '{total += $1; count++} END { if (count > 0) print total/count}' $1 
}

function minimum () {
	echo $2
	cut -f1 $1 | sort -n | head -1 	
} 

function maximum () {
	echo $2
	cut -f1 $1 | sort -n | tail -1
}

echo "Minimum (Best case), maximum (Worst case) and average" 
echo "Binary Search Tree (BST)"
#
# For Binary Search Tree
#
echo "Cases: Search ()"
# 
# The Search method 
# 
minimum OpBSTSearchReport.dat "Best case" 
maximum OpBSTSearchReport.dat "Worst case"
average OpBSTSearchReport.dat "Average case"
echo "Cases: Insert ()"
#
# The Insert method 
# 
minimum OpBSTInsertReport.dat "Best case" 
maximum OpBSTInsertReport.dat "Worst case"
average OpBSTInsertReport.dat "Average case"
echo "AVL Tree"
#
# For AVL Tree
#
echo "Cases: Search ()"
# 
# The Search method 
# 
minimum OpAVLSearchReport.dat "Best case" 
maximum OpAVLSearchReport.dat "Worst case"
average OpAVLSearchReport.dat "Average case"
echo "Cases: Insert ()"
#
# The Insert method 
# 
minimum OpAVLInsertReport.dat "Best case" 
maximum OpAVLInsertReport.dat "Worst case"
average OpAVLInsertReport.dat "Average case"
