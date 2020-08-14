package wjc.classes;

public class Course {
	private String courseId;
	private String courseName;
	private String courseTeacher;
	private String courseTeacherName;
	private String courseIntro;
	private String courseTime;

	public Course() {
		//...
	}

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
}
