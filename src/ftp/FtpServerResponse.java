package ftp;
public class FtpServerResponse {
	private String responseCode;
	private String responseLine;
	public FtpServerResponse(String responseCode,String responseLine) {
		// TODO Auto-generated constructor stub
		this.responseCode = responseCode;
		this.responseLine = responseLine;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public void setResponseLine(String responseLine) {
		this.responseLine = responseLine;
	}
	public String getResponseLine() {
		return responseLine;
	}
	public String getResponseCode() {
		return responseCode;
	}
}
