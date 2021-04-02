package xyz.tanxiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 路由控制器
 *
 * @author 谈笑、
 * @email 2300064869@qq.com
 * @dateTime 2021/3/5 周五 9:26
 */
@Controller
public class RouteController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}

}