package pv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class js extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static String sposled = "init";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

	
		if (_info.sts1.length() < 1) {
			_info.sts1 = st.rfu_utf("http://1gamesearch.blogspot.ca/p/base.html");
			_info.sts1 = _info.sts1.substring(_info.sts1.indexOf("<!-- qqq -->"));
			_info.sts1 = _info.sts1.substring(0, _info.sts1.indexOf("<!-- /qqq -->"));
			_info.sts2 = _info.sts1;
		}
		

		int i = _info.sts2.indexOf("<li class=");
		String s="";
		if (i > -1) {
			s = _info.sts2.substring(i + 1);
			
			i = s.indexOf("<li class=");
			if (i > 0)
				s = "<li class=" + s.substring(0, i);
				
			_info.sts2 = _info.sts2.substring(1);
			i = _info.sts2.indexOf("<li class=");
			
			if (i > 0)
				_info.sts2=_info.sts2.substring(i);
			else
				_info.sts2=_info.sts1;
			
			
		} 

			
		PrintWriter writer = resp.getWriter();
		writer.write(s);
		writer.close();
		resp.flushBuffer();
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String s = "ok";

		resp.setContentType("text/html; charset=utf-8");
		PrintWriter writer = resp.getWriter();
		writer.write(s);
		writer.close();
		resp.flushBuffer();
	}



}