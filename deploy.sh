#!/bin/sh

echo "开始构建"
mvn clean package -Dmaven.test.skip=true
echo "构建完成"

# jar包目录
DIR="./target"

# 检查目录是否存在，若存在则删除
if [ -d "$DIR" ]; then
    rm -rf "$DIR"
fi

# 创建新目录
mkdir -p "$DIR"

cp ./app/admin/app/bootstrap/target/admin-bootstrap.jar "$DIR"
cp ./app/payment/app/bootstrap/target/payment-bootstrap.jar "$DIR"
cp ./app/account/app/bootstrap/target/account-bootstrap.jar "$DIR"
cp ./app/channel/app/bootstrap/target/channel-bootstrap.jar "$DIR"
cp ./app/channel-gateway/test-bank-gateway/target/test-bank-gateway-bootstrap.jar "$DIR"
cp ./app/test-bank/target/test-bank-bootstrap.jar "$DIR"

echo "复制完成"



docker build -t payment:latest .
docker tag payment:latest registry.cn-hangzhou.aliyuncs.com/anypluspay/payment:latest
docker push registry.cn-hangzhou.aliyuncs.com/anypluspay/payment:latest