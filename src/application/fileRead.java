package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class fileRead {
	FileChannel ch;
	ByteBuffer b;
	File f;
	FileInputStream fStrem;

	public fileRead(File file) {
		super();
		this.f = file;

		try {
			fStrem = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		ch = fStrem.getChannel();
		b = ByteBuffer.allocate(1024);
	}

		public String readHeader(Header header) throws IOException {

			String s = "";
			try {	
				// reading the size of the extension
				b = ByteBuffer.allocate(1);
				ch.read(b);
				b.flip();
				byte extensionSize = b.get();
				byte[] ar = b.array();
				b.clear();
	
				// reading the extension
				b = ByteBuffer.allocate(extensionSize);
				ch.read(b);
				b.flip();
				ar = b.array();
				String exten = "";
				for (int i = 0; i < extensionSize; i++) {
					exten += (char) ar[i];
				}
				b.clear();
	
				// reading the size of the file
				b = ByteBuffer.allocate(4);
				ch.read(b);
				b.flip();
				int totalSize = b.getInt();
				b.clear();
	
				// reading the size of the header
				b = ByteBuffer.allocate(4);
				ch.read(b);
				b.flip();
				int headerSize = b.getInt();
				b.clear();
	
				header.setExtensionSize(extensionSize);
				header.setHeaderSize(headerSize);
				header.setTotalSize(totalSize);
				header.setExtension(exten);
	
				b = ByteBuffer.allocate(headerSize);
				ch.read(b);
				b.flip();
	
				byte TreeInBytes[] = b.array();
				s = BytesToBinaryString(TreeInBytes);
				b.clear();
			
			}catch(Exception e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Error File");
				alert.showAndWait();
				return null;
			}
			return s;
		}
	
	//from array of bytes to binary String 
	public static String BytesToBinaryString(byte[] ar) {
		String s = "";
		for (int i = 0; i < ar.length; i++) {
			//The  0xFF is used to ensure that only the least significant 8 bits are considered.
			//Ensures that the binary string is at least 8 characters
			String t=String.format("%8s", Integer.toBinaryString(ar[i] & 0xFF)).replace(' ', '0');
			
			t = padding(t);
			s+=t;	
		}
		return s;
	}
	
	//left padding  add zeros
	static String padding(String txt) {
		while (txt.length() < 8)
			txt = "0" + txt;
		return txt;
	}
	
	//convert the chars to the original rep of bits
	public void compreToDeCompress(int size, TreeNode rootNode, File f) throws IOException {

			b=ByteBuffer.allocate(512);
			FileOutputStream fout = null;
			try {
				fout = new FileOutputStream(f);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			FileChannel outCh = fout.getChannel();
			ByteBuffer outBuff = ByteBuffer.allocate(512);

			b.clear();
			ch.read(b);
			b.flip();
			
			String s=toBitString(b.get());
			TreeNode tempNode = rootNode;

			boolean flag=false;
			int counter = 0;
			int i = 0;
			while (counter < size) {
				tempNode = rootNode;
				
				while (!tempNode.isLeaf()) {
					if (i == s.length()) {
	                    if (!b.hasRemaining()) {
	                    	b.clear();
	                        if(ch.read(b)<1) flag=true;
	                        b.flip();   
	                    }
	                    	if(flag) break;
	                    	s = toBitString(b.get());   
	                    i = 0;
	                }
					if (s.charAt(i) == '0')tempNode = tempNode.getLeft();
					else tempNode = tempNode.getRight();
					
					i++;
				}
				
				s = s.substring(i);
				i = 0;
				if (tempNode.value >= 128)outBuff.put((byte) (tempNode.value - 256));
				else  outBuff.put((byte) (tempNode.value));
					
				counter++;
				if (counter%512==0) {
					outBuff.flip();
					outCh.write(outBuff);
					outBuff.clear();
				}
			}
			
			if(outBuff.hasRemaining()) {
				outBuff.flip();
				outCh.write(outBuff);
			}
			outCh.close();
			ch.close();		
		}
		
		//converts byte to bit string 
		public static String toBitString(byte b) {
			final byte by = b;
		    final char[] bits = new char[8];
	        int m = 00000001;
	        
	        for(int j = 7; j >= 0; j--) {
	            final int bitval = by & m; // check if last bit 0 or 1
	            if(bitval == 0) {
	                bits[00000 + j] = '0';
	            } else {
	                bits[00000 + j] = '1';
	            }
	            m <<= 1;
	        }
	    
	        return String.valueOf(bits);
		  }
}
