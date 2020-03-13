-- -----------------------------------------------------
-- Schema sms
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sms` DEFAULT CHARACTER SET utf8 ;
USE `sms` ;

-- -----------------------------------------------------
-- Table `sms`.`chnl_config`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sms`.`chnl_config` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL COMMENT '名称',
  `desc` VARCHAR(256) NULL COMMENT '描述',
  `type` TINYINT(1) NOT NULL COMMENT '渠道类型，参见具体枚举。0-阿里云',
  `status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '启禁用，0-禁用，1-启用',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
COMMENT = '渠道配置';


-- -----------------------------------------------------
-- Table `sms`.`chnl_template`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sms`.`chnl_template` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `chnl_config_id` INT(11) NOT NULL,
  `name` VARCHAR(64) NOT NULL COMMENT '名称',
  `code` VARCHAR(64) NOT NULL COMMENT '渠道短信模板code，如阿里云短信模板CODE',
  `content` VARCHAR(500) NOT NULL COMMENT '渠道短信模板内容，携带变量使用“${code}”的格式',
  `params` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '短信模板参数列表,多变量按顺序用英文逗号分隔,如\"name,vcode\"。无变量使用空字符。',
  `status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '启禁用，0-禁用，1-启用',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  INDEX `fk_chnl_template_chnl_config1_idx` (`chnl_config_id` ASC),
  CONSTRAINT `fk_chnl_template_chnl_config1`
    FOREIGN KEY (`chnl_config_id`)
    REFERENCES `sms`.`chnl_config` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = '渠道短信模板';


-- -----------------------------------------------------
-- Table `sms`.`client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sms`.`client` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `code` VARCHAR(64) NOT NULL COMMENT '业务方标识编号',
  `name` VARCHAR(64) NOT NULL COMMENT '名称',
  `key` VARCHAR(64) NOT NULL COMMENT '密钥',
  `desc` VARCHAR(256) NULL COMMENT '描述',
  `status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '启禁用，0-禁用，1-启用',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `code_UNIQUE` (`code` ASC))
COMMENT = '调用方';


-- -----------------------------------------------------
-- Table `sms`.`chnl_signature`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sms`.`chnl_signature` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `chnl_config_id` INT(11) NOT NULL,
  `signature` VARCHAR(64) NOT NULL COMMENT '签名',
  `desc` VARCHAR(256) NULL COMMENT '描述',
  `status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '启禁用，0-禁用，1-启用',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  INDEX `fk_signature_chnl_config1_idx` (`chnl_config_id` ASC),
  CONSTRAINT `fk_signature_chnl_config1`
    FOREIGN KEY (`chnl_config_id`)
    REFERENCES `sms`.`chnl_config` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = '短信签名';


-- -----------------------------------------------------
-- Table `sms`.`template`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sms`.`template` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `client_id` INT(11) NOT NULL,
  `chnl_signature_id` INT(11) NOT NULL,
  `chnl_template_id` INT(11) NOT NULL,
  `name` VARCHAR(64) NOT NULL COMMENT '名称',
  `template_id` VARCHAR(128) NOT NULL COMMENT '短信服务模板ID',
  `desc` VARCHAR(256) NULL COMMENT '描述',
  `usage` TINYINT(1) NOT NULL COMMENT '用途：1-验证，2-通知，3-推广',
  `status` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '启禁用，0-禁用，1-启用',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `template_id_UNIQUE` (`template_id` ASC),
  INDEX `fk_template_chnl_template1_idx` (`chnl_template_id` ASC),
  INDEX `fk_template_client1_idx` (`client_id` ASC),
  INDEX `fk_template_chnl_signature1_idx` (`chnl_signature_id` ASC),
  CONSTRAINT `fk_template_chnl_template1`
    FOREIGN KEY (`chnl_template_id`)
    REFERENCES `sms`.`chnl_template` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_template_client1`
    FOREIGN KEY (`client_id`)
    REFERENCES `sms`.`client` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_template_chnl_signature1`
    FOREIGN KEY (`chnl_signature_id`)
    REFERENCES `sms`.`chnl_signature` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = '短信服务短信模板';


-- -----------------------------------------------------
-- Table `sms`.`chnl_config_params`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sms`.`chnl_config_params` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `chnl_config_id` INT(11) NOT NULL,
  `key` VARCHAR(64) NOT NULL COMMENT '配置参数键，参见相关枚举',
  `value` VARCHAR(128) NOT NULL COMMENT '配置键对应值',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_flag` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  INDEX `fk_chnl_config_params_chnl_config1_idx` (`chnl_config_id` ASC),
  CONSTRAINT `fk_chnl_config_params_chnl_config1`
    FOREIGN KEY (`chnl_config_id`)
    REFERENCES `sms`.`chnl_config` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = '渠道配置参数列表';


-- -----------------------------------------------------
-- Table `sms`.`send_log`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sms`.`send_log` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `template_id` INT(11) NOT NULL,
  `mobile` VARCHAR(20) NOT NULL COMMENT '发送手机号',
  `params` VARCHAR(256) NOT NULL DEFAULT '' COMMENT '请求参数列表，可能为空',
  `status` SMALLINT(3) NOT NULL COMMENT '调用响应状态，200-正常，具体参见相关枚举',
  `message` VARCHAR(500) NOT NULL COMMENT '响应消息，成功默认\"OK\"，其他可直接返回渠道方信息',
  `send_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`),
  INDEX `fk_send_log_template1_idx` (`template_id` ASC),
  CONSTRAINT `fk_send_log_template1`
    FOREIGN KEY (`template_id`)
    REFERENCES `sms`.`template` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = '调用方调用日志';