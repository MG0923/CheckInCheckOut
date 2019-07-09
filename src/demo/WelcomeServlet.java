package demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;


public class WelcomeServlet extends HttpServlet
{
	private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter();
		
		out.println("  <html>");
		out.println("  <body background=\"images/bgimage.jpg\">");
		out.println("  </body></html>");
		
		HttpSession session=request.getSession();
		String name=(String) session.getAttribute("username");//request.getParameter("userName");
		String sCurrentLine;
		String lastLine = "";
		String[] status = null;
		int flag=0;
		//session.setAttribute("USER",name);
		Path path=FileSystems.getDefault().getPath("mohit");
		System.out.println(Paths.get("").toAbsolutePath().toString());
		System.out.println("Path: "+path);
		File file = new File(Paths.get("").toAbsolutePath().toString()+"/log.txt");
		 
         //Create the file
		try {
			if (file.createNewFile()){
		           System.out.println("File is created!");
		         }else{
		           System.out.println("File already exists.");
		         }	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
         
         BufferedReader br = new BufferedReader(new FileReader(Paths.get("").toAbsolutePath().toString()+"/log.txt"));
         //Read the file
         //FileReader fr=new FileReader("d://testFile1.txt");    
         RequestDispatcher rd=request.getRequestDispatcher("/Welcome.jsp");
         int i;    
         if(file.length()==0)
         {
        	 System.out.println("File is empty");
        	 request.setAttribute("flag", 0);
        	 request.setAttribute("fileEmpty", 1);
        	 request.setAttribute("name"," ");
             request.setAttribute("UserName",name);
             request.setAttribute("checkedOutDate"," ");
             request.setAttribute("checkedOutTime"," ");
             request.setAttribute("filePath",Paths.get("").toAbsolutePath().toString()+"/log.txt");
        	 rd.forward(request, response);
         }
         else
         {
             while ((sCurrentLine = br.readLine()) != null) 
             {
                 lastLine = sCurrentLine;
             }
             br.close();
             System.out.println(lastLine);
             status = lastLine.split("\\s+");  //regex expression for multiple spaces
             System.out.println(status[1]);
             if(status[1].equals("Checked-out"))
             	flag=1;
             else
            	 flag=0;
             request.setAttribute("fileEmpty", 0);
             request.setAttribute("flag",flag);
             request.setAttribute("name",status[0]);
             request.setAttribute("UserName",name);
             request.setAttribute("checkedOutDate",status[2]);
             request.setAttribute("checkedOutTime",status[3]);
             request.setAttribute("filePath",Paths.get("").toAbsolutePath().toString()+"/log.txt");
             /*
             response.setHeader("Pragma","no-cache"); 
             response.setHeader("Cache-Control","no-store"); 
             response.setHeader("Expires","0"); 
             response.setDateHeader("Expires",-1);*/ 
             rd.forward(request, response);
         }
         
         System.out.println(flag);
	}
}