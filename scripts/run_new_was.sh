#!/bin/bash

CURRENT_PORT=$(cat /home/ubuntu/haksik/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running WAS is ${CURRENT_PORT}."

if [ ${CURRENT_PORT} -eq 9091 ]; then
  TARGET_PORT=9092
elif [ ${CURRENT_PORT} -eq 9092 ]; then
  TARGET_PORT=9091
else
  echo "> No WAS is connected to nginx"
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill WAS running at ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

nohup java -jar -Dserver.port=${TARGET_PORT} /home/ubuntu/haksik/build/libs/haksik-0.0.1-SNAPSHOT.jar > /home/ubuntu/haksik/nohup.out 2>&1 &
echo "> Now new WAS runs at ${TARGET_PORT}."
exit 0
