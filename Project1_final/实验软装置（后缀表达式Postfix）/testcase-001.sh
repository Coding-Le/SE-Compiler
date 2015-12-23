echo Running Testcase 001: a correct input from DBv2.
echo ==============================================
echo The input is:
cat testcases/tc-001.infix | while read line
 do
    echo $line
done
echo ----------------------------------------------
cd bin

java Postfix < ../testcases/tc-001.infix

echo ----------------------------------------------
echo The output should be: 
cat ../testcases/tc-001.postfix | while read line
do
   echo $line
done

cd ..
echo ==============================================

