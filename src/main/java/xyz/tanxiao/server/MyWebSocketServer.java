package xyz.tanxiao.server;

import org.springframework.stereotype.Component;
import xyz.tanxiao.config.MyWebSocketEncoder;
import xyz.tanxiao.entity.MyWebSocketConnectEntity;
import xyz.tanxiao.entity.MyWebSocketMessageEntity;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务实现类
 *
 * @author 谈笑、
 * @email 2300064869@qq.com
 * @dateTime 2021/3/5 周五 9:07
 */
@Component
@ServerEndpoint(value = "/webSocket/{userName}", encoders = MyWebSocketEncoder.class)
public class MyWebSocketServer {

	// 客户端连接成功容器
	private static final Map<String, Object> CONNECT_CONTAINER = new ConcurrentHashMap<>();


	/**
	 * 客户端与服务端建立连接成功时，调用此方法。
	 *
	 * @userName session 连接会话
	 * @userName userName 附带参数
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("userName") String userName) {
		// 如果连接的客户端用户名称已存在，则不添加到客户端容器中，并强制关闭会话。
		if (MyWebSocketServer.CONNECT_CONTAINER.containsKey(userName)) {
			// 将封装消息封装为实体类。
			this.sendMessage(session, MyWebSocketMessageEntity.getMessageEntity(
					"系统", "用户 " + userName + " 已存在。",
					this.getCurrentDateTime(), null));
			// 挤掉别人BUG
			this.closeConnect(session);
		} else {
			// 创建连接会话实体类，添加到客户端容器。
			MyWebSocketServer.CONNECT_CONTAINER.put(userName,
					new MyWebSocketConnectEntity(session.getId(), userName, session));
			this.sendAllMessage(MyWebSocketMessageEntity.getMessageEntity(
					"系统", userName + " 已加入。",
					this.getCurrentDateTime(), MyWebSocketServer.CONNECT_CONTAINER.size()));
		}
	}


	/**
	 * 客户端与服务端断开连接时，调用此方法。
	 *
	 * @userName userName 附带参数
	 */
	@OnClose
	public void OnClose(Session session, @PathParam("userName") String userName) {
		// 从容器中移除断开连接的会话
		MyWebSocketServer.CONNECT_CONTAINER.remove(userName);
		this.sendAllMessage(MyWebSocketMessageEntity.getMessageEntity(
				"系统", userName + " 已离开。",
				this.getCurrentDateTime(), MyWebSocketServer.CONNECT_CONTAINER.size()));
	}


	/**
	 * 收到客户端消息时，调用此方法。
	 *
	 * @throws IOException
	 * @userName msg 客户端消息参数
	 */
	@OnMessage
	public void onMessage(@PathParam("userName") String userName, String msg) throws IOException {
		// 将封装消息封装为实体类。
		this.sendAllMessage(MyWebSocketMessageEntity.getMessageEntity(
				userName, msg, this.getCurrentDateTime(), null));
	}


	/**
	 * 客户端与服务端连接发生异常时，调用此方法。
	 *
	 * @userName session 连接会话
	 * @userName throwable
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		// 将封装消息封装为实体类。
		MyWebSocketMessageEntity messageEntity = new MyWebSocketMessageEntity(
				"系统", "系统异常：" + throwable.getMessage(),
				this.getCurrentDateTime(), null);
		this.sendAllMessage(messageEntity);
	}


	/**
	 * 将消息发送给所有连接的客户端
	 *
	 * @userName msg 消息
	 */
	public void sendAllMessage(MyWebSocketMessageEntity messageEntity) {
		// 遍历所有会话，将消息发送到所有客户端。
		for (Map.Entry<String, Object> entry : MyWebSocketServer.CONNECT_CONTAINER.entrySet()) {
			MyWebSocketConnectEntity entity = (MyWebSocketConnectEntity) entry.getValue();
			this.sendMessage(entity.getConnectSession(), messageEntity);
		}
	}


	/**
	 * 发送消息到客户端
	 *
	 * @userName session 会话
	 * @userName msg 消息
	 */
	private void sendMessage(Session session, MyWebSocketMessageEntity messageEntity) {
		try {
			session.getBasicRemote().sendObject(messageEntity);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[异常=> 消息发送失败]");
		}
	}


	/**
	 * 断开客户端连接
	 *
	 * @param session 连接会话
	 */
	public void closeConnect(Session session) {
		if (session != null) {
			try {
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 获取到当前日期时间并返回字符串格式
	 *
	 * @return 当前日期字符串
	 */
	public String getCurrentDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localDateTime.format(dateTimeFormatter);
	}

}