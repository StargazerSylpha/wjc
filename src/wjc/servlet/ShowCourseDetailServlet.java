package wjc.servlet;

import com.alibaba.fastjson.JSON;
import wjc.classes.Course;
import wjc.classes.CourseDao;
import wjc.classes.ErrorHelper;
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

@WebServlet(name = "ShowCourseDetailServlet",urlPatterns = "/console/showCourseDetail")
public class ShowCourseDetailServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		int userType = -1;
		int errCode = -1;
		UserDao ud = new UserDao();
		CourseDao cd = new CourseDao();
		String courseId = _request.getParameter("courseId");
		Object sessUid = _request.getSession().getAttribute("sessUid");
		userType = ud.getUserType(sessUid);
		Course c = new Course();
		CourseDetail cdj = new CourseDetail();

		if(userType == 1 || userType == 2) {
			try {
				c = cd.showCourseDetail(courseId);
			} catch (Exception e) {
				System.out.println("8004-获取课程详情异常-" + e.getMessage());
				errCode = 8004;
			} finally {
				if(c.getCourseId() == null) {
					errCode = 8005;
				} else {
					errCode = 0;
				}
			}
		} else {
			errCode = 8003;
		}

		cdj.setErrCode(errCode + "");
		cdj.setErrMsg(ErrorHelper.getErrMsg(errCode));
		cdj.setCourseId(c.getCourseId());
		cdj.setCourseName(c.getCourseName());
		cdj.setCourseTeacherName(c.getCourseTeacherName());
		cdj.setCourseTime(c.getCourseTime());
		cdj.setCourseIntro(c.getCourseIntro());

		out.println(JSON.toJSONString(cdj));
	}
}

class CourseDetail {
	private String courseId;
	private String courseName;
	private String courseTeacher;
	private String courseTeacherName;
	private String courseIntro;
	private String courseTime;

	private String errCode;
	private String errMsg;
	private String errTime = Long.toString(new Date().getTime() / 1000);

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseTeacher() {
		return courseTeacher;
	}

	public void setCourseTeacher(String courseTeacher) {
		this.courseTeacher = courseTeacher;
	}

	public String getCourseTeacherName() {
		return courseTeacherName;
	}

	public void setCourseTeacherName(String courseTeacherName) {
		this.courseTeacherName = courseTeacherName;
	}

	public String getCourseIntro() {
		return courseIntro;
	}

	public void setCourseIntro(String courseIntro) {
		this.courseIntro = courseIntro;
	}

	public String getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}

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
}