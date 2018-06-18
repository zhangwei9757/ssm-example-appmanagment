package cn.appsys.controller.developer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.dao.DevUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping(value = "/dev")
public class DevLoginController {
	@Resource
	private DevUserMapper devUserMapper;

	@RequestMapping(value = "/login")
	public String login() {
		// 进入开发者-登陆界面
		return "devlogin";
	}

	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String doLogin(@RequestParam String devCode,
			@RequestParam String devPassword, HttpServletRequest request,
			HttpSession session) {
		DevUser loginUser = null;
		try {
			loginUser = this.devUserMapper.getLoginUser(devCode, devPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != loginUser) {// 登录成功
			session.setAttribute(Constants.DEV_USER_SESSION, loginUser);
			//return "redirect:/dev/flatform/main";// 页面跳转（main.jsp）
			return "developer/main";
		} else {
			// 页面跳转（login.jsp）带出提示信息--转发
			request.setAttribute("error", "用户名或密码不正确");
			return "devlogin";
		}
	}

	@RequestMapping("/flatform/main")
	public String flatFormMain(HttpSession session) {
		if (session.getAttribute(Constants.DEV_USER_SESSION) == null) {
			return "redirect:/dev/login";
		}
		return "developer/main";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		// 清除session
		session.removeAttribute(Constants.DEV_USER_SESSION);
		return "devlogin";
	}
}
