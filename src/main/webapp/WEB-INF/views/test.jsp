<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- Imports -->
<%@ page import="com.demo.bot.BotUtility"%>
<%@ page import="org.alicebot.ab.MagicBooleans"%>
<%@ page import="org.alicebot.ab.Bot"%>
<%@ page import="org.alicebot.ab.Chat"%>
<%@ page import="org.alicebot.ab.utils.IOUtils"%>
<%@ page import="org.alicebot.ab.MagicStrings"%>
<style>
textarea {
	width: 90%;
	height: 50px;
}
</style>
</head>
<body>
	<%
	try {
			String resourcesPath = BotUtility.getResourcesPath();
	        System.out.println(resourcesPath);
	        MagicBooleans.trace_mode = false;
	        Bot bot = new Bot("super", resourcesPath);
	        Chat chatSession = new Chat(bot);
	        bot.brain.nodeStats();
	        String textLine = "";

	        //BotUtility.getResonseFromBot(textLine, bot, chatSession);

		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
	<h2>Chat Messages</h2>

	<div id="chatbotmainDiv">
		<div id="chatMsgsMainDiv">
			<div id="chatMsgBotDiv"></div>
			<div id="chatMsgHumanDiv"></div>
			<hr>
			<div id="chatMsgTxtArea">
			</div>
		</div>
	</div>

</body>
</html>