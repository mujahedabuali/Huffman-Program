package application;

public class HNode {
	HNode left;
	HNode right;
	int freq;
	byte charc;
	byte length;
	boolean valid;
	String hufCode;

	public HNode() {
		super();
	}

	public HNode(int freq, byte charc) {
		super();
		this.freq = freq;
		this.charc = charc;
	}

	public HNode(HNode left, HNode right, int charc) {
		super();
		this.left = left;
		this.right = right;
		this.freq = charc;
	}

	public HNode getLeft() {
		return left;
	}

	public void setLeft(HNode left) {
		this.left = left;
	}

	public HNode getRight() {
		return right;
	}

	public void setRight(HNode right) {
		this.right = right;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int charc) {
		this.freq = charc;
	}

	public byte getcharc() {
		return charc;
	}

	public void setcharc(byte charc) {
		this.charc = charc;
	}

	public String gethufCode() {
		return hufCode;
	}

	public void sethufCode(String hufCode) {
		this.hufCode = hufCode;
	}

	public byte getLength() {
		return length;
	}

	public void setLength(byte length) {
		this.length = length;
	}

	//set Huffman hufCode
	public void huffCode(String hufCode, byte length) {

		length++;
		if (this.left != null) {
			this.left.sethufCode(hufCode + "0");
			this.left.setLength(length);
			this.left.huffCode(hufCode + "0", length);
		}

		if (this.right != null) {
			this.right.sethufCode(hufCode + "1");
			this.right.setLength(length);
			this.right.huffCode(hufCode + "1", length);
		}

	}
	public void recored(recored[] arr) {

		if (this.left != null) {
			if (this.left.valid) {
				if (this.left.charc < 0) 
					arr[this.left.charc + 256] = new recored(left.hufCode, (char) (left.charc + 256),left.freq, left.length);
				 else
					arr[this.left.charc] = new recored(left.hufCode, (char) left.charc, left.freq,left.length);
			}
			this.left.recored(arr);
		}
		if (this.right != null) {
			if (this.right.valid) {
				if (this.right.charc < 0)
					arr[this.right.charc + 256] = new recored(right.hufCode, (char) (right.charc + 256),right.freq, right.length);
				else
					arr[this.right.charc] = new recored(right.hufCode, (char) right.charc, right.freq,
							right.length);
			}
			this.right.recored(arr);

		}

	}
	public String postOrder_F() {
		String str = "";
		if (this.left == null && this.right == null) {
			str += "1";
			String a = String.format("%8s", Integer.toBinaryString((this.charc + 256) % 256)).replace(' ', '0');
			str += a;
		} else {
			if (this.left != null) {
				str += this.left.postOrder_F();
			}
			if (this.right != null) {
				str += this.right.postOrder_F();
			}
			str += "0";
		}
		return str;
	}
	
	public String postOrder() {

		String s = "";
		if (this.left == null && this.right == null) {
			s += "1";
			s += (char) this.charc;
		} else {
			if (this.left != null) s += this.left.postOrder();
			if (this.right != null) s += this.right.postOrder();
			
			s += "0";
		}
		return s;
	}
}
