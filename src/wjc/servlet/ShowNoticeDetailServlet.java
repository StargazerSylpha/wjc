package wjc.servlet;

import com.alibaba.fastjson.JSON;
import wjc.classes.Notice;
import wjc.classes.NoticeDao;
import wjc.classes.UserDao;

import javax.print.attribute.standard.JobKOctets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

@WebServlet(name = "ShowNoticeDetailServlet",urlPatterns = "/console/showNoticeDetail")
public class ShowNoticeDetailServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();
		int errCode = -1;
		String errMsg = new String();

		//获取sessUid判断是否登录
		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");
		/**
		 * object.toString()
		 * 必须保证object不是null，否则会抛NullPointerException异常
		 * https://www.cnblogs.com/pxzbky/p/10127517.html
		 */
		NoticeDao nd = new NoticeDao();
		Notice n = new Notice();
		NoticeDetail ndt = new NoticeDetail();

		if(sessUid == null) {
			errCode = -1;
			errMsg = "not login";
		} else {
			try {
				String noticeId = _request.getParameter("noticeId");
				n = nd.showNoticeDetail(noticeId);
			} catch (Exception e) {
				System.out.println("4002-获取公告详情异常");
				errCode = 4002;
				e.printStackTrace();
			} finally {
				if(n.getNoticeId() == null){
					errCode = 4003;
					errMsg = "公告不存在";
				} else {
					errCode = 0;
					errMsg = "success";
				}
			}
		}

		ndt.setErrCode(errCode);
		ndt.setErrMsg(errMsg);
		ndt.setErrTime(new Date().getTime() /1000);
		ndt.setNoticeId(n.getNoticeId() + "");
		ndt.setNoticeSubject(n.getNoticeSubject());
		ndt.setNoticeContent(n.getNoticeContent());
		ndt.setCreateUser(n.getCreateUser());
		ndt.setCreateTime(n.getCreateTime());
		ndt.setLastChange(n.getLastChange());
		ndt.setCreateUserName(n.getCreateUserName());

		out.println(JSON.toJSONString(ndt));
	}
}
class NoticeDetail {
	public int errCode;
	public String errMsg;
	public long errTime;

	public String noticeId;
	public String noticeSubject;
	public String noticeContent;
	public String createUser;
	public String createTime;
	public String lastChange;

	public String createUserName;

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public long getErrTime() {
		return errTime;
	}

	public void setErrTime(long errTime) {
		this.errTime = errTime;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getNoticeSubject() {
		return noticeSubject;
	}

	public void setNoticeSubject(String noticeSubject) {
		this.noticeSubject = noticeSubject;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}


	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastChange() {
		return lastChange;
	}

	public void setLastChange(String lastChange) {
		this.lastChange = lastChange;
	}
}