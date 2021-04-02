package xyz.tanxiao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * WebSocket消息实体类
 *
 * @author 谈笑、
 * @email 2300064869@qq.com
 * @dateTime 2021/3/6 周六 9:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyWebSocketMessageEntity implements Serializable {

	// 消息发送用户
	private String sendUserName;

	// 消息内容
	private Object messageText;

	// 消息发送时间
	private String sendDateTime;

	// 连接人数
	private Integer connectCount;

	/**
	 * 快速创建消息实体类
	 */
	public static MyWebSocketMessageEntity getMessageEntity(String sendUserName, Object messageText,
	                                                        String sendDateTime, Integer connectCount) {
		return new MyWebSocketMessageEntity(sendUserName, messageText, sendDateTime, connectCount);
	}

}