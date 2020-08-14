package wjc.servlet;

import com.alibaba.fastjson.JSON;
import wjc.classes.User;
import wjc.classes.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "CheckLoginServlet",urlPatterns = "/login/checkLogin")

public class CheckLoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();
		int errCode = -1;
		UserDao ud = new UserDao();
		CheckLoginMsg clm = new CheckLoginMsg();

		Cookie[] cks = _request.getCookies();
		String ckUid = "";
		String ckLoginTicket = "";
		String errMsg = "";

		int uid_ = -1;
		int userType_ = -1;
		String userId_ = "";

		for(Cookie ck : cks) {
			if (ck.getName().equals("ckUid")) {
				ckUid = ck.getValue();
			}
			if (ck.getName().equals("ckLoginTicket")) {
				ckLoginTicket = ck.getValue();
			}
		}

		try {
			errCode = ud.checkLogin(ckUid,ckLoginTicket);
		} catch (Exception e) {
			System.out.println("3004-loginTicket计算失败");
			errCode = 3004;
			errMsg = "not login";
		} finally {
			if(errCode == 0) {
				errMsg = "still login";
				try {
					User uif = new User();
					User uif2 = new User();
					uif.setUid(Integer.parseInt(ckUid));
					uif2 = ud.getUserInfo(uif);
					uid_ = uif2.getUid();
					userType_ = uif2.getUserType();
					userId_ = uif2.getUserId();

					//初始化session，有效期一年
					HttpSession sess = _request.getSession();
					sess.setMaxInactiveInterval(31536000);

					sess.setAttribute("sessUid",uid_);
				} catch (Exception e) {
					System.out.println("3006-session设置失败");
					//e.printStackTrace();
					//这一个无关紧要所以不需要返回errCode
				}
			} else {
				errMsg = "not login";
				try {
					//校验失败，cookie、session销毁
					_request.getSession().invalidate();

					Cookie ckUid_ = new Cookie("ckUid",null);
					ckUid_.setMaxAge(0);
					ckUid_.setPath("/");
					Cookie ckTicket_ = new Cookie("ckLoginTicket",null);
					ckTicket_.setMaxAge(0);
					ckTicket_.setPath("/");

					_response.addCookie(ckUid_);
					_response.addCookie(ckTicket_);

				}catch (Exception e) {
					System.out.println("3005-cookie/session销毁失败");
					//这一个无关紧要所以不需要返回errCode
				}
			}

			clm.setErrCode(errCode);
			clm.setErrMsg(errMsg);
			clm.setUid(uid_);
			clm.setUserType(userType_);
			clm.setUserId(userId_);
			out.println(JSON.toJSONString(clm));

		}
	}
}

class CheckLoginMsg {
	public int errCode;
	public String errMsg;
	public int uid;
	public int userType;
	public String errTime = Long.toString(new Date().getTime() / 1000);
	public String userId;

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrTime() {
		return errTime;
	}

	public void setErrTime(String errTime) {
		this.errTime = errTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}