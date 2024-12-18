package com.zodiac.homehealthdevicedatalogger.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
	private String Id;
	private String FirstName;
	private String LastName;
	private int Age;
	private String Phone;
	private String Gender;
	private String Role;
	private String BloodGroup;
	private String Email;
	private String Password;
	private String ConfirmPassword;
	private String RoleID;
	private User currentUser;

	public User() {
		super();
	}

	// Constructor
	public User(String id, String firstName, String lastName, int age, String phone, String gender, String role, String BloodGroup, String email, String password, String confirmPassword, String RoleID) {
		this.Id = id;
		this.FirstName = firstName;
		this.LastName = lastName;
		this.Age = age;
		this.Phone = phone;
		this.Gender = gender;
		this.Role = role;
		this.BloodGroup = BloodGroup;
		this.Email = email;
		this.Password = password;
		this.ConfirmPassword = confirmPassword;
		this.RoleID = RoleID;
	}

	public User(String id, String firstName) {
		this.Id = id;
		this.FirstName = firstName;
	}

	public void setCurrentUser(User user) {
		this.currentUser = user;
	}

	public User getCurrentUser() {
		return this.currentUser;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	public String getBloodGroup() {
		return BloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		BloodGroup = bloodGroup;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getRoleID() {
		return RoleID;
	}

	public void setRoleID(String roleID) {
		RoleID = roleID;
	}

	public String getConfirmPassword() {
		return ConfirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		ConfirmPassword = confirmPassword;
	}

}

