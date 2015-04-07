package pv;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;

import javax.mail.internet.MimeUtility;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
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
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.jsoup.Jsoup;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.KeyFactory;

import java.nio.charset.Charset;

public class st {



	public static String get_first(String s, String s1, String s2) {
		int i1 = s.indexOf(s1);
		if (i1 > 0)
			s = s.substring(i1);

		int i2 = s.indexOf(s2) + s2.length();
		if (i2 > 0)
			s = s.substring(0, i2);

	//	s = Jsoup.parse(s).text();

		return s;
	}

	public static String rem_all_sub(String s, String s1, String s2) {
		int i = s.indexOf(s1);
		String s3 = "";
		if (i > -1) {
			if (i > 0) {
				s3 = s.substring(0, i);
				s = s.substring(i);
			}

			String[] sss = s.split(s1);
			s = "";
			for (String ss : sss) {
				i = ss.indexOf(s2);
				if (i > -1)
					s = s + ss.substring(i + s2.length());
			}
		}
		return s3 + s;
	}

	public static String fir(String sfilename) {
		String sid = "Files", skk = "files", s = "", sfn = "";
		DatastoreService dbf = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey(sid, skk);
		Query query = new Query(sid, key).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> spisok = dbf.prepare(query).asList(
				FetchOptions.Builder.withLimit(10));
		for (Entity ent : spisok) {
			sfn = ((String) ent.getProperty("file_name")).trim();

			if (sfn.equals(sfilename))
				s = ((Text) ent.getProperty("content")).getValue();
		}
		return s;
	}

	public static void fiw(String sfilename, String s) {
		String sid = "Files", skk = "files";

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key = KeyFactory.createKey(sid, skk);
		Query query = new Query(sid, key).addSort("date",
				Query.SortDirection.DESCENDING);
		List<Entity> spisok = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(10));
		for (Entity ent : spisok) {
			if (((String) ent.getProperty("file_name")).trim()
					.equals(sfilename))
				datastore.delete(ent.getKey());
		}

		Entity ent = new Entity(sid, key);
		ent.setProperty("file_name", sfilename);
		ent.setProperty("content", new Text(s));
		ent.setProperty("date", new Date());

