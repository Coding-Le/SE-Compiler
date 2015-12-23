echo Running Testcase 002: a correct long input.
echo ==============================================
echo The input is:
cat testcases/tc-002.infix | while read line
    do 
      echo $line
   done
echo ----------------------------------------------
cd bin

java Postfix < ../testcases/tc-002.infix

echo ----------------------------------------------
echo The output should be: 
cat ../testcases/tc-002.postfix | while read line
   do 
     echo $line
   done

cd ..
echo ==============================================

