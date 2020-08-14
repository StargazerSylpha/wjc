package wjc.classes;

import javax.print.DocFlavor;
import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NoticeDao {

	public NoticeDao() {
		//...
	}

	public ArrayList<Notice> showNoticeList() throws Exception{
		ArrayList<Notice> noticeList = new ArrayList<>();
		String queryString = "select * from notice,user where notice.create_user = user.uid;";
		CachedRowSet rs = DbHelper.dbSelect(queryString);

		if(rs != null) {
			while (rs.next()) {
				Notice n = new Notice();
				n.setNoticeId(rs.getInt("notice_id") + "");
				n.setNoticeSubject(rs.getString("notice_subject"));
				n.setNoticeContent(rs.getString("notice_content"));
				n.setCreateUser(rs.getInt("create_user") + "");
				n.setCreateTime(rs.getString("create_time"));
				n.setLastChange(rs.getString("last_change"));
				n.setCreateUserName(rs.getString("user_name"));
				noticeList.add(n);
			}
		}
		if(noticeList.size() > 0) {
			return noticeList;
		}
		return noticeList;
	}

	public boolean checkNoticeId(String _noticeId) {

		if(_noticeId == null) {
			return false;
		}
		int noticeIdInt = Integer.parseInt(_noticeId);

		if(noticeIdInt <= 0) {
			return false;
		}
		//字符不检查，只检查长度
		return true;
	}

	public boolean checkNoticeSubject(String _noticeSubject) {

		if(_noticeSubject == null) {
			return false;
		}
		if(_noticeSubject.length() < 1) {
			return false;
		}
		return true;
	}

	public boolean checkNoticeContent(String _noticeContent) {
		if(_noticeContent == null) {
			return false;
		}
		if(_noticeContent.length() < 4) {
			return false;
		}
		return true;
	}

	public Notice showNoticeDetail(String _noticeId) throws Exception {
		ArrayList<Notice> noticeResult = new ArrayList<>();
		String queryString = "select * from notice,user where notice.create_user = user.uid and notice_id=" + _noticeId + ";";
		CachedRowSet rs = null;
		Notice n = new Notice();

		if(checkNoticeId(_noticeId)) {
			rs = DbHelper.dbSelect(queryString);
			if(rs != null) {
				while(rs.next()) {
					Notice n_ = new Notice();
					n_.setNoticeId(rs.getInt("notice_id") + "");
					n_.setNoticeSubject(rs.getString("notice_subject"));
					n_.setNoticeContent(rs.getString("notice_content"));
					n_.setCreateUser(rs.getString("create_user"));
					n_.setCreateTime(rs.getString("create_time"));
					n_.setLastChange(rs.getString("last_change"));
					n_.setCreateUserName(rs.getString("user_name"));
					noticeResult.add(n_);
				}
			}

			if(noticeResult.size() == 1) {
				n = noticeResult.get(0);
				return  n;
			}
		}
		return n;
		
	}

	public int editNotice(Notice _n,String _uid) throws Exception {
		String queryString = new String();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ts = sdf.format(new Date());
		queryString = "INSERT INTO notice(notice_subject, notice_content,create_user,create_time, last_change) VALUES ('" + _n.getNoticeSubject() +"','" + _n.getNoticeContent() + "','" + _uid + "','" + ts + "','" + ts + "');";
		int errCode = -1;
		UserDao ud = new UserDao();

		if(checkNoticeSubject(_n.getNoticeSubject())) {
			if(checkNoticeContent(_n.getNoticeContent())) {
				if(ud.checkUid(_uid)) {
					errCode = DbHelper.dbChange(queryString);
				} else {
					errCode = 4007;
				}
			} else {
				errCode = 4004;
			}
		} else {
			errCode = 4005;
		}

		return errCode;
	}

	public int deleteNotice(String _noticeId,String _uid) throws Exception {
		String queryString = new String();
		queryString = "delete from notice where notice_id = '" + _noticeId +"' and create_user='" + _uid +"';";
		UserDao ud = new UserDao();
		int errCode = -1;

		if(ud.checkUid(_uid)) {
			if(checkNoticeId(_noticeId)) {
				errCode = DbHelper.dbChange(queryString);
			} else {
				errCode = 4011;
			}
		} else {
			errCode = 4012;
		}

		return errCode;
	}
}
