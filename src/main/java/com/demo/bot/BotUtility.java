package com.demo.bot;

import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BotUtility {
	private static final boolean TRACE_MODE = false;
	static String botName = "super";

	public static String resourcesPath = "";
	public static Bot bot = null;
	public static Chat chatSession = null;

	static {
		resourcesPath = getResourcesPath();
		MagicBooleans.trace_mode = TRACE_MODE;
		bot = new Bot("super", resourcesPath);
		chatSession = new Chat(bot);
		bot.brain.nodeStats();
	}

	public static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}

	public static void main(String[] args) {
		try {
			String resourcesPath = getResourcesPath();
			System.out.println(resourcesPath);
			MagicBooleans.trace_mode = TRACE_MODE;
			Bot bot = new Bot("super", resourcesPath);
			Chat chatSession = new Chat(bot);
			bot.brain.nodeStats();
			String textLine = "";

			// getResonseFromBot(textLine, bot, chatSession);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping(value = "/getBotResponse/{MsgFromChatBox}")
	public static String getResonseFromBot(@PathVariable("MsgFromChatBox") String MsgFromChatBox) {
		try {
			String response = "";
			// textLine = IOUtils.readInputTextLine(); // Reads data from Command Line
			if ((MsgFromChatBox == null) || (MsgFromChatBox.length() < 1))
				MsgFromChatBox = MagicStrings.null_input;
			if (MsgFromChatBox.equals("q")) {
				System.exit(0);
			} else if (MsgFromChatBox.equals("wq")) {
				bot.writeQuit();
				System.exit(0);
			} else {
				String request = MsgFromChatBox;
				if (MagicBooleans.trace_mode)
					System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
							+ ":TOPIC=" + chatSession.predicates.get("topic"));
				response = chatSession.multisentenceRespond(request);
				while (response.contains("&lt;"))
					response = response.replace("&lt;", "<");
				while (response.contains("&gt;"))
					response = response.replace("&gt;", ">");
			}
			System.out.println("Robot : " + response);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}