package com.sgoshika.user.unitTesting;

import java.net.URI;

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

import junit.framework.TestCase;

public class AddUserTest extends TestCase {
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
	public void testNullID_User() {
		User user=new User();
		user.setuId(null);
		String output = uService.addUser(user);
		assertEquals(output, "Invalid User");
	}
	@Test
	public void testExisingID_User() {
		User user=new User();
		user.setuId("1630215c-2608-44b9-aad4-9d56d8aafd4c");
		String output = uService.addUser(user);
		assertEquals(output, "User already exists");
	}
	@Test
	public void testAdd_NewUser() {		
		User user=new User();
		user.setuId("1630215c-2608-44b9-aad4-9ml56d8ajkjdj");
		String output = uService.addUser(user);
		assertEquals(output, "Add User Success");
	}
}
