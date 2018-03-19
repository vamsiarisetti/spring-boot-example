package com.demo.bot;

import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotUtility {

	public static Logger logger = LogManager.getLogger(BotUtility.class);

	private static final boolean TRACE_MODE = false;
	static String botName = "super";

	public static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		logger.info("Path :: " + path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		logger.info("resourcesPath :: " + resourcesPath);
		return resourcesPath;
	}

	public String getResponseFromBot(String inputString, Chat chatSession) {

		try {

			String request = inputString;
			if (MagicBooleans.trace_mode) {
				System.out.println("STATE=" + request + ": THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
						+ ":TOPIC=" + chatSession.predicates.get("topic"));
			}
			String response = chatSession.multisentenceRespond(request);
			while (response.contains("&lt;"))
				response = response.replace("&lt;", "<");
			while (response.contains("&gt;"))
				response = response.replace("&gt;", ">");
			System.out.println("Robot : " + response);
			return response;
		} catch (Exception e) {
			logger.error("Caught in Chatsession ::" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		BotUtility botUtil = new BotUtility();
		String resourcesPath = getResourcesPath();
		MagicBooleans.trace_mode = TRACE_MODE;
		Bot bot = new Bot("super", resourcesPath);

		// Create chat session
		Chat chatSession = new Chat(bot);
		bot.brain.nodeStats();
		String textLine = "";
		while (true) {

			System.out.println("Human : ");
			textLine = IOUtils.readInputTextLine();
			if (textLine == null || textLine.length() < 1) {
				textLine = MagicStrings.null_input;
			}
			if ("q".equals(textLine)) {
				System.exit(0);
			} else if ("wq".equals(textLine)) {
				bot.writeQuit();
				System.exit(0);
			} else {
				botUtil.getResponseFromBot(textLine, chatSession);
			}
		}
	}
}