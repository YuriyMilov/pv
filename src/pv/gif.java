package pv;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

public class gif extends HttpServlet {
	public static String semailto = "ymilov@gmail.com";
	public static String semailfrom = "admin@ddtor.com";

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
	
		resp.setContentType("image/gif");
		resp.getOutputStream().write(get_bb());

	}

	public static byte[] get_bb() {
		String s1 = "R0lGODlhAQABAPAAAAAAAAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==";
		return new Base64().decode(s1.getBytes());
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
		
		/*
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

		String s = "";// stkl.rfu_utf(sh+"/news");
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("HH:mm '(Мск)'");
		ft.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
		String subj = ft.format(dNow);
		try {
			s = req.getQueryString();
			if (s.equals("qq3")||s.equals("qq2")) {
				
				subj=s+" "+subj;
				
				s = s+" <form method=post action=\"http://www.iplocation.net/\"><input type=text name=query value="+req.getRemoteAddr()+"> "
						+ "<input type=submit></form>";
				//sm_htm(new String[] { semailto }, subj, s);
			}
		} catch (Exception qq1) {
		}
		*/
	}

	public static void sm_htm(String[] to, String subject, String body) {

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);

		try {
			try {
				msg.setFrom(new InternetAddress(semailfrom, "Kuka Tverskoy"));
			} catch (UnsupportedEncodingException e1) {
				msg.setFrom(new InternetAddress(semailfrom));
			}
			InternetAddress[] toAddress = new InternetAddress[to.length];
			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}
			for (int i = 0; i < toAddress.length; i++) {
				msg.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}
			msg.setSubject(subject);
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(textPart);
			msg.setContent(mp);
			try {
				msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Transport.send(msg);

		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	private static final long serialVersionUID = 1L;

}