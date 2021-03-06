package groupSPV.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import groupSPV.model.User;

/** Used to manage active User objects.
 * @author Frank Fasola
 * @author James Donnell
 * @author Spencer Escalante
 * @author Trevor Silva */
public class LoginList {

	/** ArrayList of Users. */
	protected static ArrayList<User> users;
	
	/** Adds and creates a User.
	 * @param username Username.
	 * @param password Password. */
	public static void addUser(String username, String password) {
		load();
		users.add(new User(username, password));
		save();
	}
	
	/** Removes a User, only with correct username and password.
	 * @param username Username to remove.
	 * @param password Correct password.
	 * @return boolean. */
	public static boolean removeUser(String username, String password) {
		User user = verifyUser(username, password);
		
		if(user == null) return false;
		
		boolean removeResult = users.remove(user);
		save();
		return removeResult;
	}
	
	/** Verifies if a username and password are valid.
	 * @param username Username to verify.
	 * @param password Password to verify.
	 * @return User object if correct, null if wrong. */
	public static User verifyUser(String username, String password) {
		load();
		for(User user : users) {
			if(username.equals(user.toString()) && user.verifyUser(password))
				return user;
		}
		return null;
	}
	
	/** Returns if a username already exists.
	 * @param username Username to check.
	 * @return boolean true if exits, false if doesn't. */	
	public static boolean exists(String username){
		load();
		for(User user : users){
			if(username.equals(user.toString())){
				return true;
			}
		}
		return false;
	}
	
	/** Saves the User ArrayList. */
	private static void save() {
		try {
			FileOutputStream fileOut = new FileOutputStream(getSaveFile());
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(users);
			out.close();
			fileOut.close();
		} catch (FileNotFoundException fnfe) {
			System.err.println("FNFE" + fnfe.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		}
	}
	
	/** Loads the User ArrayList. */
	@SuppressWarnings("unchecked")
	private static void load() {
		try {
			FileInputStream fileIn = new FileInputStream(getSaveFile());
	        ObjectInputStream in = new ObjectInputStream(fileIn);
	        users = (ArrayList<User>) in.readObject();
	        in.close();
	        fileIn.close();
		} catch (FileNotFoundException fnfe) {
			users = new ArrayList<User>(); // No save, create empty list.
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.err.println(cnfe.getMessage());
		}
	}

	/** Gets the full path and file for the User login.
	 * @return Full path. */
	private static String getSaveFile() {
		return Utils.getSystemPath("loginList.ser");
	}
}
