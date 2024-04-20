package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Compress {
	
	//GET FREQ AND MAKE HEAP AND GET HUFFMAN CODE
	public static void doThis(Stage primaryStage) {
		
		//get freq
		int freq[] = new int[256];
		try {
			if (Pages.file == null) throw new IOException("its Empty.");
			RandomAccessFile f = new RandomAccessFile(Pages.file, "r");
			FileChannel ch = f.getChannel();
			ByteBuffer bf = ByteBuffer.allocate(16);

			try {
				while (ch.read(bf) > 0) {
					bf.flip();//beginning of data
					for (int i = 0; i < bf.limit(); i++) {
						byte read = bf.get();

						if (read < 0) freq[read + 256]++;// due to negative representation
						else freq[read]++;
					}
					bf.clear();
				}		
			} catch (IOException x) {
				x.printStackTrace();
			}
		} catch (FileNotFoundException x) {
			x.printStackTrace();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//count number of freq
		int n = 0;
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] != 0) 
				n++; }
		HNode arrayNode[] = new HNode[n + 1];
		
		//make heap node
		int xx = 1;
		for (int i = 0; i < freq.length; i++) {
			if (freq[i] != 0) {
				if (i >= 128)
					arrayNode[xx] = new HNode(freq[i], (byte) (i - 256));//represents a negative byte value.
				else
					arrayNode[xx] = new HNode(freq[i], (byte) i);
				arrayNode[xx].valid = true;
				xx++;}}
	
		//make tree
		Heap heap = new Heap(arrayNode.length, arrayNode);
		for (int i = 1; i < arrayNode.length - 1; i++) {
			HNode root = new HNode();
			HNode x = heap.deleteMin();
			HNode y = heap.deleteMin();
			root.right = y;
			root.left = x;
			root.freq = x.freq + y.freq;
			heap.insert(root);
		}
		
		//make huffman code
		if (n != 0) {
			heap.array[1].huffCode("", (byte) 0);//set Huffman code 
			
			recored arr[] = new recored[256];
			HNode root = heap.array[1];
			root.recored(arr);//get Record for information of each node
			
			//get num of leaves
			int num_lev = 0;
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] == null) 
					arr[i] = new recored();
				 else 
					 num_lev++;
			}		
			Header header = Compress.write(num_lev, root, arr);//create header
			
			String postOrder=root.postOrder();//get postOrder for GUI
			primaryStage.setScene(Pages.compress_Page(primaryStage, arr,  header,postOrder));
			
		} else {
			Alert err = new Alert(AlertType.ERROR);
			err.setTitle("Error");
			err.setHeaderText("File is empty");
			err.showAndWait();
		}
	}

	//write Header on file
	public static Header write(int num_lev, HNode root, recored arr[]) {

		String Exten = Helper.FileExten(Pages.file);
	
		int leng = num_lev-1;
		int size = (int) Pages.file.length();
		int h_size = (int) ((double) Math.ceil((leng + num_lev) / 8.0)) + num_lev;
		
		Header header = new Header(Helper.FileExten(Pages.file), (byte) Exten.length(),h_size);
		header.setTotalSize(size);

		File compreFile = new File(Helper.FileName(Pages.file) + ".huf");
		 
		try {
			compreFile.createNewFile();
		} catch (IOException x) {
			x.printStackTrace();
		}
		try {	
			String s = root.postOrder_F();
			s = postOrder_byte(s);
			
			fileWrite writer = new fileWrite(compreFile);
			writer.write(header, s);
			Helper.readToEncode(writer, arr);
			writer.clean();

		} catch (Exception x) {
			x.printStackTrace();
		}
		header.setCompSize((int) compreFile.length());
		return header ;
	}
	//set complete byte
	public static String postOrder_byte(String s) {
		while (s.length() % 8 != 0) 
			s += "0";
		return s;

	}

}
