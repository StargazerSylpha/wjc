package wjc.servlet;

import wjc.classes.Course;
import wjc.classes.CourseDao;
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
import java.util.ArrayList;

@WebServlet(name = "ShowCourseListServlet",urlPatterns = "/console/showCourseList")
public class ShowCourseListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		String courseHtml = "";
		int userType = -1;
		int errCode = -1;
		UserDao ud = new UserDao();
		ArrayList<Course> alc = new ArrayList<>();
		CourseDao cd = new CourseDao();

		HttpSession sess = _request.getSession();
		Object sessUid = sess.getAttribute("sessUid");
		userType = ud.getUserType(sessUid);
		if(userType == 1 || userType == 2) {
			//教师和学生可以查看，非登录人员不能查看
			try {
				alc = cd.showCourseList();
			} catch (Exception e) {
				System.out.println("8002-获取课程列表异常-" + e.getMessage());
				//e.printStackTrace();
			} finally {
				for(int i = alc.size() -1;i > -1; i--) {
					Course c = alc.get(i);
					courseHtml = courseHtml + "<tr><td>" + c.getCourseId() + "</td><td><a href='courseDetail.jsp?courseId=" +c.getCourseId() + "'>" + c.getCourseName() +
							"</a></td><td>" + c.getCourseTeacherName() + "</td><td>" + c.getCourseTime() + "</td><td><a href='javascript:deleteCourse(" + c.getCourseId() +")'>删除</a></td></tr>";
				}
			}
		} else {
			errCode = 8001;
		}
		out.println(courseHtml);
	}
}
