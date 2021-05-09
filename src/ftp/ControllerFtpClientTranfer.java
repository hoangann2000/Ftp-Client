package ftp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
public class ControllerFtpClientTranfer{
	ControllerFtpClientsPI clientPI;
	FtpServerResponse ftpResponse;
	int percentCompleted = 0;
	long totalByteRead = 0;
	Socket dataTransferSocket;
	public ControllerFtpClientTranfer(ControllerFtpClientsPI clientPI){
		this.clientPI = clientPI;
	}
	
	// Bóc tách dùng để lấy địa chỉ 
	private String[] getDataSocketAddress(String s){
		//  trả về vị trí ( trong String s
        int beginIdx = s.indexOf('(');
        // trả về vị trí ) trong String s
        int endIdx = s.indexOf(')');
        if(beginIdx < 0 ||endIdx < 0 ){
            return null;
        }
        // lấy giá trị sau ( và trước ) 
        String addressString = s.substring(beginIdx+1, endIdx);
        String [] cat = addressString.split(",");
        return cat;
       
    }
	
	// Lấy danh sách các file từ Server
	public  ArrayList<FileHost> listFile(String path){
		ArrayList<FileHost> arrList = new ArrayList<FileHost>();
		ImageIcon folder = new ImageIcon("./image/Live Folder Green.png");
		ImageIcon fileBackHome = new ImageIcon("./image/folderhome.png");
		ImageIcon fileBack = new ImageIcon("./image/Folder back.png");
		String line = null,fileName = null,size = null,kieuFile =null,date = null,thuMucCon = null;
		String [] s = null;
		String [][] arrayTail = {{".ai","ai.png"},{".avi","avi.png"},{".css","css.png"},{".csv","csv.png"},{".dbf","dbf.png"},{".doc","doc.png"},{".dwg","dwg.png"},
				{".exe","exe.png"},{".fla","fla.png"},{".html","html.png"},{".iso","iso.png"},{".js","javascript.png"},{".jpg","jpg.png"},{".json","json-file.png"},{".mp3","mp3.png"},
				{".mp4","mp4.png"},{".pdf","pdf.png"},{".png","png.png"},{".ppt","ppt.png"},{".psd","psd.png"},{".rtf","rtf.png"},{".svg","svg.png"},{".txt","txt.png"},{".xls","xls.png"},
				{".xml","xml.png"},{".zip","zip.png"},{".php","php.png"},{".java","java.png"},{".MP3","MP3.png"}};
		String tail = "file.png";
		try {	        
	        // Lấy địa chỉ port từ server DTP
			clientPI.sendCmd("CWD "+path);
			ftpResponse = clientPI.serverReply();
	        System.out.println(ftpResponse.getResponseLine());
	        GuiMain.setStatusConnect(ftpResponse.getResponseLine());
	        
	        clientPI.sendCmd("PASV");
	        ftpResponse = clientPI.serverReply();
	        System.out.println(ftpResponse.getResponseLine());
	        GuiMain.setStatusConnect(ftpResponse.getResponseLine());
	        
	        String[] address = getDataSocketAddress(ftpResponse.getResponseLine());
	        String hostName = address[0];
	        for(int i=1;i<4;i++)
	        {
	            hostName += "."+address[i];
	        }
	        int port = (Integer.parseInt(address[4]) *256)+Integer.parseInt(address[5]);

	        // kết nối đến server DTP
	        dataTransferSocket = new Socket(hostName,port);
			clientPI.sendCmd("LIST");
			ftpResponse = clientPI.serverReply();
			GuiMain.setStatusConnect(ftpResponse.getResponseLine());
			System.out.println(ftpResponse.getResponseLine());
			
			BufferedReader reader  = new BufferedReader(new InputStreamReader(dataTransferSocket.getInputStream()));
			while ((line = reader.readLine())!=null) {
				System.out.println(line);
				s = line.split("\\s+");
				kieuFile = s[0];
				size = s[4];
				thuMucCon = s[1];
				date = " "+s[5]+" "+s[6]+" "+s[7];		
				s = line.split("\\s+",9);
				fileName = s[8];
				
				for(int i = 0;i < arrayTail.length;i++){
					if(fileName.endsWith(arrayTail[i][0])){
						tail = arrayTail[i][1];
						break;
					}
					else tail = "file.png";
				}
				if(kieuFile.equals("drwxr-xr-x")&&fileName.equals(".")){
					arrList.add(new FileHost(fileBackHome, fileName,thuMucCon, "", date));
				}
				else if(kieuFile.equals("drwx--x---")&&fileName.equals("..")){
					arrList.add(new FileHost(fileBack, fileName,thuMucCon, "", date));
				}
				else if(kieuFile.equals("drwxr-xr-x")){
					arrList.add(new FileHost(folder, fileName,thuMucCon, "", date));
				}
				else if(kieuFile.equals("-rw-r--r--")){
					ImageIcon file = new ImageIcon("./iconfile/" + tail);
					arrList.add(new FileHost(file,fileName ,thuMucCon,size, date));
				}
			}
			reader.close();
			dataTransferSocket.close();
			ftpResponse = clientPI.serverReply();
			GuiMain.setStatusConnect(ftpResponse.getResponseLine());
			System.out.println(ftpResponse.getResponseLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrList;
	} 
	
	public void dowloadFile(FileDowUp fileDowUp){
		try {
			clientPI.sendCmd("TYPE I");
	        ftpResponse = clientPI.serverReply();
	        System.out.println(ftpResponse.getResponseLine());
	        GuiMain.setStatusConnect(ftpResponse.getResponseLine());
	        
	        // Get the port address of the server DTP
	        clientPI.sendCmd("PASV");
	        ftpResponse = clientPI.serverReply();
	        System.out.println(ftpResponse.getResponseLine());
	        GuiMain.setStatusConnect(ftpResponse.getResponseLine());
	        
	        String[] address = getDataSocketAddress(ftpResponse.getResponseLine());  
	        String hostName = address[0];
	        for(int i=1;i<4;i++)
	        {
	            hostName += "."+address[i];
	        }
	        int port = (Integer.parseInt(address[4]) *256)+Integer.parseInt(address[5]);

	        // Kết nối đến server DTP
	        dataTransferSocket = new Socket(hostName,port);
	        // Đưa ra lệnh RETR, nhận 1 file về local
	        clientPI.sendCmd("RETR "+fileDowUp.getURL());
	        ftpResponse = clientPI.serverReply();
	        System.out.println(ftpResponse.getResponseLine());
	        GuiMain.setStatusConnect(ftpResponse.getResponseLine());
	        // tải dữ liệu từ socket xuống hệ thống tệp
	        File file = new File(fileDowUp.getPath());
	        FileOutputStream outputStream = new FileOutputStream(file);
	        InputStream inputStream = dataTransferSocket.getInputStream();
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	        while((bytesRead = inputStream.read(buffer)) != -1)
	        {
	            outputStream.write(buffer, 0, bytesRead);
	            totalByteRead +=bytesRead;
				percentCompleted = (int) (totalByteRead*100/fileDowUp.getSize());
				GuiMain.setTienTrinhDow(percentCompleted);
	        }
	        if(fileDowUp.getSize() == 0){
	        	GuiMain.setTienTrinhDow(100);
	        }
	        outputStream.flush();
	        outputStream.close();
	        inputStream.close();

	        dataTransferSocket.close();
	        // Nhận reply từ server
	        ftpResponse = clientPI.serverReply();
	        System.out.println(ftpResponse.getResponseLine());
	        GuiMain.setStatusConnect(ftpResponse.getResponseLine());
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
	
	public void uploadFile(FileDowUp fileDowUp){
		try {
			clientPI.sendCmd("TYPE I");
	        ftpResponse = clientPI.serverReply();
	        System.out.println(ftpResponse.getResponseLine());
	        GuiMain.setStatusConnect(ftpResponse.getResponseLine());
	        
	        // Lấy port từ server DTP
	        clientPI.sendCmd("PASV");
	        ftpResponse = clientPI.serverReply();
	        System.out.println(ftpResponse.getResponseLine());
	        GuiMain.setStatusConnect(ftpResponse.getResponseLine());
	        
	        String[] address = getDataSocketAddress(ftpResponse.getResponseLine());
	        String hostName = address[0];
	        for(int i=1;i<4;i++)
	        {
	            hostName += "."+address[i];
	        }
	        int port = (Integer.parseInt(address[4]) *256)+Integer.parseInt(address[5]);

	        // Kết nối đến server DTP
	        dataTransferSocket = new Socket(hostName,port);
	        // Đưa ra lệnh RETR, nhận 1 file về local
	        clientPI.sendCmd("STOR "+fileDowUp.getURL());
	        ftpResponse = clientPI.serverReply();
	        System.out.println(ftpResponse.getResponseLine());
	        GuiMain.setStatusConnect(ftpResponse.getResponseLine());
	        // tải dữ liệu từ socket xuống hệ thống tệp
	        File file = new File(fileDowUp.getPath());
	        FileInputStream inputStream = new FileInputStream(file);
	        OutputStream outputStream = dataTransferSocket.getOutputStream();
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	        while((bytesRead = inputStream.read(buffer)) != -1)
	        {
	        	outputStream.write(buffer, 0, bytesRead);
	            totalByteRead +=bytesRead;
				percentCompleted = (int) (totalByteRead*100/fileDowUp.getSize());
				GuiMain.setValueProcessUp(percentCompleted);
	        }
	        if(fileDowUp.getSize() == 0){
	        	GuiMain.setTienTrinhDow(100);
	        }
	        outputStream.flush();
	        outputStream.close();
	        inputStream.close();

	        dataTransferSocket.close();
	        // Get the completion reply from the server.
	        ftpResponse = clientPI.serverReply();
	        System.out.println(ftpResponse.getResponseLine());
	        GuiMain.setStatusConnect(ftpResponse.getResponseLine());
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
		
}
