package com.huarun.gohome.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huarun.gohome.config.WebSecurityConfig;
import com.huarun.gohome.mapper.UserMapper;
import com.huarun.gohome.model.User;
import com.huarun.gohome.response.BaseResp;

@RestController
public class LoginController {

	@Resource
	private UserMapper userMapper;

	@RequestMapping("/login")
	public String preLogin() {
		return "login";
	}

	@RequestMapping("/loginHandle")
	public BaseResp loginHandle( HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
		BaseResp baseResp = new BaseResp();
		if (StringUtils.isEmpty(username)) {
			baseResp.setErrMsg("请输入用户名");
			return baseResp;
		}
		if (StringUtils.isEmpty(password)) {
			baseResp.setErrMsg("请输入密码");
			return baseResp;
		}
		User dbUser = userMapper.selectByUserName(username);
		if (dbUser==null) {
			baseResp.setErrMsg("没有该用户");
			return baseResp;
		}
		if (!password.equals(dbUser.getPassword())) {
			baseResp.setErrMsg("密码错误");
			return baseResp;
		}
//		session.setAttribute(WebSecurityConfig.SESSION_KEY, dbUser);
		response.setHeader(WebSecurityConfig.SESSION_KEY, dbUser.getId()+"");
		baseResp.setResult(BaseResp.SUCCESS);
		baseResp.setData(dbUser.getId()+"");
		return baseResp;
	}

	@RequestMapping("/loginOut")
	public BaseResp loginOut(HttpSession session) {
		session.removeAttribute(WebSecurityConfig.SESSION_KEY);
		BaseResp baseResp = new BaseResp();
		baseResp.setResult(BaseResp.SUCCESS);
		return baseResp;
	}
}
