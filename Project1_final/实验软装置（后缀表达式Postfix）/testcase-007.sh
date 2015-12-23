echo Running Testcase 007: an input with repetitive errors.
echo ==============================================
echo The input is:
cat testcases/tc-007.infix | while read line
 do
    echo $line
done
echo ----------------------------------------------
cd bin

java Postfix < ../testcases/tc-007.infix

echo ----------------------------------------------
echo The output should be: 
cat ../testcases/tc-007.postfix | while read line
do
   echo $line
done

cd ..
echo ==============================================
