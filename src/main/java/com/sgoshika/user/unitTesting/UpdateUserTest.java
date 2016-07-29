package com.sgoshika.user.unitTesting;

import static org.junit.Assert.*;

import java.net.URI;

import org.bson.types.ObjectId;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sgoshika.user.entities.Address;
import com.sgoshika.user.entities.Company;
import com.sgoshika.user.entities.User;
import com.sgoshika.user.service.UserService;

public class UpdateUserTest {
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
		Address address=new Address();
		Company company=new Company();
		ObjectId objId=new ObjectId();
		//User with this ID 1630215c-2608-44b9-aad4-9d56d8aafd4c already present in MY Database so, it passes
		User user = new User(objId,"1630215c-2608-44b9-aad4-9d56d8aafd4c",
				"Sandeep",
				"Keeling",
				"Darby_Leffler68@gmail.com",
				address,
				"2016-03-15T07:02:40.896Z",
				company,
				"http://lorempixel.com/640/480/people");
		String output=uService.updateUser(user);
		assertEquals("Update Success", output);
	}

}
