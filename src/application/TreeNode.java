package application;

public class TreeNode {
	char value;
	String code;
	TreeNode right;
	TreeNode left;

	public TreeNode() {
		super();
	}

	public TreeNode(char value, String code) {
		super();
		this.value = value;
		this.code = code;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}
	// set huffman code
	public void hufCode(String code) {

		if (this.left != null) {
			this.left.setCode(code + "0");
			this.left.hufCode(code + "0");
		}

		if (this.right != null) {
			this.right.setCode(code + "1");
			this.right.hufCode(code + "1");
		}
	}
	
	//to check if the node is leaf
	public boolean isLeaf() {
		if (this.left == null && this.right == null) {
			return true;
		} else {
			return false;
		}
	}

}
