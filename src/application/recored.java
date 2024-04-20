package application;

public class recored {
	String  code;
	char value;
	int freq;
	byte length;

	public recored() {
		super();
		code = "#";
		value ='#';
	}

	public recored(String code, char value, int freq, byte length) {
		super();
		this.code = code;
		this.value = value;
		this.freq = freq;
		this.length = length;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public byte getLength() {
		return length;
	}

	public void setLength(byte length) {
		this.length = length;
	}

}
