# 镜像构建流程待优化
FROM node:8.15-alpine

MAINTAINER leonzhangxf@gmail.com

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Bundle app source
COPY .. /usr/src/app
RUN npm rebuild node-sass
# 如果本地已经安装过，则不再需要重新安装所有依赖，因为此操作极为耗时
RUN npm install
RUN npm run build

EXPOSE 10003
CMD [ "npm", "run", "start" ]