package wjc.classes;

import javax.sql.rowset.CachedRowSet;
import java.util.ArrayList;

public class CourseDao {
	public CourseDao() {
		//...
	}

	public boolean checkCourseId(String _courseId) {

		if(_courseId == null) {
			return false;
		}
		int courseIdInt = Integer.parseInt(_courseId);

		if(courseIdInt <= 0) {
			return false;
		}
		//字符不检查，只检查长度
		return true;
	}

	public ArrayList<Course> showCourseList() throws Exception{
		ArrayList<Course> courseList = new ArrayList<>();
		String queryString = "select * from course,user where course.course_teacher = user.uid;";
		CachedRowSet rs = DbHelper.dbSelect(queryString);

		if(rs != null) {
			while (rs.next()) {
				Course c = new Course();
				c.setCourseId(rs.getString("course_id"));
				c.setCourseName(rs.getString("course_name"));
				c.setCourseTeacherName(rs.getString("user_name"));
				c.setCourseTime(rs.getString("create_time"));
				courseList.add(c);
			}
		}

		return courseList;
	}

	public Course showCourseDetail(String _courseId) throws Exception {
		ArrayList<Course> courseResult = new ArrayList<>();
		String queryString = "select * from course,user where course.course_teacher = user.uid and course_id=" + _courseId + ";";
		CachedRowSet rs = null;
		Course c = new Course();

		if(checkCourseId(_courseId)) {
			rs = DbHelper.dbSelect(queryString);
			if(rs != null) {
				while(rs.next()) {
					Course c_ = new Course();
					c_.setCourseId(rs.getString("course_id"));
					c_.setCourseName(rs.getString("course_name"));
					c_.setCourseTeacherName(rs.getString("user_name"));
					c_.setCourseTime(rs.getString("create_time"));//是create_time不是course_time，千万别写错！
					c_.setCourseIntro(rs.getString("course_intro"));
					courseResult.add(c_);
				}
			}

			if(courseResult.size() == 1) {
				c = courseResult.get(0);
				return  c;
			}
		}
		return c;

	}
}
