cd %1
call mvn eclipse:clean && mvn -Dwtp.version=3.5.1 eclipse:eclipse