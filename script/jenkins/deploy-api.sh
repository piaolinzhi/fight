#!/bin/bash
#filename deploy-api.sh
set -x
export JAVA_HOME=/usr/lib/jvm
export TOMCAT_HOME=/home/ucmed/ylpt/yilian-api/apache-tomcat-yilian-api-18480
api_pid=$(ps -ef|grep 18480|grep yilian-api|awk '{print $2}')
for temp_pid in ${api_pid}
do 
    kill -9 ${temp_pid}
done
war_file="/home/ucmed/ylpt/yilian-api/yilian-api.war"
if [ -f "$war_file" ];
then
    echo "War file exists, deploy and start the server."
    rm -rf ${TOMCAT_HOME}/webapps/ROOT/
    rm -f ${TOMCAT_HOME}/webapps/ROOT.war
    mv -f ${war_file} ${TOMCAT_HOME}/webapps/ROOT.war
else
    echo "War file not exists, restart the server."
fi
set +x
/bin/bash ${TOMCAT_HOME}/bin/startup.sh

