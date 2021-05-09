package ftp;
import javax.swing.ImageIcon;

class FileHost {
	private String fileName;
	private String fileSize;
	private String date;
	private ImageIcon icon;
	private String SLTM;
	public FileHost(ImageIcon icon,String name,String SLTM,String size,String date) {
		// TODO Auto-generated constructor stub
		this.icon = icon;
		this.fileName = name;
		this.SLTM =SLTM;
		this.fileSize = size;
		this.date =date;
	}
	public String getSLTM() {
		return SLTM;
	}
	public void setSLTM(String sLTM) {
		SLTM = sLTM;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public String getFileName() {
		return fileName;
	}
	public String getFileSize(){
		return fileSize;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
	}
	public void setFileSize(String fileSize){
		this.fileSize = fileSize;
	}
}
