package wjc.classes;
import org.apache.commons.codec.digest.DigestUtils;


import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import wjc.classes.User;
import wjc.classes.DbHelper;

import javax.sql.rowset.CachedRowSet;

public class UserDao {

	public UserDao() {
		//...
	}

	/**
	 * 用户信息判断
	 * _userId  用户名（登录名），4-25位，字母、数字
	 * _userPassword  密码，6-25位,字母、数字、符号
	 * _userEmail 邮箱，4-50位，有@有.
	 *
	 */

	public boolean checkUserId(String _userId) {
		if(_userId.length() < 4 || _userId.length() > 25 || _userId == null) {
			return false;
		}

		//字符不检查，只检查长度
		return true;
	}

	public boolean checkUid(String _uid) {

		if(_uid == null) {
			return false;
		}

		int uidInt = Integer.parseInt(_uid);

		if(uidInt <= 0 || _uid == null) {
			return false;
		}


		//字符不检查，只检查长度
		return true;
	}

	public boolean checkUserPassword(String _userPassword) {
		if(_userPassword.length() < 6 || _userPassword.length() > 25) {
			return false;
		}
		//字符不检查，只检查长度
		return true;
	}

	public String getLoginTicket(User _u) throws Exception {
		UserDao ud = new UserDao();
		String sha1pw = ud.getSha1Password(_u.getUid() + "");

		String loginTicket = DigestUtils.sha1Hex(_u.getUid() + "wjc" + sha1pw + Long.toString(new Date().getTime() / 10000000));
		return loginTicket;
	}

	public String getSha1Password(String _uid) throws Exception {

		UserDao ud = new UserDao();
		String inputUid = _uid;
		String sha1Password = "";

		if(ud.checkUserId(inputUid)) {
			String queryString = "select user_password from user where user_id = '" + inputUid +"';";
			CachedRowSet rs = DbHelper.dbSelect(queryString);
			ArrayList<String> resultList = new ArrayList<>();

			if(rs != null) {
				while(rs.next()) {
					resultList.add(rs.getString("user_password"));
				}
			}

			if(resultList.size() == 1) {
				sha1Password = resultList.get(0);
			} else {
				System.out.println("3001-找不到用户或登录名错误");
				return sha1Password;
			}
		}

		return sha1Password;
	}

	public int checkLogin(String _ckUid,String _ckLoginTicket) throws Exception {

		UserDao ud = new UserDao();


		if(!(ud.checkUid(_ckUid)))  {
			System.out.println("3002-uid不合法");
			return 3002;
		}

		String sha1Password = "";
		sha1Password = ud.getSha1Password(_ckUid);

		//ticket有效期100 0000ms，约16分钟
		String calcLoginTicket = "";
		calcLoginTicket =DigestUtils.sha1Hex (_ckUid + "wjc" + sha1Password + Long.toString(new Date().getTime() / 10000000));

		if(calcLoginTicket.equals(_ckLoginTicket)) {
			return 0;
		} else {
			System.out.println("3003-loginTicket计算后匹配失败");

			return 3003;
		}


	}

	public User getUserInfo(User _u) throws Exception {

		UserDao ud = new UserDao();
		User nu = new User();

		String queryString = "";
		ArrayList<User> alu = new ArrayList<>();



		/*
		if(ud.checkUserId(_u.getUserId())) {
			queryString = "select * from user where user_id = '" +_u.getUserId() + "';";

		}
		*/

		if(ud.checkUid(_u.getUid() + "")) {
			queryString = "select * from user where uid = '" + _u.getUid() + "';";
		} else {
			queryString = "select * from user where user_id = '" +_u.getUserId() + "';";
		}

		CachedRowSet rs = DbHelper.dbSelect(queryString);

		if(rs != null) {
			while(rs.next()) {
				User rsu = new User();
				rsu.setUid(rs.getInt("uid"));
				rsu.setUserCollegeId(rs.getString("user_college_id"));
				rsu.setUserId(rs.getString("user_id"));
				rsu.setUserType(rs.getInt("user_type"));
				rsu.setUserName(rs.getString("user_name"));
				rsu.setUserEmail(rs.getString("user_email"));
				rsu.setUserPhone(rs.getString("user_phone"));
				rsu.setUserDepartment(rs.getString("user_department"));
				alu.add(rsu);
			}
		}

		if(alu.size() == 1) {
			nu = alu.get(0);
		}

		return nu;


	}

	public int userLogin(User _u) throws Exception {
		String loginUserId = new String();
		String loginUserPassword = new String();

		//UserDao业务逻辑检查用户名和密码是否符合规范
		UserDao ud = new UserDao();
		if(ud.checkUserId(_u.getUserId())) {
			loginUserId = _u.getUserId();
		} else {
			System.out.println("2001-校验用户名不合法");
			return 2001;
		}

		if(ud.checkUserPassword(_u.getUserPassword())) {
			loginUserPassword = _u.getUserPassword();
		} else {
			System.out.println("2002-校验密码不合法");
			return 2002;
		}

		//构造sql查询语句
		String sha1Password = DigestUtils.sha1Hex(loginUserPassword);
		String queryString = "select user_id,user_password from user where user_id='"+ loginUserId +"' and user_password='" +sha1Password +"';" ;
		//返回结果集，填充ArrayList

		ArrayList<String> resultList = new ArrayList<>();
		CachedRowSet rs = DbHelper.dbSelect(queryString);

		/**
		 * 即使数据库返回0行（没有符合要求的），返回的ResultSet还是不为null
		 * 但又什么都输出不出来，所以务必填充到ArrayList中查看结果
		 */

		if(rs != null) {
			while (rs.next()) {
				resultList.add(rs.getString("user_id"));
			}
		}
		if (resultList.size() == 1){
			return 0;
		} else {
			System.out.println("2003-登录失败，信息有误");
			return 2003;
		}

	}

	public int getUserType(Object _sessUid) {
		int userType = -1;

		UserDao ud = new UserDao();

		if(_sessUid == null) {
			return userType;
		} else {
			try{
				User u = new User();
				u.setUid(Integer.parseInt(_sessUid.toString()));
				userType = ud.getUserInfo(u).getUserType();
			} catch (Exception e) {
				System.out.println("2004-获取用户权限失败" + e.getMessage());
				userType = -1;
			}
		}
		return userType;
	}

	public ArrayList<User> showStudentList() throws Exception {
		ArrayList<User> studentList = new ArrayList<>();
		String queryString = "select * from user where user_type = 2;";
		CachedRowSet rs = DbHelper.dbSelect(queryString);

		if(rs != null) {
			while (rs.next()) {
				User stu = new User();
				stu.setUid(rs.getInt("uid"));
				stu.setUserDepartment(rs.getString("user_department"));
				stu.setUserPhone(rs.getString("user_phone"));
				stu.setUserEmail(rs.getString("user_email"));
				stu.setUserName(rs.getString("user_name"));
				stu.setUserCollegeId(rs.getString("user_college_id"));
				studentList.add(stu);
			}
		}
		return studentList;
	}
}
