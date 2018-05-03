<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<script src="/resources/js/bot/jquery.ui.chatbox.js"></script>
<link rel="stylesheet" type="text/css"
	href="/resources/css/bot/jquery.ui.chatbox.css">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#idIconGo').click(function() {
			var txtMsg = $("#idTxt").val();
			var userInputDiv = "<li><div id=\"idUserMsg\" class=\"left-chat\"><img src=\"/resources/images/BuntyAvataar.png\" style=\"border-radius: 50%;\"><p id=\"getMsg\">"+txtMsg+"</p><span>2 min</span></div></li>";
			$(".chat-section ul").append(userInputDiv + "<br/>");
			
			$.ajax({
				type: "GET",
				dataType: "text",
				url: "http://localhost:8080/getBotResponse/"+txtMsg,
				success: function(data) {
					var responseDiv = "<li><div id=\"idUserMsgResp\" class=\"right-chat\"><img src=\"/resources/images/You.png\" style=\"border-radius: 50%;\"><p id=\"getMsgResp\">"+data+"</p><span>2 min</span></div></li>";
					$(".chat-section ul").append(responseDiv + "<br/>");
					// To scroll to the bottpm pf the div
					document.getElementById('idchat').scrollTop = document.getElementById('idchat').scrollHeight;
				},
                error: function (msg, url, line) {
                    alert('error msg = ' + msg + ', url = ' + url + ', line = ' + line);
                }
			});
			$('#idTxt').val("");
		});

		var input = document.getElementById("idTxt");
		input.addEventListener("keyup", function(event) {
		    event.preventDefault();
		    if (event.keyCode === 13) {
		        document.getElementById("idIconGo").click();
		    }
		});
	});

</script>
</head>
<body>
	<div class="main-section">
		<div class="row border-chat">
			<div class="col-md-12 col-sm-12 col-xs-12 first-section">
				<div class="row">
					<div class="col-md-8 col-sm-6 col-xs-6 left-first-section">
						<p>Chat</p>
					</div>
					<div class="col-md-4 col-sm-6 col-xs-6 right-first-section">
						<a href="#"><i class="fa fa-minus" aria-hidden="true"></i></a>
					</div>
				</div>
			</div>
		</div>
		<div class="row border-chat">
			<div class="col-md-12 col-sm-12 col-xs-12 second-section">
				<div class="chat-section" id="idchat">
					<ul>
					</ul>
				</div>
			</div>
		</div>
		<div class="row border-chat">
			<div class="col-md-12 col-sm-12 col-xs-12 third-section">
				<div class="text-bar">
					<input type="text" placeholder="Write messege" id="idTxt">
					<a href="#">
						<i class="fa fa-arrow-right" aria-hidden="true" id="idIconGo" ></i>
					</a>
				</div>
			</div>
		</div>
	</div>

</body>
</html>