package pv;

// GAE toklist1.appspot admin at ddtor quick-0
// 1gamesearch.blogspot.com

public class _info {

	public static String gm = "";
	public static String gm1 = "";
	public static String gm2 = "";
	public static String viz = "";
	public static String rss = "";
	public static String sts1 = "";
	public static String sts2 = "";

	public static void main(String[] args) throws Exception {

		String ss="",s = "http://www.agame.com/games/new?page=7", s2="", s3="";
		
		s="http://www.girlsgogames.com/games/popular?page=2";
			s = st.rfu_utf(s);
		
			//int i = s.indexOf("<ul class=\"list-tiles clearfix\">");			
			
			int i=s.indexOf("<li class=\"grid-col\">");	
			s = s.substring(i);
			i=s.lastIndexOf("<li class=\"grid-col\">");	
			s = s.substring(0,i);
			
			System.out.println("qqqbeginqqq<br/><br/>\r\n");
			
		
			s=s.replace(("<li class=\"grid-col\">"), "<div class=\"box col1\">").replace("</li>", "</div>").replace("href=\"/game", "href=\"http://www.girlsgogames.com/game").replace("href=","target=\"_blank\" href=");
			
			System.out.println(s+"<br/><br/>qqqendqqq\r\n");
	

	
		/*
		 * 
		 * i=st.get_random_number(2, i);
		 * 
		 * int i=s.indexOf("<li class=\"tile\">"); s=s.substring(i);
		 * 
		 * int k=s.indexOf("</ul>"); s=s.substring(0,k); i=0; String[]
		 * sss=s.split("<li class=\"tile\">"); s=""; for (String s1: sss ) {
		 * if(i++ >0) s=s+ "<li class=\"tile\">" +s1;
		 * 
		 * 
		 * }
		 */

	}

}
