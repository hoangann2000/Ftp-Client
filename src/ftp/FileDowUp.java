package ftp;
public class FileDowUp {
	private String URL;
	private String path;
	private long size;
	public FileDowUp(String URL,String path, long size) {
		// TODO Auto-generated constructor stub
		this.URL = URL;
		this.path =path;
		this.size =size;
	}
	public String getPath() {
		return path;
	}
	public long getSize() {
		return size;
	}
	public String getURL() {
		return URL;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
}
