package pv;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class scroll extends HttpServlet {

	public static int i = 2;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html; charset=utf-8");
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		String s = st.rfu_utf("http://1gamesearch.blogspot.ca/p/" + i++ + ".html");

		if (!s.contains("qqqbeginqqq")){			
			i=2;
			s = st.rfu_utf("http://1gamesearch.blogspot.ca/p/" + i++ + ".html");
	}

		
		if (s.contains("qqqbeginqqq")){	
			
		int k = s.indexOf("qqqbeginqqq")+11;
		s=s.substring(k);
		k = s.indexOf("qqqendqqq");
		s=s.substring(0,k);		
		
		}
		
		PrintWriter writer = resp.getWriter();
		writer.write(s);
		writer.close();
		resp.flushBuffer();
	}

	private static final long serialVersionUID = 1L;
}