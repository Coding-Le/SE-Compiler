echo Running Testcase 006: an input with multiple types of error.
echo ==============================================
echo The input is:
cat testcases/tc-006.infix | while read line
 do
    echo $line
done
echo ----------------------------------------------
cd bin

java Postfix < ../testcases/tc-006.infix

echo ----------------------------------------------
echo The output should be: 
cat ../testcases/tc-006.postfix | while read line
do
   echo $line
done

cd ..
echo ==============================================
