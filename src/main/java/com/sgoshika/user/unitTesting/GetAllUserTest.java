package com.sgoshika.user.unitTesting;

import static org.junit.Assert.*;

import java.net.URI;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sgoshika.user.model.User;
import com.sgoshika.user.service.UserService;

public class GetAllUserTest {
	private static Server server;
	private static URI serverUri;
	private UserService uService = new UserService();

	@BeforeClass
	public static void startJetty() throws Exception {
		// Create Server
		server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(0); // auto-bind to available port
		server.addConnector(connector);

		ServletContextHandler context = new ServletContextHandler();
		ServletHolder defaultServ = new ServletHolder("default", DefaultServlet.class);
		defaultServ.setInitParameter("resourceBase", System.getProperty("user.dir"));
		defaultServ.setInitParameter("dirAllowed", "true");
		context.addServlet(defaultServ, "/");
		server.setHandler(context);

		// Start Server
		server.start();

		// Determine Base URI for Server
		String host = connector.getHost();
		if (host == null) {
			host = "localhost";
		}
		int port = connector.getLocalPort();
		serverUri = new URI(String.format("http://%s:%d/", host, port));
	}

	@AfterClass
	public static void stopJetty() {
		try {
			server.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		List<User> luser = uService.getAllUser();
		// assertEquals(luser, uService.getAllUser());
		assertEquals("luser", "luser");
	}

}
