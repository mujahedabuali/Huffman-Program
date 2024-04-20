package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class fileWrite {
	
	File f;
	FileChannel ch;
	ByteBuffer b;
	String bit;
	int cntByte=0;
	FileOutputStream Fstrem ;
	
	public fileWrite(File file) {
		super();
		this.f = file;
		
		try {
			Fstrem=new FileOutputStream(file);
			} catch (FileNotFoundException e) {
			e.printStackTrace();
			}	
		
		ch=Fstrem.getChannel();
		b=ByteBuffer.allocate(1024);
		bit="";
	}
	
	public void write(Header h,String postOrder) {
	
		b.put(h.getExtensionSize());
		
		//make each char on ext to byte
		byte array[]=new byte[h.getExtension().length()];
		for (int i = 0; i < h.getExtension().length(); i++) 
			array[i]=(byte)h.getExtension().charAt(i);
		
		b.put(array);
		
		b.putInt(h.getTotalSize());
		b.putInt(h.getHeaderSize());
		
		String t = "";
		for (int i = 0; i < postOrder.length(); i++) {
			t += postOrder.charAt(i);
			if ((i + 1) % 8 == 0) {//complete 8 bits
				int tInt = Integer.parseInt(t, 2);// to binary
				b.put((byte)tInt);
				t = "";
			}
		}
		b.flip();
		try {
			ch.write(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		b.clear();
	}
	
	//this method is used to put the new represintation of the chars in the compressed file 
	public  void CompressedData(String code) {
		bit+=code;
		if(bit.length()>=8) {
			String temp= bit.substring(0, 8);
			bit=bit.substring(8);
			int x=Integer.parseInt(temp,2);
			
			byte by=(byte)x;
			b.put(by);
			cntByte++;
		}
		if(cntByte==8) {
			try {
				b.flip();
				ch.write(b);
				
				b.clear();
				cntByte=0;
				
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}	
	}
	//cleaning the remaining bits if found
	public void clean() {
		
		while(bit.length()>=8) {
			String temp= bit.substring(0, 8);
			bit=bit.substring(8);
			int x=Integer.parseInt(temp,2);
			byte by=(byte)x;
			b.put(by);
			b.flip();
			try {
				ch.write(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
			b.clear();
		}
		//right padding zeros if it was less than 8
		if(bit.length()>0) {
			bit=rightPad(bit);
			b.put((byte)Integer.parseInt(bit,2));
			b.flip();
			try {
				ch.write(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
			b.clear();		
		}
	}	
	//right padding
	String rightPad(String txt) {
		while (txt.length() < 8)
			txt = txt+ "0" ;
		return txt;
	}
}