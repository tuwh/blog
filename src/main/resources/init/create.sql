--用户表
drop table T_USER;
CREATE TABLE T_USER(
  ID INTEGER  PRIMARY KEY AUTO_INCREMENT NOT  NULL COMMENT '主键(自增长)',
  USER_NO VARCHAR(20)  NOT NULL COMMENT '用户号',
  USER_NAME VARCHAR(50)  COMMENT '用户名',
  PASSWORD VARCHAR (100)  COMMENT '密码',
  SALT VARCHAR (50)  ,
  STATUS VARCHAR (2)  COMMENT '状态',
  EMAIL VARCHAR (50) COMMENT '邮箱',
  PHONE_NO VARCHAR (15) COMMENT '手机号',
  CREATE_TM VARCHAR (6),
  CREATE_DT VARCHAR(8),
  UPDATE_DT VARCHAR (8),
  UPDATE_TM VARCHAR(6)
)
comment '用户表'
ENGINE = INNODB
  CHARSET = UTF8;

--菜单表
drop table t_resource;
CREATE TABLE t_resource
(
  id        INT AUTO_INCREMENT PRIMARY KEY,
  resource_name VARCHAR (200) NULL COMMENT '资源名',
  resource_path      VARCHAR(200) NOT NULL,
  resource_desc VARCHAR(200) NULL COMMENT '注释',
  resource_type VARCHAR (200) not null DEFAULT '1' COMMENT '资源类型: 1菜单 2按钮 3接口',
  status VARCHAR (2) not null DEFAULT '1' comment '资源状态：0无效 1有效'
) comment '资源表'
  ENGINE = InnoDB
  CHARSET = utf8;


--角色表
drop table t_role;
CREATE TABLE t_role
(
  ID        INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  name VARCHAR(100) NULL comment '角色名，全局唯一',
  role_desc VARCHAR(200) NULL comment '角色描述',
  remark VARCHAR(200) NULL comment '备注'
) comment '角色信息表'
  ENGINE = InnoDB
  CHARSET = utf8;

--角色资源表
drop table t_role_resource;
create table t_role_resource(
  id INTEGER AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  role_id INT NOT NULL COMMENT '角色id',
  resource_id INT NOT NULL COMMENT '资源id',
  status VARCHAR (2) DEFAULT '1' COMMENT '状态： 0无效 1有效',
  permission VARCHAR(300) DEFAULT 'view' COMMENT '操作权限'
)comment '角色资源表'
  ENGINE = InnoDB
  CHARSET = utf8;

--用户角色表
drop table t_user_role;
create table t_user_role(
  id INTEGER AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
  user_id INT NOT NULL COMMENT '角色id',
  role_id INT NOT NULL COMMENT '角色id',
  status VARCHAR (2) DEFAULT '1' COMMENT '状态： 0无效 1有效',
  role_name VARCHAR (100) not null comment '角色名'
)
comment '用户角色表'
  ENGINE = InnoDB
  CHARSET = utf8;




