
# 编译打包
mvn clean package -Dmaven.test.skip=true

# docker 部署
docker-compose down && docker-compose up -d --build