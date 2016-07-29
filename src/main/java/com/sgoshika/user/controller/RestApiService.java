package com.sgoshika.user.controller;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;
import com.sgoshika.user.entities.User;
import com.sgoshika.user.service.UserService;

public class RestApiService {
	private static UserService userService = new UserService();

	public static void main(String[] args) {
		Gson gson = new Gson();
		
		// Adding User 		
		post("/add-user", (req, res) -> {
			res.type("application/json");
			User user = gson.fromJson(req.body(), User.class);
			return userService.addUser(user);
		}, gson::toJson);

		// Retrieving all users
		get("/", (req, res) -> {
			res.type("application/json");
			return userService.getAllUser();
		}, gson::toJson);

		// Updating existing user data
		put("/update-user", (req, res) -> {
			res.type("application/json");
			User user = gson.fromJson(req.body(), User.class);
			return userService.updateUser(user);
		}, gson::toJson);

	}

}
