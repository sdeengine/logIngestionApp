@echo off
setlocal enabledelayedexpansion

echo Setting up MinIO alias...
C:\Users\HP\AppData\Local\MC\mc alias set localminio https://play.min.io Q3AM3UQ867SPQQA43P2F zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG

echo Listing objects in bucket...
C:\Users\HP\AppData\Local\MC\mc  ls localminio/logdata

echo Downloading log files...
if exist temp_logs rmdir /s /q temp_logs
mkdir temp_logs
C:\Users\HP\AppData\Local\MC\mc cp --recursive localminio/logdata/ temp_logs/

echo Counting log entries...
set total=0
for /r temp_logs %%f in (*.txt) do (
    for /f "usebackq" %%l in (`type "%%f" ^| find /c /v ""`) do (
        set /a total+=%%l
    )
)
echo Total log entries: !total!

pause