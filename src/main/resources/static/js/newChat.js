// WebSocket对象
let socket;

// 连接状态指示灯
let connectStateColor;

// 连接状态
let connectState;

// 连接人数
let connectCount;

// 连接地址
let connectAddress;

// 用户昵称
let connectUserName;


/**
 * 清除消息输入框的内容
 */
function clearChatText() {
	const writeChatText = $("#writeChatText");
	if (writeChatText.val() != "") {
		writeChatText.val("");
	}
}

/**
 * 发送消息
 */
function sendChatMessage() {
	if (socket != null) {
		const writeChatText = $("#writeChatText");
		const msg = writeChatText.val();
		if (msg != "") {
			socket.send(msg);
			writeChatText.val("");
		} else {
			alert("提示：请输入消息内容。")
		}
	} else {
		alert("提示：请先连接到服务端。");
	}

}

/**
 * 断开连接
 */
function connectClose() {
	socket.close();
}

/**
 * 开启连接
 */
function connectServer() {
	if (typeof (WebSocket) == "undefined") {
		alert('浏览器暂不支持WebSocket');
		return false;
	}
	connectAddress = $("#connectAddress");
	connectUserName = $("#connectUserName");
	if (connectAddress.val() == "") {
		alert("提示：请输入连接地址。")
		return false;
	}
	if (connectUserName.val() == "") {
		alert("提示：请输入用户昵称。")
		return false;
	}
	connectStateColor = $("#connectStateColor");
	connectState = $("#connectState");
	connectCount = $("#connectCount");
	try {
		// 连接到指定接口的服务端
		socket = new WebSocket(connectAddress.val() + connectUserName.val());
	} catch (exception) {
		connectState.val("状态：连接失败");
		alert("异常：" + exception);
	}
	// 连接成功事件
	socket.onopen = function () {
		$("#connectServer").attr("disabled", true);
		$("#connectClose").removeAttr("disabled", true);
		connectAddress.attr("disabled", true);
		connectUserName.attr("disabled", true);
		connectState.val("状态：连接成功");
		connectStateColor.css('background', '#7fd575');
	}

	// 关闭连接事件
	socket.onclose = function () {
		$("#connectServer").removeAttr("disabled", true);
		$("#connectClose").attr("disabled", true);
		connectAddress.removeAttr("disabled");
		connectUserName.removeAttr("disabled");
		connectState.val("状态：未连接");
		connectStateColor.css('background', '#d57575');
		$("#connectCount").val("在线：0人");
	};

	// 获取消息事件
	socket.onmessage = function (msg) {
		// 解析消息信息
		const parse = JSON.parse(msg.data);
		const messageDiv = $("#messageDiv");
		if (parse.sendUserName === connectUserName.val()) {
			// 自己发送的消息靠右
			messageDiv.append(
				"<div class='right_msg'>" +
				"<div class='picture myMsg'></div>" +
				"<div class='info'>" + parse.sendUserName + " " + parse.sendDateTime + "</div>" +
				"<div class='msg'>" + parse.messageText + "</div>" +
				"</div>"
			);
		} else if (parse.sendUserName === "系统") {
			// 播放提示音
			new Audio("../music/m.mp3").play();
			$("#connectCount").val("在线：" + parse.connectCount + "人");
			// 系统消息
			messageDiv.append(
				"<div class='left_msg'>" +
				"<div class='picture systemMsg'></div>" +
				"<div class='info'>" + parse.sendUserName + " " + parse.sendDateTime + "</div>" +
				"<div class='msg' style='background-color: #b9bee8'>" + parse.messageText + "</div>" +
				"</div>"
			);
		} else {
			// 播放提示音
			new Audio("../music/m.mp3").play();
			// 别人发送的消息靠左
			messageDiv.append(
				"<div class='left_msg'>" +
				"<div class='picture otherMsg'></div>" +
				"<div class='info'>" + parse.sendUserName + " " + parse.sendDateTime + "</div>" +
				"<div class='msg'>" + parse.messageText + "</div>" +
				"</div>"
			);
		}
	};

	// 发生错误事件
	socket.onerror = function () {
		connectAddress.removeAttr("disabled");
		connectUserName.removeAttr("disabled");
		$("#connectServer").removeAttr("disabled", true);
		$("#connectClose").attr("disabled", true);
		connectState.val("状态：未连接");
		connectStateColor.css('background', '#d57575');
		$("#connectCount").val("在线：0人");
	}
}

/**
 * 回车键发送消息
 */
$(function () {
	$("#b_top").mouseover(function () {
		$("#b_top").mousedown(function () {
			$(document).mousemove(function (e) {
				$("#aox").css("left", e.pageX - 380 + "px").css("top", e.pageY - 120 + "px");
			})
		})
	})
	$("#b_top").mouseup(function () {
		$(document).off("mousemove");
	})
})