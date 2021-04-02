package xyz.tanxiao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * WebSocket配置类
 *
 * @author 谈笑、
 * @email 2300064869@qq.com
 * @dateTime 2021/3/5 周五 9:04
 */
@Configuration
public class MyWebSocketConfig {

	/**
	 * 自动注册使用@ServerEndpoint注解声明的WebSocket实现类。
	 *
	 * @return ServerEndpointExporter实例对象
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

}