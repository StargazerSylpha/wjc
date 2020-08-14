package wjc.servlet;

import com.alibaba.fastjson.JSON;
import wjc.classes.Comment;
import wjc.classes.CommentDao;
import wjc.classes.ErrorHelper;
import wjc.classes.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "ShowCommentDetailServlet",urlPatterns = "/console/showCommentDetail")
public class ShowCommentDetailServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		int errCode = -1;
		int userType = -1;

		Object sessUid = _request.getSession().getAttribute("sessUid");
		UserDao ud = new UserDao();
		userType = ud.getUserType(sessUid);
		CommentDao cd = new CommentDao();
		Comment c = new Comment();
		String commentId = _request.getParameter("commentid");

		CommentDetail cdt = new CommentDetail();

		if(userType == 1 || userType == 2) {
			try {
				c = cd.showCommentDetail(commentId);
			} catch (Exception e) {
				System.out.println("8007-获取作答详情异常-" + e.getMessage());
				errCode = 8007;
			} finally {
				if(c.getCommentId() == null){
					errCode = 8008;
				} else if((userType == 1) || (userType == 2 && c.getCommentStu().equals(sessUid.toString()))) {
					errCode = 0;
					cdt.setCommentId(c.getCommentId());
					cdt.setCommentTask(c.getCommentTask());
					cdt.setCommentTaskName(c.getCommentTaskName());
					cdt.setCommentStuCollegeId(c.getCommentStuCollegeId());
					cdt.setCommentStuName(c.getCommentStuName());
					cdt.setCommentTime(c.getCommentTime());
					cdt.setCommentContent(c.getCommentContent());
					cdt.setCommentCmt(c.getCommentCmt() == null ? "" : c.getCommentCmt());
					cdt.setCommentScore(c.getCommentScore() == null ? "" :c.getCommentScore());
				} else {
					errCode = 8009;
				}
			}
		}

		cdt.setErrCode(errCode + "");
		cdt.setErrMsg(ErrorHelper.getErrMsg(errCode));
		out.println(JSON.toJSONString(cdt));
	}
}

class CommentDetail {
	private String errCode;
	private String errMsg;
	private String errTime = Long.toString(new Date().getTime() / 1000);

	private String commentId;
	private String commentTask;
	private String commentStu;
	private String commentContent;
	private String commentTime;
	private String commentTaskName;
	private String commentStuName;
	private String commentStuCollegeId;

	private String commentCmt; //commentComment 作答-教师评语
	private String commentScore; //教师评分

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
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

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentTask() {
		return commentTask;
	}

	public void setCommentTask(String commentTask) {
		this.commentTask = commentTask;
	}

	public String getCommentStu() {
		return commentStu;
	}

	public void setCommentStu(String commentStu) {
		this.commentStu = commentStu;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public String getCommentTaskName() {
		return commentTaskName;
	}

	public void setCommentTaskName(String commentTaskName) {
		this.commentTaskName = commentTaskName;
	}

	public String getCommentStuName() {
		return commentStuName;
	}

	public void setCommentStuName(String commentStuName) {
		this.commentStuName = commentStuName;
	}

	public String getCommentStuCollegeId() {
		return commentStuCollegeId;
	}

	public void setCommentStuCollegeId(String commentStuCollegeId) {
		this.commentStuCollegeId = commentStuCollegeId;
	}

	public String getCommentCmt() {
		return commentCmt;
	}

	public void setCommentCmt(String commentCmt) {
		this.commentCmt = commentCmt;
	}

	public String getCommentScore() {
		return commentScore;
	}

	public void setCommentScore(String commentScore) {
		this.commentScore = commentScore;
	}
}