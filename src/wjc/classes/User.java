package wjc.classes;

public class User {

	private int uid = -1;	//唯一指定用户id 自增
	private String userCollegeId;	//用户工号（学号）
	private String userId;	//用户登录名
	private String userPassword;
	private int userType = -1; //用户类型 1教师 2学生
	private String userName;	//用户姓名
	private String userEmail;
	private String userPhone;
	private String userDepartment; //用户部门（专业班级）

	/**
	 * create table user (
	 * 	uid int primary key not null,   //自增
	 * 	user_college_id varchar(25) not null,
	 * 	user_id VARCHAR(25) not null,
	 * 	user_password VARCHAR(25) not null,
	 * 	user_type int not null,
	 * 	user_name VARCHAR(25),
	 * 	user_email VARCHAR(50),
	 * 	user_phone VARCHAR(25),
	 * 	user_department varchar(50)
	 * )
	 */

	public User() {
		//...
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUserCollegeId() {
		return userCollegeId;
	}

	public void setUserCollegeId(String userCollegeId) {
		this.userCollegeId = userCollegeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserDepartment() {
		return userDepartment;
	}

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}
}

