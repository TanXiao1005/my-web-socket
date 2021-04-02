package xyz.tanxiao.exception;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author 谈笑、
 * @email 2300064869@qq.com
 * @dateTime 2021/3/8 周一 10:51
 */
@ControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(Exception.class)
	public String exception(Exception e) {
		return e.getMessage();
	}

}