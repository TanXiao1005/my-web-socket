package xyz.tanxiao.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.tanxiao.entity.MyWebSocketMessageEntity;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * 自定义WebSocket Encoder解码器
 *
 * @author 谈笑、
 * @email 2300064869@qq.com
 * @dateTime 2021/3/6 周六 11:31
 */
public class MyWebSocketEncoder implements Encoder.Text<MyWebSocketMessageEntity> {

	@Override
	public void init(EndpointConfig endpointConfig) {

	}

	@Override
	public void destroy() {

	}

	/**
	 * 将消息实体类转换为json格式的数据
	 *
	 * @param object 实体类
	 * @return 对象json数据
	 */
	@Override
	public String encode(MyWebSocketMessageEntity object) throws EncodeException {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}