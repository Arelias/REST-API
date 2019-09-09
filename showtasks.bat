call runcrud
if "%ERRORLEVEL%" == "0" goto startBrowser
echo.
echo runcrud has errors - breaking work
goto fail

:startBrowser
echo.
echo loading getTasks page
start http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end

:fail
echo.
echo There were errors

:end
echo.
echo Work is finished.
