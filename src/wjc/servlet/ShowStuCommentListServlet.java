package wjc.servlet;

import wjc.classes.Comment;
import wjc.classes.CommentDao;
import wjc.classes.Course;
import wjc.classes.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ShowStuCommentListServlet",urlPatterns = "/console/showStuCommentList")
public class ShowStuCommentListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		Object sessUid = _request.getSession().getAttribute("sessUid");
		int userType = -1;
		UserDao ud = new UserDao();
		userType = ud.getUserType(sessUid);
		ArrayList<Comment> alc = new ArrayList<>();
		CommentDao cd = new CommentDao();
		String commentHtml = "";

		if(userType == 2) {
			try {
				alc = cd.showStuCommentList(sessUid.toString());
			} catch (Exception e) {
				System.out.println("8006-获取学生作答列表异常-" + e.getMessage());
			} finally {
				for(int i = alc.size() -1;i > -1; i--) {
					Comment c = alc.get(i);
					String cct = "";
					String cs = "";
					String cc = "";
					cct = (c.getCommentContent().length() > 6) ? (c.getCommentContent().substring(0,5)) + "..." : (c.getCommentContent());
					cs = c.getCommentScore() == null ? "" : c.getCommentScore();
					cc = c.getCommentCmt() == null ? "" : (c.getCommentCmt().length() > 6 ? c.getCommentCmt().substring(0,5) + "..." : c.getCommentCmt());

					commentHtml = commentHtml + "<tr><td>" + c.getCommentId() + "</td><td><a href='taskDetail.jsp?taskId=" +c.getCommentTask() + "'>" + c.getCommentTaskName() +
							"</a></td><td>" + cct + "</td><td>" + c.getCommentTime() + "</td><td>" + cc + "</td><td>" + cs +
							"</td><td><a href='commentDetail.jsp?commentId=" + c.getCommentId() +"'>详情</a></td></tr>";
				}
			}
		}

		out.println(commentHtml);
	}
}
