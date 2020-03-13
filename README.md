SMS (Short Message Service)
=============================

A short message service project. 
For provider multi channel short message service.

> 短信服务

# 1.项目结构

### 后台API接口 sms-api-dist

### 后台页面 sms-manage-static

### 对外API接口 sms-open-api-dist

### 对外API接口调用SDK sms-open-api-sdk


# 2.功能列表

### 2.1 后台管理

- 短信发送必要配置(DONE)

- 短信发送记录查询(DONE)

- 短信发送数据统计(TODO)

- 短信异常报警(TODO)

### 2.2 对外接口

- 短信发送请求鉴权(DONE)

- 短信发送(DONE)

- 短信发送结果回调(TODO)

### 2.3 短信发送SDK

- 短信发送(TODO)

- 短信回调处理(TODO)


## 3.注：

### 3.1 后台认证

后台项目使用的单点认证服务请见 [KEYCLOAK](https://www.keycloak.org/)

### 3.2 数据库初始化

初始化数据库脚本在根目录下 `sms-2.0-20180828.sql`