		datastore.put(ent);
	}



	public static String clean_htm(String s) {
		s = s.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">")
				.replace("&quot;", "\"");
		s = s.replace("#036;", "$");
		return s;
	}

	public static int get_random_number(int i, int k) {

		Random rand = new Random();
		k = i + rand.nextInt(k - i + 1);
		return k;
	}

	public static String decodeUTF8(byte[] bytes) {
		return new String(bytes, Charset.forName("UTF-8"));
	}

	public static byte[] encodeUTF8(String string) {
		return string.getBytes(Charset.forName("UTF-8"));
	}

	public static String rfu_utf(String s) {
		try {
			URL url = new URL(s);

			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf8"));
			s = "";
			String thisLine = "";
			while ((thisLine = br.readLine()) != null) { // while loop begins
															// here
				s = s + thisLine + "\r\n";
			}
			br.close();
			return s.toString();

		} catch (Exception e) {
			return e.toString();
		}
	}

	public static String post(String surl, String body) {
		String s = "";

		try {

			URL url = new URL(surl);
			URLConnection urlConnection = url.openConnection();
			DataOutputStream outStream;

			// Build request body
			// Create connection

			((HttpURLConnection) urlConnection).setRequestMethod("POST");
			urlConnection.setConnectTimeout(0);
			urlConnection.setReadTimeout(0);
			urlConnection.setDoInput(true);
			urlConnection.setDoOutput(true);
			urlConnection.setUseCaches(false);
			urlConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			urlConnection.setRequestProperty("Content-Length",
					"" + body.length());

			// Create I/O streams
			outStream = new DataOutputStream(urlConnection.getOutputStream());

			// Send request
			outStream.writeBytes(body);
			outStream.flush();
			outStream.close();

			// Get Response

			BufferedReader br = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream(), "utf8"));
			s = "";
			String thisLine = "";
			while ((thisLine = br.readLine()) != null) {
				s = s + thisLine + "\r\n";
			}
			br.close();

		} catch (Exception ex) {
			s = ex.toString();
		}
		return s;
	}

	public static String send_mail(String from_name, String from_address,
			String to_name, String to_address, String subj, String body) {
		String s = "ok";
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from_address, MimeUtility
					.encodeText(from_name, "utf-8", "B")));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to_address, MimeUtility.encodeText(to_name, "utf-8", "B")));
			msg.setSubject(MimeUtility.encodeText(
					MimeUtility.encodeText(subj, "utf-8", "B"), "utf-8", "B"));

			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(textPart);
			msg.setContent(mp);
			Transport.send(msg);

		} catch (Exception e) {
			s = e.toString();
		}
		return s;
	}

	public static String sm2a(String from_name, String from_address,
			String subject, String body) {
		String s = "ok";
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from_address, MimeUtility
					.encodeText(from_name, "utf-8", "B")));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"admins"));
			msg.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
			msg.setText(body);

			Multipart mp = new MimeMultipart();
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			// textPart.setContent(body, "text/html");
			mp.addBodyPart(textPart);
			Transport.send(msg);
		} catch (Exception e) {
			s = e.toString();
		}
		return s;
	}


	public static void smtxt(String from,String to, String s) throws Exception {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
				to));
		msg.setSubject("Your Example.com account has been activated");
		msg.setText("msgBody");

		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setText(s);

		// Create a multipar message
		Multipart multipart = new MimeMultipart();

		// Set text message part
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		messageBodyPart = new MimeBodyPart();
		String filename = "file.txt";

		DataSource source = new ByteArrayDataSource(
				"xmlData".getBytes("utf-8"), "text/xml; charset=utf-8");
		messageBodyPart.setDataHandler(new DataHandler(source));
		messageBodyPart.setFileName(filename);
		messageBodyPart.addHeader("Content-Type", " text/xml; charset=utf-8");
		multipart.addBodyPart(messageBodyPart);

		// Send the complete message parts
		msg.setContent(multipart);

		Transport.send(msg);
	}

	public static void send_file(HttpServletRequest req,
			HttpServletResponse resp, String s) throws IOException {
		ServletOutputStream out = resp.getOutputStream();
		resp.setContentType("text/xml");
		resp.setHeader("Content-Disposition", "attachment; filename=owl.xml");
		byte[] b = s.getBytes("UTF8");
		out.write(b);
	}

	public static String posti(String surl, String sname, String scontent) {

		try {
			// Encode the query
			String postData = "f=" + URLEncoder.encode(sname, "UTF-8") + "&s="
					+ URLEncoder.encode(scontent, "UTF-8");

			URL url = new URL(surl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					String.valueOf(postData.length()));

			// Write data
			OutputStream os = connection.getOutputStream();
			os.write(postData.getBytes());

			// Read response
			StringBuilder responseSB = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String line;
			while ((line = br.readLine()) != null)
				responseSB.append(line);

			// Close streams
			br.close();
			os.close();

			return responseSB.toString();
		} catch (Exception e) {
			return e.toString();
		}
	}

	public static String get_is(InputStream is) throws Exception {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while ((i = is.read()) != -1)
			sb.append((char) i);
		return sb.toString();
	}




	public static void sendFromGMail(String from, String pass, String[] to,
			String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	public static void sendFromGMail5(String from, String pass, String[] to,
			String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];

			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}

			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}

			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	public static void sendFromGMail_html(String from, String pass,
			String[] to, String subject, String body) {
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			InternetAddress[] toAddress = new InternetAddress[to.length];
			// To get the array of addresses
			for (int i = 0; i < to.length; i++) {
				toAddress[i] = new InternetAddress(to[i]);
			}
			for (int i = 0; i < toAddress.length; i++) {
				message.addRecipient(Message.RecipientType.TO, toAddress[i]);
			}
			message.setSubject(subject);
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(textPart);
			message.setContent(mp);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}

	public static String get_date_msk() {

		Date dd = new Date();

		TimeZone tz = TimeZone.getTimeZone("Europe/Moscow");
		Calendar cc = Calendar.getInstance(tz);
		cc.setTime(dd);
		cc.add(Calendar.HOUR, -1);

		String s_hh = String.valueOf(cc.get(Calendar.HOUR_OF_DAY));
		String s_mm = String.valueOf(String.format("%02d",
				cc.get(Calendar.MINUTE)));
		String s_dofm = String.valueOf(cc.get(Calendar.DAY_OF_MONTH));
		String s_dow = String.valueOf(cc.get(Calendar.DAY_OF_WEEK))
				.replace("1", "воскресенье").replace("2", "понедельник")
				.replace("3", "вторник").replace("4", "среда")
				.replace("5", "четверг").replace("6", "пятница")
				.replace("7", "суббота");

		String s = String.valueOf(cc.get(Calendar.MONTH));
		s = s.replace("0", " января ").replace("1", " февраля ")
				.replace("2", " марта ").replace("3", " апреля ")
				.replace("4", " мая ").replace("5", " июня ")
				.replace("6", " июня ").replace("7", " августа ")
				.replace("8", " сентября ").replace("9", " октября ")
				.replace("10", " ноября ").replace("11", " декабря ");

		s = s_hh + ":" + s_mm + " " + s_dow + " " + s_dofm + s;

		return s;
	}

	
	public static boolean date_old(String dt) {
		// dt = "Thu, 19 Mar 2015 19:41:42 GMT";
		// dt = "20 Mar 2015 19:53:19 +0300";
		boolean bb = false;
		Calendar cc = Calendar.getInstance();
		cc.add(Calendar.HOUR, -1);
		Date dc = cc.getTime();
		Date d2 = new Date();
		try {
			d2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz")
					.parse(dt);
		} catch (ParseException e) {
			try {
				d2 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss zzz").parse(dt);
			} catch (ParseException e1) {
				System.err.println(e1.toString());
			}
		}
		int n = d2.compareTo(dc);
		if (n == -1)
			bb = true;
		return bb;
	}

	public static String get_date() {

		Date dd = new Date();

		
	
		String s = dd.toString().substring(10,20);
		
		return s;
	}
	
	
	
}