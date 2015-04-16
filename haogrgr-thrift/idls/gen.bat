@echo off
echo --------------start gen idls---------------
SetLocal EnableDelayedExpansion   
cd %~dp0
FOR /D %%i IN (*) DO (IF EXIST %%i/srv.thrift ( thrift -out ../src/main/java/ -r --gen java %%i/srv.thrift) & echo %%i done)

EndLocal

echo --------------idls done---------------