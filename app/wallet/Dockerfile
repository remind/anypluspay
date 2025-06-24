# java 17基础镜像
# 默认
FROM m.daocloud.io/docker.io/eclipse-temurin:17.0.15_6-jdk

# mac 使用
#FROM arm64v8/openjdk:17

#配置时区，校正后续容器日志时间
ENV TZ=Asia/Shanghai

ENV LANG=C.UTF-8

# 配置nacos地址和空间，可在run时覆盖
ENV NACOS_HOST=localhost
ENV NACOS_NAMESPACE=local

# 创建目录
RUN mkdir /root/app -p

#拷贝java项目包
COPY ./app/bootstrap/target/anypluspay-wallet.jar /root/app/anypluspay-wallet.jar

# 挂载日志目录
VOLUME ["/root/app/logs"]

#进入工作目录
WORKDIR /root/app

#镜像需要暴露的端口
EXPOSE 8078

#入口，java项目的启动命令
CMD ["java","-jar","/root/app/anypluspay-wallet.jar"]