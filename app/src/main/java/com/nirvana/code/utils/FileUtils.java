package com.nirvana.code.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build;
import android.os.Environment;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class FileUtils {
	public static final int PRIORITY_SD = 0x0001;
	public static final int PRIORITY_APP = 0x0002;

	public final static String ImageFileSuffix = ".png";

	private Context context = null;

	public FileUtils(Context context) {
		this.context = context;
	}

	// static methods
	public static boolean isSDMounted() {
		String state = Environment.getExternalStorageState();
		Environment.getExternalStorageDirectory();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}

		return false;
	}

	/**
	 * 创建目录
	 * @param pathFile
	 * @return
     */
	public static boolean mkdirs(File pathFile) {
		if (!pathFile.isDirectory()) {
			pathFile.delete();
			return pathFile.mkdirs();
		}
		return false;
	}

	public static File getSDRootDirectory() {
		if (isSDMounted()) {
			File file = Environment.getExternalStorageDirectory();
			file.mkdir();

			return file;
		}

		return null;
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	public static File getSDPublicDirectory(String type) {
		if (isSDMounted()) {
			File file = Environment.getExternalStoragePublicDirectory(type);
			file.mkdir();

			return file;
		}

		return null;
	}

	public File getCacheDir() {
		return getCacheDir(PRIORITY_SD);
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	public File getCacheDir(int priority) {
		File file = null;
		if (priority == PRIORITY_APP || !isSDMounted()) {
			file = context.getCacheDir();
		}

		file = context.getExternalCacheDir();

		if (file != null) {
			file.mkdir();
		}

		return file;
	}

	public File getFilesDir() {
		return getFilesDir(PRIORITY_SD);
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	public File getFilesDir(int priority) {
		File file = null;
		if (priority == PRIORITY_APP || !isSDMounted()) {
			file = context.getFilesDir();
		}

		file = context.getExternalFilesDir(null);

		if (file != null) {
			file.mkdir();
		}

		return file;
	}

	public File getDir(String name) {
		return getDir(name, PRIORITY_SD);
	}

	@TargetApi(Build.VERSION_CODES.FROYO)
	public File getDir(String name, int priority) {
		File file = null;
		if (priority == PRIORITY_APP || !isSDMounted()) {
			file = context.getDir(name, Context.MODE_PRIVATE);
		}

		file = context.getExternalFilesDir("app_" + name);

		if (file != null) {
			file.mkdirs();
		}
		
		return file;
	}

	public static File[] listFiles(File dir, final String regular) {
		if (dir != null && dir.exists() && dir.isDirectory()) {
			FilenameFilter filter = null;
			if (regular != null) {
				filter = new FilenameFilter() {
					@Override
					public boolean accept(File dir, String filename) {
						// TODO Auto-generated method stub
						if (filename != null && filename.contains(regular)) {
							return true;
						}

						return false;
					}
				};
			}

			return dir.listFiles(filter);
		}

		return null;
	}

	public static boolean copyFile(File sourceFile, File targetFile) {
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			input = new FileInputStream(sourceFile);
			output = new FileOutputStream(targetFile);

			byte[] tmp = new byte[1024 * 2];
			int len;
			while ((len = input.read(tmp)) != -1) {
				output.write(tmp, 0, len);
			}
			output.flush();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (input != null) {
					input.close();
					input = null;
				}

				if (output != null) {
					output.close();
					output = null;
				}
			} catch (IOException ioException) {
				// TODO: handle exception
			}
		}

		return false;
	}


	
	public static boolean saveBitmap(Bitmap bitmap, File file, boolean recycle) {
		try {
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(file));
			bitmap.compress(CompressFormat.PNG, 100, stream);
			stream.flush();
			stream.close();
			
			if(recycle) {
				bitmap.recycle();
				bitmap = null;
	
				System.gc();
			}
			
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	

	
	public static boolean isValidFileName(String fileName) {
		if (fileName == null || fileName.length() > 255) 
			return false; 
		else 
			return fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$"); 
	}



	/**
	 * Reads Assets file to bytes
	 *
	 * @param context
	 * @param filename
	 * @return
	 */
	public static byte[] getAssert(Context context, String filename) {
		InputStream is = null;
		try {
			AssetManager am = context.getAssets();
			is = am.open(filename);
			return readInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Reads input stream to bytes
	 *
	 * @param in
	 * @return
	 * @author kris.zhang
	 * @date 2016-09-29 09:00:53
	 */
	public static byte[] readInputStream(InputStream in) {
		byte[] buffer = null;
		try {
			int length = in.available();
			buffer = new byte[length];
			in.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
}
