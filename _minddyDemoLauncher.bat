
@ECHO OFF
TITLE _minddy
echo.
echo.
echo	 	[1;36m _minddy_dataService [1;37m
echo		[1m Your mind Buddy app
echo		 by Didac Llorens (BCN IT Academy)
echo 	 --------------
echo 	 --------------
echo.
echo.
REM

echo 		Reset demo data from DDBB...
start /min "_minddy: Reset DDBB" cmd /k call mysql -u minddy_dev -pscvriae --database=minddy_test -e "source %~dp0src\test\resources\reset.sql"
timeout 10 > nul
echo			[1;32m				.OK[1;37m
echo.
echo 		Launching springboot app...
start /min "_minddy: DataService" cmd /k call mvn spring-boot:run
timeout 10> nul
echo 			please, be patient...
timeout 10> nul
echo							[1;32m.OK 	[1;37m
echo.
timeout 1 > nul
echo 		Filling DDBB DEMO DATA...
start /min "_minddy: FILL DDBB" cmd /k call mysql -u minddy_dev -pscvriae --database=minddy_test -e "source %~dp0src\test\resources\demoCreation.sql"
timeout 5 > nul
echo						[1;32m	.OK[1;37m
echo.
timeout 1 > nul
echo.
echo.
:PROMPT
endlocal
echo.
echo.
echo.
echo 		Automated services are runing.
timeout 1 > nul
echo			Press any key to close all services.
echo.
echo.
echo.
timeout 1 > nul
pause
echo.
echo.
setlocal
SET /P AREYOUSURE="		     [1;37m Are you sure to stop all _minddy running services? [37m(Y/[N])?"

IF /I "%AREYOUSURE%" NEQ "Y" GOTO PROMPT 
echo
ELSE GOTO PROMPT

:END
endlocal
start cmd /k "%~dp0minddy_stopper.bat"
exit
