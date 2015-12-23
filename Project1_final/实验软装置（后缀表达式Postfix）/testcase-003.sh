echo Running Testcase 003: missing an operator.
echo ==============================================
echo The input is:
cat testcases/tc-003.infix | while read line
   do 
     echo $line
  done
echo ----------------------------------------------
cd bin

java Postfix < ../testcases/tc-003.infix

echo ----------------------------------------------
echo The output should be: 
cat ../testcases/tc-003.postfix | while read line
    do 
       echo $line
    done

cd ..
echo ==============================================

