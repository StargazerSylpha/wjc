package wjc.classes;

public class Comment {

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


	public Comment() {
		//...
	}

	public String getCommentStuCollegeId() {
		return commentStuCollegeId;
	}

	public void setCommentStuCollegeId(String commentStuCollegeId) {
		this.commentStuCollegeId = commentStuCollegeId;
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
