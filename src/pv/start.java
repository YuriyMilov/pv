package pv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class start extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		String s = st.rfu_utf(sh+"/base.html");//http://toklist1.appspot.com
		
		String s1=st.rfu_utf("http://1gamesearch.blogspot.ca/p/qq.html");
				
		int i = s1.indexOf("qqqbeginqqq");
				s1=s1.substring(i);
		
		i = s1.indexOf("qqqendqqq");
				s1=s1.substring(0,i);

		s = s.replace("<!-- qqq -->", s1);
		

		PrintWriter writer = resp.getWriter();
		writer.write(s);
		writer.close();
		resp.flushBuffer();
	}

	private static final long serialVersionUID = 1L;
}