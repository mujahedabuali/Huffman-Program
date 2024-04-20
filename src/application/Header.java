package application;

public class Header {
	
	String exten;
	byte extenSize;
	int headerSize;
	int totalSize;
	int compSize;
	
	
	
	public Header() {
		super();
	}
	public Header(String extension, byte extensionSize, int headerSize) {
		super();
		this.exten = extension;
		this.extenSize = extensionSize;
		this.headerSize = headerSize;
	}
	public String getExtension() {
		return exten;
	}
	public void setExtension(String extension) {
		this.exten = extension;
	}
	public byte getExtensionSize() {
		return extenSize;
	}
	public void setExtensionSize(byte extensionSize) {
		this.extenSize = extensionSize;
	}
	public int getHeaderSize() {
		return headerSize;
	}
	public void setHeaderSize(int headerSize) {
		this.headerSize = headerSize;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public int getCompSize() {
		return compSize;
	}
	public void setCompSize(int compSize) {
		this.compSize = compSize;
	}

	
	
}
