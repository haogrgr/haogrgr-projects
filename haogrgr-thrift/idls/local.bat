@echo off
echo --------------start gen idls---------------
thrift -out ../src/main/java/ -r --gen java shared.thrift
thrift -out ../src/main/java/ -r --gen java tutorial.thrift
echo --------------idls done---------------