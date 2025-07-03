package app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * @note experimental utility methods
 * @author Dennis Thomas
 */

public class FileUtil {

    public static void writeBytesToFile(byte[] contentBytes, String location) {

        try {

            File afile = new File(location);
            OutputStream os = new FileOutputStream(afile);
            os.write(contentBytes);
            os.close();

        } catch (IOException e) {
            System.out.print(e.toString());
        }

    }

    public static void writeContentToFile(String content, String location) {

        try {

            File afile = new File(location);
            OutputStream os = new FileOutputStream(afile);
            FileOutputStream fos = new FileOutputStream(afile);
            os.write(content.getBytes());
            os.close();

        } catch (IOException e) {
            System.out.print(e.toString());
        }

    }

    public static void writeContentToFile(String content, String location, Charset charset) {
        try {

            File afile = new File(location);
            OutputStream os = new FileOutputStream(afile);
            os.write(content.getBytes(charset));
            os.close();

        } catch (IOException e) {
            System.out.print(e.toString());
        }

    }

    public static String convertFileContentToString(String location) {

        String content = null;
        try {

            File afile = new File(location);
            InputStream is = new FileInputStream(afile);
            byte[] abs = is.readAllBytes();
            content = new String(abs,Charset.forName("UTF8"));
            is.close();

        } catch (IOException e) {
            System.out.print(e.toString());
        }

        return content;

    }

    public static String convertFileContentToString(String location, Charset charset) {

        String content = null;
        try {

            File afile = new File(location);
            InputStream is = new FileInputStream(afile);
            byte[] abs = is.readAllBytes(); 
            //byte[] abs = is.readNBytes(is.available()); 
            content = new String(abs,charset);
            is.close();

        } catch (IOException e) {
            System.out.print(e.toString());
        }

        return content;

    }

}
