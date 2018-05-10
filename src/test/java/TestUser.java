import com.uncub.blog.dao.CustomUserMapper;
import com.uncub.blog.dao.base.UserMapper;
import com.uncub.blog.dto.base.User;
import com.uncub.blog.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:spring-application.xml"}) //加载配置文件
public class TestUser {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CustomUserMapper customUserMapper;
    @Autowired
    DefaultSecurityManager securityManager;

    @Autowired
    UserService userService;

    @Test
    public void test(){
        /*userService.queryUser(new User());
        userService.queryUser(new User());*/

        String machineName = null;
        try {
            machineName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(machineName);
    }

    @Test
    public void test1(){
        customUserMapper.queryPermissionByUserId(1);
    }

    @Test
    public void testLogin(){
        SecurityUtils.setSecurityManager(securityManager);
        //3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("tuwh", "123456");
        token.setRememberMe(true);

        try {
            //4、登录，即身份验证
            subject.login(token);
            Assert.assertTrue(subject.isRemembered());
        } catch (AuthenticationException e) {
            //5、身份验证失败
            e.printStackTrace();
        }
    }
}
