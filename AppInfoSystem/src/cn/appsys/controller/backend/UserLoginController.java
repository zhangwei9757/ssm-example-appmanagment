package cn.appsys.controller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import cn.appsys.dao.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping(value = "/manager")
public class UserLoginController {
	@Resource
	public BackendUserMapper backendUserMapper;

	@RequestMapping(value = "/login")
	public String login() {
		// 进入APP管理员-登陆界面
		return "backendlogin";
	}

	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String dologin(@RequestParam String userCode,
			@RequestParam String userPassword, HttpServletRequest request,
			HttpSession session) {
		BackendUser loginUser = null;
		try {
			// 根据用户名与密码登陆
			loginUser = this.backendUserMapper.getLoginUser(userCode,
					userPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != loginUser) {
			// 登录成功
			session.setAttribute(Constants.USER_SESSION, loginUser);
			// return "redirect:/manager/backend/main";// 页面跳转（main.jsp）
			return "backend/main";
		} else {
			// 页面跳转（login.jsp）带出提示信息--转发
			request.setAttribute("error", "用户名或密码不正确");
			return "backendlogin";
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		// 清除session
		session.removeAttribute(Constants.USER_SESSION);
		return "backendlogin";
	}
}
