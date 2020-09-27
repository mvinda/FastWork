package com.example.fastwork.utils.log;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by asia on 16/8/11.
 */
public class IOUtils {

    private static final String UTF_8 = "UTF-8";

    /**
     * Writes the given string to a {@link File}.
     *
     * @param data The data to be written to the File.
     * @param file The File to write to.
     * @throws IOException
     */
    public static void writeToFile(String data, File file) throws IOException {
        writeToFile(data.getBytes(UTF_8), file);
    }

    /**
     * Write the given bytes to a {@link File}.
     *
     * @param data The bytes to be written to the File.
     * @param file The {@link File} to be used for writing the data.
     * @throws IOException
     */
    public static void writeToFile(byte[] data, File file) throws IOException {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(data);
            os.flush();
            // Perform an fsync on the FileOutputStream.
            os.getFD().sync();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    /**
     * Write the given content to an {@link OutputStream}
     * <p/>
     * Note: This method closes the given OutputStream.
     *
     * @param content The String content to write to the OutputStream.
     * @param os      The OutputStream to which the content should be written.
     * @throws IOException
     */
    public static void writeToStream(String content, OutputStream os) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(os, UTF_8));
            writer.write(content);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Reads a {@link File} as a String
     *
     * @param file The file to be read in.
     * @return Returns the contents of the File as a String.
     * @throws IOException
     */
    public static String readFileAsString(File file) throws IOException {
        return readAsString(new FileInputStream(file));
    }

    /**
     * Reads an {@link InputStream} into a String using the UTF-8 encoding.
     * Note that this method closes the InputStream passed to it.
     *
     * @param is The InputStream to be read.
     * @return The contents of the InputStream as a String.
     * @throws IOException
     */
    public static String readAsString(InputStream is) throws IOException {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            reader = new BufferedReader(new InputStreamReader(is, UTF_8));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return sb.toString();
    }

    public static void close(Closeable object) {
        if (object == null) return;
        try {
            object.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOUtils", "error while close", e);
        }
    }

    public static boolean createNoMediaFile(File imageFolder) {
        if (!imageFolder.exists()
                && !imageFolder.mkdirs()) {
            return false;
        }
        try {
            final File file = new File(imageFolder, ".nomedia");
            return file.exists() || file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean fileExists(String path) {
        return path != null && new File(path).exists();
    }

    public static boolean deleteDir(File dir) {
        deleteFile(dir);
        return true;

//        if (dir.isDirectory()) {
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    return false;
//                }
//            }
//        }
//        deleteFile(dir);
//        // The directory is now empty so now it can be smoked
//        return dir.delete();
    }

    public static void deleteFile(File file) {
        if (file.isFile()) {
            deleteFileSafely(file);
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                deleteFileSafely(file);
                return;
            }
            for (File childFile : childFiles) {
                deleteFile(childFile);
            }
            deleteFileSafely(file);
        }
    }


    /**
     * 安全删除文件.
     *
     * @param file
     * @return
     */
    private static boolean deleteFileSafely(File file) {
        if (file != null) {
            String tmpPath = file.getParent() + File.separator + System.currentTimeMillis();
            File tmp = new File(tmpPath);
            file.renameTo(tmp);
            return tmp.delete();
        }
        return false;
    }

    /**
     * 删除文件夹和子文件夹下的文件
     *
     * @param dir
     * @return
     */
    public static boolean deleteFiles(File dir) {

        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFiles(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return true;
        }

        return dir.delete();
    }

    public static void writeStream(InputStream inputStream, FileOutputStream outputStream) throws IOException {
        byte[] buf = new byte[1024];
        int n;
        while ((n = inputStream.read(buf)) >= 0) {
            outputStream.write(buf, 0, n);
        }
    }

    public static void copy(File src, File dst) throws IOException {
        FileInputStream inputStream;
        FileOutputStream outputStream;

        inputStream = new FileInputStream(src);
        outputStream = new FileOutputStream(dst);
        writeStream(inputStream, outputStream);

    }

    public static long folderSize(File file) {
//        if (file == null || !file.exists()) return 0;
//        if (file.isFile())
//            return file.length();
//        final File[] children = file.listFiles();
//        long total = 0;
//        if (children != null)
//            for (final File child : children)
//                total += folderSize(child);
//        return total;
        return getFileSize(file);
    }

    private static long getFileSize(final File file) {
        if (file == null || !file.exists())
            return 0;
        if (!file.isDirectory())
            return file.length();
        final List<File> dirs = new LinkedList<>();
        dirs.add(file);
        long result = 0;
        while (!dirs.isEmpty()) {
            final File dir = dirs.remove(0);
            if (!dir.exists())
                continue;
            final File[] listFiles = dir.listFiles();
            if (listFiles == null || listFiles.length == 0)
                continue;
            for (final File child : listFiles) {
                result += child.length();
                if (child.isDirectory())
                    dirs.add(child);
            }
        }
        return result;
    }

}
