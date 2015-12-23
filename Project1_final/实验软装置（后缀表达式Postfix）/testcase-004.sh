echo Running Testcase 004: missing an operand.
echo ==============================================
echo The input is:
cat testcases/tc-004.infix | while read line
   do 
echo $line
done
echo ----------------------------------------------
cd bin

java Postfix < ../testcases/tc-004.infix

echo ----------------------------------------------
echo The output should be: 
cat ../testcases/tc-004.postfix | while read line
   do 
    echo $line
    done

cd ..
echo ==============================================

