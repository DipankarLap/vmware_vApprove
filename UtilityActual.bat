if exist "%temp%\test-output\testng-results.xml" (
del %temp%\test-output\testng-results.xml
echo "found"
) 
%AUTOMATION_PATH:~0,2%
cd %AUTOMATION_PATH:~0,-1%
set Project_Name=vApprove
echo PROJECT NAME : %Project_Name%
::set AUTOMATION_PATH: %Automation_Path%
set path=%path%;%JAVA_HOME%\bin;
set CLASSPATH=.;%AUTOMATION_PATH%Resources\*;%AUTOMATION_PATH%%Project_Name%\bin;%AUTOMATION_PATH%Resources\Excel_Jars\*;.;

java -classpath %AUTOMATION_PATH%Resources\jars\*;%AUTOMATION_PATH%Resources\MobileFramework.jar;%AUTOMATION_PATH%%Project_Name%\bin;.; org.testng.TestNG -d %temp%\test-output %TestNG_SuiteFilePath%
cd %Automation_Path%%Project_Name%\ANT\apache-ant-1.9.3\bin 
ant -Dxmlfile=%TestNG_SuiteFilePath% -DEmailTo="%EmailTo%"
	
pause
exit

