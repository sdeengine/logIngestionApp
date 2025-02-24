@echo off
set URL=http://localhost:8087/log
set DATA=script\data.json
echo Running load test...
ab -n 1000 -c 50 -p %DATA% -T "application/json" %URL%
pause