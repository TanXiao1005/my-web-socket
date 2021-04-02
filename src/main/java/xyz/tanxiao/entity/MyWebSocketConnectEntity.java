package xyz.tanxiao.entity;

import lombok.*;

import javax.websocket.Session;
import java.io.Serializable;

/**
 * WebSocket连接实体类
 *
 * @author 谈笑、
 * @email 2300064869@qq.com
 * @dateTime 2021/3/5 周五 10:33
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyWebSocketConnectEntity implements Serializable {

	// 连接ID
	private String connectId;

	// 连接名称
	private String connectName;

	// 连接会话
	private Session connectSession;

}