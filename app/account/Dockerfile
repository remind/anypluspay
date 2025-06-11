#指定jdk基础镜像
#FROM openjdk:17-slim
FROM arm64v8/openjdk:17

#配置时区，校正后续容器日志时间
ENV TZ=Asia/Shanghai

ENV LANG=C.UTF-8

# 创建目录
RUN mkdir /root/app -p

#拷贝java项目包
COPY ./app/bootstrap/target/anypluspay-account.jar /root/app/anypluspay-account.jar

# 挂载日志目录
VOLUME ["/root/app/logs"]

#进入工作目录
WORKDIR /root/app

#镜像需要暴露的端口
EXPOSE 8082

#入口，java项目的启动命令
CMD ["java","-jar","/root/app/anypluspay-account.jar","--spring.profiles.active=prod"]