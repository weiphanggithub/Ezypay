package com.ezypay.main;

import io.javalin.Javalin;
import io.javalin.embeddedserver.Location;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Get configuration directory.
		String path_base = "default";
		int port = 21001;
		
		String path = path_base.endsWith("/") ? (path_base) : (path_base + "/");
		Javalin app = Javalin.create().port(port).enableStaticFiles(path + "resources", Location.EXTERNAL).start();
		app.post("/insert", Subscription.insert);
		app.post("/display", Subscription.display);
		app.post("/clear", Subscription.clear);
	}

}
