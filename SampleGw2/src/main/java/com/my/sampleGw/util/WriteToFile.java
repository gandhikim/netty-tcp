package com.my.sampleGw.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

	private File file;
	
	public WriteToFile(String fileName) {
		file = new File(fileName);
	}
	
	public void writeToFile(String stuff) {
        try {

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(stuff);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
