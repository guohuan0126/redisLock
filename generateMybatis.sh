#!/bin/bash
SHELL_FOLDER=$(cd "$(dirname "$0")";pwd)
echo $SHELL_FOLDER
rm -f $SHELL_FOLDER/../demo/src/main/java/com/redis/lock/demo/entity/*
rm -f  $SHELL_FOLDER/../demo/src/main/java/com/redis/lock/demo/mapper/*
rm -f  $SHELL_FOLDER/../demo/src/main/resources/mybatis/com/redis/lock/demo/mapper/*

cd $SHELL_FOLDER/../demo/
./gradlew mybatisGenerate

echo "mybatisGenerate package success!"
