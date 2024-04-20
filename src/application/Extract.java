package application;

import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import javafx.stage.Stage;

public class Extract {
	
	public static void doThis(File compreFile,Stage primaryStage) {

		try {
			Header header = new Header();
			fileRead r=new fileRead(compreFile);
			
			String hed=r.readHeader(header);//read header
			if(hed==null) return;

			File f = new File(Helper.FileName(compreFile)+"."+header.getExtension());

			try {
				f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			TreeNode root = buildtree(hed);//make a tree
			
			root.hufCode("");//make huffman code
			
			r.compreToDeCompress(header.totalSize, root, f);//return to original data 
			
			Desktop.getDesktop().open(f);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//to rebuild the tree
	public static TreeNode buildtree(String s) {
		Stack stack = new Stack();

		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '1') {//char is 1,push the next char on the stack 
				char x = (char) Integer.parseInt(s.substring(i + 1, i + 9), 2);
				TreeNode node = new TreeNode();
				node.setValue(x);
				stack.push(node);
				i += 8;
			} else {
					if (stack.cnt == 1) return (TreeNode) stack.pop(); // char is 0,the stack has no more 1 ,it is root
					else {//char is 0 and stack has more then 1, pop two and make the first one the right child and the second one is the left child of a new node
						TreeNode node = new TreeNode();
						TreeNode right = (TreeNode) stack.pop();
						TreeNode left = (TreeNode) stack.pop();
						node.setRight(right);
						node.setLeft(left);
						stack.push(node);
				}
			}
		}
		return null;
	}
}