package ftp;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class ControllerFtpClientsPI {
	private Socket socket = null;
	private BufferedWriter writer = null;
	private BufferedReader reader = null;
	FtpServerResponse ftpResponse = null;
	public boolean connect(String host,int port,String user,String pass){
		try {
			socket = new Socket(host, port);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = null;
	        String responseCode = null;
	        do
	        {
	            line=reader.readLine();
	            if(responseCode == null){
	            	// Substring ( start, end ) đọc ký tự trong 1 chuỗi 
	            	 responseCode=line.substring(0,3);       
	            }           
	            GuiMain.setStatusConnect(line);
	        }
	        while( !(line.startsWith(responseCode) &&  line.charAt(3) == ' '));
			sendCmd("USER " + user);
			ftpResponse = serverReply();
			GuiMain.setStatusConnect(ftpResponse.getResponseLine());
		    sendCmd("PASS " + pass);
		    ftpResponse = serverReply();
		    GuiMain.setStatusConnect(ftpResponse.getResponseLine());
		    if(!ftpResponse.getResponseCode().equals("230")){
		    	GuiMain.setStatusConnect("Tài khoản hoặc Mật khẩu đăng nhập sai! Vui lòng kiểm tra lại!");
		    	return false;
		    }
		    return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			GuiMain.setStatusConnect("Đường dẫn truy cập FTP không đúng hoặc Do kết nối mạng của bạn gặp vấn đề .\nVui lòng kiểm tra lại!");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			GuiMain.setStatusConnect("Không thể kết nối!. Lỗi vào ra " +e);
			e.printStackTrace();
			return false;
		}
		 
	}
	// hàm disconnect
	public boolean disconnect(){
		try {
			if(writer!=null){
					sendCmd("QUIT");
					writer.close();
					reader.close();
					socket.close();
					return true;
			}
			else{
				return false;
			}
		} catch (IOException e) {
			// TODO: handle exception
			return false;
		}
	}
	
	// Gửi lệnh đến Server
	public void sendCmd(String cmd) throws IOException{
		writer.write(cmd+"\r\n");
		writer.flush();
	}
	// Delete 1 file
	public boolean deleteFile(String delete){
		try{
			sendCmd("DELE "+delete);
			ftpResponse = serverReply();
			GuiMain.setStatusConnect(ftpResponse.getResponseLine());
			return true;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}
	public boolean deleteFolder(String delete){
		try{
			sendCmd("RMD "+delete);
			ftpResponse = serverReply();
			GuiMain.setStatusConnect(ftpResponse.getResponseLine());
			return true;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}
	// Tạo mới 1 thư mục
	public boolean makeFolder(String foldeName){
		try{
			sendCmd("MKD "+foldeName);
			ftpResponse = serverReply();
			GuiMain.setStatusConnect(ftpResponse.getResponseLine());
			return true;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}

	// Đổi tên  1 thư mục
		public boolean renameFile(String oldName,String newName){
			try{
				sendCmd("RNFR "+oldName);
				ftpResponse = serverReply();
				GuiMain.setStatusConnect(ftpResponse.getResponseLine());
				sendCmd("RNTO " +newName);
				ftpResponse = serverReply();
				GuiMain.setStatusConnect(ftpResponse.getResponseLine());
				return true;
			}catch(IOException e){
				e.printStackTrace();
				return false;
			}
		}
	/*
	  Trả về thư mục làm việc hiện taị của FTP Server khi Cliet kết nối đến Để gán vô link
	*/
	
	public String getCurrentDirectory() throws IOException {
		 	sendCmd("PWD");
		    String dir = null;
		    ftpResponse = serverReply();
		    dir = ftpResponse.getResponseLine();
		    int firstQuote = dir.indexOf('"');
		    int secondQuote = dir.indexOf('"', firstQuote + 1);
		      if (secondQuote > 0) {
		        dir = dir.substring(firstQuote + 1, secondQuote);
		       
		    }
		    GuiMain.setStatusConnect(ftpResponse.getResponseLine());
		    return dir;
	}
		
	// Server trả lời lại khi client Gửi lệnh yêu cầu
	public FtpServerResponse serverReply() throws IOException{
		String line = null;
        String responseCode = null;
        do
        {
            line=reader.readLine();
            if(responseCode == null)
            responseCode=line.substring(0,3);
        }
        while( !(line.startsWith(responseCode) &&  line.charAt(3) == ' '));
        return new FtpServerResponse(responseCode,line);
	}
}
