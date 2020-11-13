package com.example.fastwork.utils.file;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.util.Log;
import com.example.fastwork.utils.log.Lg;

import java.io.*;
import java.util.Date;


public class FileUtil {
    public static final String TAG = "FileUtil";
    private static int len = 0;
    private static byte[] buffer = new byte[1024];

//
//    public static String getSDCardCachePath(String appName) {
//        StringBuffer sb = new StringBuffer(SystemUtil.getSDCardPath());
//        return sb.append("/").append(appName).append("/cache/").toString();
//    }


    public static void saveFileAsString(Context context, String filename, String content) {
        saveFileAsByte(context, filename, content == null ? new byte[0] : content.getBytes());
    }

    public static void saveFileAsByte(Context context, String filename, byte[] content) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(content);
        } catch (Exception e) {
            logException(filename, e);
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                logException(filename, e);
            }
        }
    }

    public static String readFileAsString(Context context, String filename) {
        byte[] data = readFileAsByte(context, filename);
        return new String(data);
    }

    public static byte[] readFileAsByte(Context context, String filename) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;
        int len = 0;
        try {
            fis = context.openFileInput(filename);
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            logException(filename, e);
        } finally {
            try {
                if (baos != null)
                    baos.close();
            } catch (IOException e) {
                logException(filename, e);
            }
        }

        return baos.toByteArray();
    }

    public static void logException(String filename, Exception e) {
        Log.e("FileUtil", "File operation failed, file name: " + filename, e);
    }


    public static long getFileSize(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.length();
        } else {
            return 0;
        }
    }

    public static boolean moveFile(String srcFile, String destPath) {
        // File (or directory) to be moved
        File file = new File(srcFile);
        // Destination directory
        File dir = new File(destPath);
        // Move file to new directory
        boolean moveFlag = file.renameTo(new File(dir, file.getName()));
        //boolean moveFlag = file.renameTo(new File(destPath));
        return moveFlag;
    }


    public static void saveBitmapInFile(String filePath, String fileName, Bitmap bitmap, Bitmap.CompressFormat imageType) {
        File cacheFolder = new File(filePath);
        if (!cacheFolder.exists()) {
            cacheFolder.mkdirs();
            creatNoMediaFile(filePath);
        }
        FileOutputStream fouts = null;
        try {
            File cacheFile = new File(filePath, fileName);
            cacheFile.createNewFile();
            fouts = new FileOutputStream(cacheFile);
            bitmap.compress(imageType, 100, fouts);
            fouts.flush();
            fouts.close();
        } catch (IOException e) {
            e.printStackTrace();
            Lg.d(TAG, "save bitmap file failed", e);
        }
    }

    public static void saveBitmapInFile(String filePath, String fileName, Bitmap bitmap) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        if (bitmap.getConfig() == Bitmap.Config.ARGB_8888) {
            format = Bitmap.CompressFormat.PNG;
        }
        saveBitmapInFile(filePath, fileName, bitmap, format);
    }


    public static boolean saveFileInSDCard(String fileName, String filePath, InputStream inputStream) {
        Log.d("FIleUtil", "<saveFileInSDCard> and filepath = " + filePath);
        if (!checkFileIsExits(filePath)) {
            createDir(filePath);
        }
        String tragetFilePath = filePath + fileName;
        return writeFile(tragetFilePath, inputStream);
    }


    public static boolean writeFile(String targetFile, InputStream inputStream) {
        boolean flag = true;

        try {
            File file = new File(targetFile);
            OutputStream myOutput = new FileOutputStream(file);
            BufferedInputStream myInput = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                myOutput.write(buffer, 0, length);
                Log.d("FileUtil", "file length = " + length);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (Exception e) {
            Log.e("FileUtil", "", e);
        }

        return flag;
    }


    public static FileInputStream readFile(String filePath) {

        if (!checkFileIsExits(filePath)) {
            return null;
        }
        File file = new File(filePath);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }


    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        if (filePath == null || filePath.length() == 0) return false;
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    public static void delete(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }

        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
                return;
            }

            for (int i = 0; i < childFiles.length; i++) {
                delete(childFiles[i]);
            }
            file.delete();
        }
    }


    public static void creatNoMediaFile(String path) {
        String fileName = path + ".nomedia";
        File file = new File(fileName);
        createNoMediaFile(file);
    }

    public static void createNoMediaFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean checkFileIsExits(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    
   /* public static String getSDPath(){
        boolean sdCardExist = MemoryUtil.externalMemoryAvailable();
        if   (sdCardExist)                              
        return	android.os.Environment.getExternalStorageDirectory().getAbsolutePath(); 
        return null;
    } */


    public static boolean createDir(String filePath) {
        File fileSaveDir = new File(filePath);
        if (!fileSaveDir.exists())
            return fileSaveDir.mkdirs();
        return true;
    }

    public static boolean createFile(String filePath) {
        if (createDir(getFilePath(filePath))) {
            //创建文件
            File file = new File(filePath);
            if (file.exists()) {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean copyAssetsFile(Context context, String fileName,
                                         String dataPath) {

        InputStream inputStream = null;
        OutputStream myOutput = null;
        BufferedInputStream myInput = null;
        boolean result = false;
        String targetFile = dataPath + fileName;
        try {
            inputStream = context.getAssets().open(fileName);
            myOutput = new FileOutputStream(targetFile);
            myInput = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) != -1) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                if (myOutput != null) {
                    myOutput.close();
                }
                if (myInput != null) {
                    myInput.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return result;
    }

    public static boolean copyAndDeleteFromFile(String fromFileName, String toFileName) {
        if (copyCheck(fromFileName, toFileName)) return false;
        return new File(fromFileName).renameTo(new File(toFileName));
    }

    public static boolean copy(String fromFileName, String toFileName) {
        if (copyCheck(fromFileName, toFileName)) return false;
        InputStream isFrom = null;
        FileOutputStream osTo = null;
        try {
            isFrom = new FileInputStream(fromFileName);
            osTo = new FileOutputStream(toFileName);
            byte bs[] = new byte[1024 * 8];
            int c;

            while ((c = isFrom.read(bs)) != -1) {
                osTo.write(bs, 0, c);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (isFrom != null) {
                try {
                    isFrom.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osTo != null) {
                try {
                    osTo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private static boolean copyCheck(String fromFileName, String toFileName) {
        if (!new File(fromFileName).exists()) return false;
        if (!createFile(toFileName)) return false;
        return true;
    }

//    /**
//     * Save image to the SD card
//     *
//     * @param photoBitmap
//     * @param photoName
//     * @param path
//     */
//    public static void savePhotoToSDCard(Bitmap photoBitmap, String path, String photoName) {
//        if (SystemUtil.checkSDCardAvailable()) {
//            File dir = new File(path);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//
//            File photoFile = new File(path, photoName + ".png");
//            FileOutputStream fileOutputStream = null;
//            try {
//                fileOutputStream = new FileOutputStream(photoFile);
//                if (photoBitmap != null) {
//                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
//                        fileOutputStream.flush();
////						fileOutputStream.close();
//                    }
//                }
//            } catch (FileNotFoundException e) {
//                photoFile.delete();
//                e.printStackTrace();
//            } catch (IOException e) {
//                photoFile.delete();
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (fileOutputStream != null)
//                        fileOutputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

    public static String getResourceSmallFile(int resId, Context context) {
        return getResourceSmallFile(resId, context, "UTF-8");
    }

//    public static String saveInputStreamCache(InputStream inputStream, String url, String appName) {
//        if (inputStream == null) {
//
//            return "";
//        }
//        String name = getFromURLFileNameNotSuffix(getFromURLToFileName(url));
//
//        String address = getSDCardCachePath(appName) + "/" + name;
//
//        //writeFile(address, inputStream , false);
//
//        return name;
//    }

    public static void saveInputStreamBitmap(InputStream inStream, String path) throws IOException {

        //创建文件
        createFile(path);

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存

        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = outStream.toByteArray();
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        File imageFile = new File(path);
        //创建输出流
        FileOutputStream fileOutStream = new FileOutputStream(imageFile);
        //写入数据
        fileOutStream.write(data);

    }

    public static String getResourceSmallFile(int resId, Context context, String encoding) {

        String sb = null;
        try {
            sb = new String(getResourceSmallFilebytes(resId, context), encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return sb;
        }
        return sb;
    }

    public static byte[] getResourceSmallFilebytes(int resId, Context context) {
        if (resId <= 0 || context == null) return new byte[0];

        InputStream out = context.getResources().openRawResource(resId);
        if (out == null) return new byte[0];
        byte[] bytes = null;
        try {
            int length = out.available();
            bytes = new byte[length];

            int result = out.read(bytes);

            if (result == -1) return new byte[0];


        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }

    /**
     * Get images from SD card by path and the name of image
     *
     * @param photoName
     * @return
     */

    public static Bitmap getPhotoFromSDCard(String path, String photoName) {
        return getPhoto(path + "/" + photoName + ".png");
    }

    public static Bitmap getPhoto(String filePath) {
        Bitmap photoBitmap = BitmapFactory.decodeFile(filePath);
        if (photoBitmap == null) {
            return null;
        } else {
            return photoBitmap;
        }
    }

    public static String getFilePath(String fileName) {
        int a = getFromUrlToFileNamePostion(fileName);
        if (a == -1) return fileName;
        return fileName.substring(0, a + 1);
    }

//    public static Uri getFileUri(Context context, File file) {
//        Uri uri = new Uri.Builder().build();
//        try {
//            if (context == null || file == null || !file.exists())
//                return uri;
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
//            } else {
//                uri = Uri.fromFile(file);
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        return uri;
//    }

    //从网站里面解析出对应的
    public static String getFromURLToFileName(String url) {

        int a = getFromUrlToFileNamePostion(url);
        if (a == -1) return new Date().getSeconds() + "";

        return url.substring(a + 1, url.length());

    }

    private static int getFromUrlToFileNamePostion(String url) {
        if (url == null || url.trim().length() == 0) return -1;

        int a = url.lastIndexOf('\\');
        int b = url.lastIndexOf('/');

        return a > b ? a : b;
    }

    //获取没有后缀名的
    private static String getFromURLFileNameNotSuffixs(String name) {
        if (name == null || name.trim().length() == 0) return "" + new Date().getSeconds();
        int i = name.lastIndexOf('.');
        return i < 0 ? name : name.substring(0, i);
    }

    public static String getFromURLFileNameNotSuffix(String url) {
        return getFromURLFileNameNotSuffixs(getFromURLToFileName(url));
    }

    public static String getFromRaw(Context context, int assestid) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().openRawResource(assestid));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getFromAssets(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void saveFileToSD(String pathName, String fileName, String value) {

        try {

            File path = new File(pathName);
            File file = new File(pathName + fileName);
            if (!path.exists()) {
                Log.d("TestFile", "Create the path:" + pathName);
                path.mkdir();
            }
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + fileName);
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            byte[] buf = value.getBytes();
            stream.write(buf);
            stream.close();

        } catch (Exception e) {
            Log.e("TestFile", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }


    public static boolean saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        return true;
    }


    public static void writeStream(InputStream inputStream, FileOutputStream outputStream) {

    }
//
//    /**
//     * 保存对象
//     *
//     * @param object
//     * @param file
//     * @throws IOException
//     */
//    public static boolean saveObject(Serializable object, String file) {
//        file = MD5Util.hexdigest(file);
//        FileOutputStream fos = null;
//        ObjectOutputStream oos = null;
//        try {
//            File data = MakaApplicationLike.getContext().getFileStreamPath(file);
//            if (data != null && data.exists()) {
//                data.delete();
//            }
//            fos = MakaApplicationLike.getContext().openFileOutput(file, SampleApplication.MODE_PRIVATE);
//            oos = new ObjectOutputStream(fos);
//            oos.writeObject(object);
//            oos.flush();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            IOUtils.close(oos);
//            IOUtils.close(fos);
//        }
//    }
//
//    /**
//     * 读取对象
//     *
//     * @param file
//     * @return
//     * @throws IOException
//     */
//    public static Serializable readObject(String file) {
//        file = MD5Util.hexdigest(file);
//        File path = MakaApplicationLike.getContext().getFileStreamPath(file);
//        if (path == null || !path.exists())
//            return null;
//        FileInputStream fis = null;
//        ObjectInputStream ois = null;
//        try {
//            fis = MakaApplicationLike.getContext().openFileInput(file);
//            ois = new ObjectInputStream(fis);
//            return (Serializable) ois.readObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 反序列化失败 - 删除缓存文件
//            if (e instanceof InvalidClassException) {
//                File data = MakaApplicationLike.getContext().getFileStreamPath(file);
//                data.delete();
//            }
//        } finally {
//            IOUtils.close(ois);
//            IOUtils.close(fis);
//        }
//        return null;
//    }

    /**
     * 计算磁盘缓存大小
     *
     * @param dir 缓存目录
     * @return
     */
    public static long calculateDiskCacheSize(File dir) {
        long size = 10485760L;//10M

        try {
            StatFs ignored = new StatFs(dir.getAbsolutePath());
            long available = (long) ignored.getBlockCount() * (long) ignored.getBlockSize();
            size = available / 10L;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return Math.max(Math.min(size, 104857600L), 20971520L);//10M~200M
    }

    /**
     * 计算内存缓存大小
     *
     * @param context
     * @return
     */
    public static int calculateMemoryCacheSize(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        boolean largeHeap = (context.getApplicationInfo().flags & ApplicationInfo.FLAG_LARGE_HEAP) != 0;
        int memoryClass = am.getMemoryClass();
        if (largeHeap && Build.VERSION.SDK_INT >= 11) {
            memoryClass = am.getLargeMemoryClass() / 2;
        }

        return 1048576 * memoryClass / 15;
    }
}
