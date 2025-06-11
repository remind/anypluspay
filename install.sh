#!/bin/sh

# 定义变量
APP_NAME="anypluspay-account"
VERSION="latest"
DOCKER_REGISTRY="" # 可选，如果是私有仓库可以加上，如 "registry.example.com/"
PORT=8082 # 应用端口
DOCKER_PORT=8082 # 容器映射端口

# 1. 构建Spring Boot项目
#echo "开始构建Spring Boot项目..."
#./mvnw clean package -DskipTests || { echo "项目构建失败"; exit 1; }

cd app/account || { echo "account项目目录不存在"; exit 1; }

./build.sh  || { echo "构建失败"; exit 1; }