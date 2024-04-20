package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Helper {

	//get extension
	public static String FileExten(File file) {
		String fName = file.getName();

		int i = fName.lastIndexOf('.');
		String extn = "";
		if (i > 0) {
			extn = fName.substring(i + 1);
			return extn;

		} else return null;
	}

	//get file name
	public static String FileName(File file) {
		String fName = file.getName();

		int i = fName.lastIndexOf('.');
		String name = "";
		if (i > 0) {
			name = fName.substring(0, i);
			return name;
		} else 	return null;		
	}

	// put the new repr of the chars in the file
	public static void readToEncode(fileWrite writer, recored arr[]) {
		if (Pages.file == null) return;
		
		RandomAccessFile aFile;
		try {
			aFile = new RandomAccessFile(Pages.file, "r");
			FileChannel inChannel = aFile.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(64);

			try {
				while (inChannel.read(buffer) > 0) {
					buffer.flip();

					for (int i = 0; i < buffer.limit(); i++) {
						byte x = buffer.get();

						try {
							if (x < 0) writer.CompressedData(arr[x + 256].code);
							else writer.CompressedData(arr[x].code);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					buffer.clear();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				inChannel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				aFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	

}
