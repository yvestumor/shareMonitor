<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
var ws;
function wsOpen(){ // 웹소켓 열기 
	ws = new WebSocket("ws://"+location.host+"/streaming");
}
wsOpen();
ws.onmessage = function(data) { // 서버에서 데이터 받기 
		console.log(data.data);
		var base64 = "data:image/png;base64," + data.data; // base64로 인코딩된 이미지를 출력하기위해서 받은데이터 수정하기
		document.getElementById("stream").src = base64; // src에 받은 데이터 넣기
	}
</script>
<body>
<img id="stream" src=""/> <!-- 출력 -->
</body>
</html>