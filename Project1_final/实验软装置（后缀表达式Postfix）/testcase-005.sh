echo Running Testcase 005: an input with multiple errors.
echo ==============================================
echo The input is:
cat testcases/tc-005.infix | while read line
 do
    echo $line
done
echo ----------------------------------------------
cd bin

java Postfix < ../testcases/tc-005.infix

echo ----------------------------------------------
echo The output should be: 
cat ../testcases/tc-005.postfix | while read line
do
   echo $line
done

cd ..
echo ==============================================
