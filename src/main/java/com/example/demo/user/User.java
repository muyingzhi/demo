package com.example.demo.user;

public class User {

	private Long id;
	private String username;
	private String password;
	private String fullname;
	private String deptCode;
	public User(){

	}
	public User(String username,String password,String fullname,String deptCode){
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.deptCode = deptCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	@Override
	public String toString(){
		return String.format("用户：%s ID= %d }",this.getFullname(),this.getId());
	}
}
