package wjc.servlet;

import com.alibaba.fastjson.JSON;
import wjc.classes.Notice;
import wjc.classes.NoticeDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "ShowNoticeListServlet", urlPatterns = "/console/showNoticeList")
public class ShowNoticeListServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
		_request.setCharacterEncoding("utf-8");
		_response.setCharacterEncoding("utf-8");
		PrintWriter out = _response.getWriter();

		NoticeDao nd = new NoticeDao();
		ArrayList<Notice> aln = new ArrayList<>();

		String noticeHtml = "";

		int errCode = -1;

		try {
			aln = nd.showNoticeList();
			errCode = 0;
		} catch (Exception e) {
			System.out.println("4001-获取公告列表失败");
			errCode = 4001;
		} finally {
			for(int i = aln.size() -1;i > -1; i--) {
				Notice n = aln.get(i);
				noticeHtml = noticeHtml + "<tr><td>" + n.getNoticeId() + "</td><td><a href='noticeDetail.jsp?noticeId=" + n.getNoticeId() + "'>" + n.getNoticeSubject() +
						"</a></td><td>" +n.getCreateUserName() + "</td><td>" + n.getCreateTime() + "</td><td>" +
						n.getLastChange() + "</td><td><a href='javascript:deleteNotice(" + n.getNoticeId() +")'>删除</a></td></tr>";
			}
		}
		out.println(noticeHtml);
	}
}
