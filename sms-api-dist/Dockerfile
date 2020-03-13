# 部署单元 docker 镜像构建文件配置

FROM openjdk:8-jdk-alpine

MAINTAINER leonzhangxf@gmail.com

VOLUME /tmp /tmp

ARG DEPENDENCY=target/dependency

# 拷贝依赖
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib

# 拷贝meta信息
COPY ${DEPENDENCY}/META-INF /app/META-INF

# 拷贝项目字节码文件等信息
COPY ${DEPENDENCY}/BOOT-INF/classes /app

# 启动程序
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-cp","app:app/lib/*","com.leonzhangxf.sms.SMSAPIDistApplication"]