package com.ego.passport.controller;

import com.ego.commons.pojo.EgoResults;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.PassportService;
import com.ego.pojo.TbUser;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Auther: Elton Ge
 * @Date: 8/8/20
 * @Description: com.ego.passport.controller
 * @version: 1.0
 */
@Controller
public class PassportController {
    @Autowired
    private PassportService passportService;

    /**
     * 显示登录页面
     *  并实现跳转回原页面！除了原先是注册页面外。user/showRegister
     * @return
     */
    @RequestMapping("/user/showLogin")
    public String showLogin(@RequestHeader(name = "Referer", required = false) String referer, Model model) {
        if (!referer.isEmpty()&&!referer.endsWith("user/showRegister")) {
            model.addAttribute("redirect",referer);
        }
        return "login";
    }

    /**
     * 显示注册页面
     *
     * @return
     */
    @RequestMapping("/user/showRegister")
    public String showRegister() {
        return "register";
    }

    /**
     * 检查用户名、手机号、邮箱是否存在
     *
     * @param param
     * @param type
     * @return
     */
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public EgoResults check(@PathVariable String param, @PathVariable int type) {
        TbUser tbUser = new TbUser();
        if (type == 1) {
            tbUser.setUsername(param);
        } else if (type == 2) {
            tbUser.setPhone(param);
        } else if (type == 3) {
            tbUser.setEmail(param);
        }
        return passportService.check(tbUser);
    }

    /**
     * 完成注册之前：
     * 标准的项目一定要包含:
     * 客户端校验。 服务器端校验。
     *
     * @param tbUser
     * @return
     */
    @RequestMapping("/user/register")
    @ResponseBody
    public EgoResults finishRegister(TbUser tbUser, String pwdRepeat) { //如果使用对象接受前台参数，那前台页面参数名必须和对象的属性名保持一致。
        if (Strings.isEmpty(tbUser.getUsername()) || !tbUser.getUsername().matches("^[a-zA-Z0-9]{6,12}$")) {
            return EgoResults.error("用户名必须是 6-12 位，且只能是数字和字母");
        }
        if (Strings.isEmpty(tbUser.getEmail()) || !tbUser.getEmail().matches("^[a-z\\d]+(\\.[a-z\\d]+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$")) {
            return EgoResults.error("邮箱格式不正确");
        }
        if (Strings.isEmpty(tbUser.getPassword()) || !tbUser.getPassword().matches("^[a-zA-Z0-9]{6,12}$")) {
            return EgoResults.error("密码必须是 6-12 位，且只能是数字和字母");
        }
        if (Strings.isEmpty(pwdRepeat) || !pwdRepeat.equals(tbUser.getPassword())) {
            return EgoResults.error("密码不一致");
        }
        if (Strings.isEmpty(tbUser.getPhone()) || !tbUser.getPhone().matches("^1\\d{10}$")) {
            return EgoResults.error("手机号不正确");
        }
        return passportService.finishRegister(tbUser);
    }                                            
    @RequestMapping("/user/login")
    @ResponseBody
    public EgoResults login(TbUser tbUser, HttpSession session){
        EgoResults results = passportService.login(tbUser);
        if (results.getStatus()==200) {
            session.setAttribute("loginUser",results.getData());
        }
        results.setData(null);   //客户端也不需要，里面用户名、密码等敏感信息都能被人看见。
        return results;
    }

    /**
     * 跨域获取用户信息
     * @param session
     * @return
     */
    @RequestMapping("/user/token/{token}")
    @ResponseBody
    @CrossOrigin(allowCredentials = "true") //跨域请求携带cookie时，必须设置allCredentials为true，表示允许接收cookie数据
    public EgoResults token(HttpSession session){                                       
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser!=null){
            TbUser tbUser = (TbUser) loginUser;
            tbUser.setPassword("");
            return  EgoResults.ok(tbUser);
        }
        return EgoResults.error("获取用户信息失败");
    }

    @RequestMapping("/user/logout/{token}")
    @ResponseBody
    @CrossOrigin(allowCredentials = "true") //跨域请求携带cookie时，必须设置allCredentials为true，表示允许接收cookie数据
    public EgoResults logout(HttpSession session) {
        session.invalidate();
        return EgoResults.ok();
    }
}

