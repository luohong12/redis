package com.shidebin.mongodb.spring_redis;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private int age;
	private String sex;
	private String address;
	private String mobile;
	private User() {};
	private User(UserBuilder build) {
		this.name = build.name;
		this.age = build.age;
		this.sex = build.sex;
		this.address = build.address;
		this.mobile = build.mobile;
	}

	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public String getSex() {
		return sex;
	}
	public String getAddress() {
		return address;
	}
	public String getMobile() {
		return mobile;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public static UserBuilder getUserBuilder() {
		return new UserBuilder();
	}
	public static class UserBuilder{
		private String name;
		private int age;
		private String sex;
		private String address;
		private String mobile;
		public UserBuilder name(final String name) {
			this.name = name;
			return this;
		}
		public UserBuilder age(final int age) {
			this.age = age;
			return this;
		}
		public UserBuilder sex(final String sex) {
			this.sex = sex;
			return this;
		}
		public UserBuilder address(final String address) {
			this.address = address;
			return this;
		}
		public UserBuilder mobile(final String mobile) {
			this.mobile = mobile;
			return this;
		}
		public User build() {
			return new User(this);
		}
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", sex=" + sex + ", address=" + address + ", mobile=" + mobile
				+ "]";
	}
	public static void main(String[] args) {
		User build = User.getUserBuilder().name("fdsafsda").age(12).address("fasdf").build();
		System.out.println(build);
	}
}
