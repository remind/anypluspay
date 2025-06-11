#!/bin/sh

# 定义变量
APP_NAME="anypluspay-account"
VERSION="latest"
NETWORK="my_network"
DOCKER_REGISTRY="" # 可选，如果是私有仓库可以加上，如 "registry.example.com/"
PORT=8082 # 应用端口
DOCKER_PORT=8082 # 容器映射端口

# 1. 检查构建结果
JAR_FILE=$(find app/bootstrap/target -name "*.jar" | head -n 1)
if [ -z "$JAR_FILE" ]; then
    echo "错误：未找到生成的jar包"
    exit 1
fi
echo "找到生成的jar包: $JAR_FILE"

# 2. 构建Docker镜像
echo "开始构建Docker镜像..."
docker build --build-arg JAR_FILE=$JAR_FILE -t ${DOCKER_REGISTRY}${APP_NAME}:${VERSION} . || { echo "Docker镜像构建失败"; exit 1; }

# 3. 停止并删除旧容器（如果存在）
echo "检查并清理旧容器..."
docker stop ${APP_NAME} 2>/dev/null || true
docker rm ${APP_NAME} 2>/dev/null || true

# 4. 运行新容器
echo "启动Docker容器..."
docker run -d --name ${APP_NAME} --network=${NETWORK} --restart=always -v /etc/localtime:/etc/localtime:ro -v /app/logs/${APP_NAME}:/root/app/logs -p ${DOCKER_PORT}:${PORT} ${DOCKER_REGISTRY}${APP_NAME}:${VERSION} || { echo "容器启动失败"; exit 1; }

# 5. 验证容器状态
echo "检查容器状态..."
sleep 5 # 等待容器启动
docker ps | grep ${APP_NAME} || { echo "容器未正常运行"; exit 1; }

echo "部署成功完成!"
echo "应用运行在: http://localhost:${DOCKER_PORT}"