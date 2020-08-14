package wjc.servlet;

import com.alibaba.fastjson.JSON;
import wjc.classes.User;
import wjc.classes.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "ShowUserInfoServlet",urlPatterns = "/console/showUserInfo")
public class ShowUserInfo extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		UserDao ud = new UserDao();
		User u = new User();
		int errCode;
		String errMsg = new String();
		ShowUserInfoResult suir = new ShowUserInfoResult();

		//获取sessUid判断是否登录
		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");

		if(sessUid == null) {
			errCode = -1;
			errMsg = "not login";
		} else {
			try {
				u.setUid(Integer.parseInt(sessUid.toString()));
				u = ud.getUserInfo(u);
			} catch (Exception e) {
				System.out.println("3007-获取用户信息失败" + e.getMessage());
				errCode = 3007;
			} finally {
				if(u.getUserId() == null) {
					errCode = 3008;
					errMsg = "can not get user info";
				} else {
					errCode = 0;
					errMsg = "success";
				}
			}
		}

		suir.setErrCode(errCode);
		suir.setErrMsg(errMsg);
		suir.setUser(u);
		out.println(JSON.toJSONString(suir));
	}
}

class ShowUserInfoResult {
	public int errCode;
	public String errTime = Long.toString(new Date().getTime() / 1000);
	public String errMsg;

	private int uid = -1;	//唯一指定用户id 自增
	private String userCollegeId;	//用户工号（学号）
	private String userId;	//用户登录名

	private int userType = -1;
	private String userName;	//用户姓名
	private String userEmail;
	private String userPhone;
	private String userDepartment; //用户部门（专业班级）

	public void setUser(User _u) {
		this.uid = _u.getUid();
		this.userCollegeId = _u.getUserCollegeId();
		this.userType = _u.getUserType();
		this.userName = _u.getUserName();
		this.userEmail = _u.getUserEmail();
		this.userPhone = _u.getUserPhone();
		this.userDepartment = _u.getUserDepartment();
		this.userId = _u.getUserId();
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrTime() {
		return errTime;
	}

	public void setErrTime(String errTime) {
		this.errTime = errTime;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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