package com.sgoshika.user.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.mongodb.MongoClient;
import com.sgoshika.user.model.User;

public class UserService {
	MongoClient client = new MongoClient("localhost", 27017);
	Datastore datastore = new Morphia().createDatastore(client, "user");

	// Adds User if there is no existing user
	public String addUser(User user) {
		if (user.getuId() == null)
			return "Invalid User";
		List<User> lUser = getAllUser();
		Iterator<User> itr = lUser.iterator();
//		Collections.binarySearch((ArrayList) lUser, user);
		if (lUser != null) {
			while (itr.hasNext()) {
				if (itr.next().equals(user)) {
					return "User already exists";
				}
			}
		}
		datastore.save(user);
		return "Add User Success";
	}

	// Lists all users if any in the database otherwise returns null
	public List<User> getAllUser() {
		List<User> list = datastore.find(User.class).asList();
		if (list != null) {
			return list;
		}
		return null;
	}

	// Update specific user based on his ID sent from request. Checks if any
	// document in database matches with ID sent in request
	// otherwise no update takes place
	public String updateUser(User user) {
		// Finding the document in database using UID
		User userDB = datastore.find(User.class, "uId", user.getuId()).get(); 

		if (userDB != null) {
			Query<User> query = datastore.createQuery(User.class).field("_id").equal(userDB.getId());

			UpdateOperations<User> ops = datastore.createUpdateOperations(User.class)
					.set("firstName", user.getFirstName()).set("lastName", user.getLastName())
					.set("email", user.getEmail()).set("address", user.getAddress())
					.set("dateCreated", user.getDateCreated()).set("company", user.getCompany())
					.set("profilePic", user.getProfilePic());

			UpdateResults urs = datastore.update(query, ops); // Update the object
																
			System.out.println("UpdateResults " + urs.getUpdatedCount());
			if (urs.getUpdatedCount() > 0) {
				return "Update Success";
			} else {
				return "Update Failed";
			}
		} else {
			return "404 user not found";
		}

	}

	/*
	 * public User getUserByID(String username) { Blog blog =
	 * datastore.find(Blog.class, "auth", username).get(); if (blog != null) {
	 * return blog; } else return null; }
	 */
}
