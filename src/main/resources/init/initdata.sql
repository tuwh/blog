insert into t_user (USER_NO,USER_NAME,PASSWORD,SALT,STATUS,EMAIL,PHONE_NO,CREATE_TM,CREATE_DT,UPDATE_DT,UPDATE_TM)
VALUE ('tuwh','tuwh','9AF24FA098B24593A8D089F0308043BB','123456','1','461175170@qq.com','12345562336',date_format(sysdate(),'%k%i%s'),date_format(sysdate(),'%y%m%d'),date_format(sysdate(),'%y%m%d'),date_format(sysdate(),'%k%i%s'))

insert into t_role(name,role_desc,remark) VALUE ('admin','管理员','管理员角色，全部权限');
insert into t_role(name,role_desc,remark) VALUE ('manger','运营','网站运营角色，后台内容管理权限');
insert into t_role(name,role_desc,remark) VALUE ('cust','普通用户','普通用户，普通内容页面权限');
insert into t_role(name,role_desc,remark) VALUE ('custvip','会员','会员，普通页面及会员页面');

insert into t_user_role(user_id,role_id,status,role_name) VALUE ('1','1','1','admin');
insert into t_user_role(user_id,role_id,status,role_name) VALUE ('1','2','1','manger');
insert into t_user_role(user_id,role_id,status,role_name) VALUE ('1','3','1','cust');
insert into t_user_role(user_id,role_id,status,role_name) VALUE ('1','4','1','custvip');