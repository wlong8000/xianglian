package com.wl.lianba.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class ImageViewAsyncTask extends AsyncTask<String, Integer, Bitmap> {

	private ImageView imageView;
	private String imgName;
	private int pos;
	private Button button;
	private ImageView ivButton;
	private boolean isCache; // 是否缓存图片
	private boolean isWemedia = false;// 自媒体使用图片不是FIT_XY

	public ImageViewAsyncTask(ImageView imageView, boolean isCache) {
		this.imageView = imageView;
		this.isCache = isCache;
	}

	public ImageViewAsyncTask(ImageView imageView, boolean isCache, boolean isWemedia) {
		this.imageView = imageView;
		this.isCache = isCache;
		this.isWemedia = isWemedia;
	}

	public ImageViewAsyncTask(Button button, int pos, boolean isCache) {
		this.button = button;
		this.isCache = isCache;
		this.pos = pos;
	}

	public ImageViewAsyncTask(ImageView ivButton, int pos, boolean isCache) {
		this.ivButton = ivButton;
		this.isCache = isCache;
		this.pos = pos;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		//MyLog.ii("params[0] : " + params[0]);
		Bitmap bitmap = null;
		if (isCancelled() || (params[0] == null))
			return null;
		imgName = params[0].substring(params[0].lastIndexOf("/") + 1);
		//MyLog.ii("imgName: " + imgName);
		try {
			if (isCache) {
				bitmap = Tool.loadCacheBitmapToFile(imgName);
				if (bitmap != null) {
					return bitmap; // 读取缓存
				}
			}
			InputStream input = null;
			BufferedInputStream bis = null;
			ByteArrayOutputStream out = null;
			URL url = new URL(params[0]);
			input = (InputStream) url.getContent();
			bis = new BufferedInputStream(input, 1024 * 8);
			out = new ByteArrayOutputStream();
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = bis.read(buffer)) != -1) {
				out.write(buffer, 0, len);
				// super.onPostExecute(result);
			}
			out.close();
			bis.close();
			byte[] data = out.toByteArray();
			// MyLog.ii("doInBackground - > data : " + data.length);
			if (data.length < 12000000) {
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inPreferredConfig = Bitmap.Config.RGB_565;
				opt.inPurgeable = true;
				opt.inInputShareable = true;
				//opt.inSampleSize = 4;
				bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
			} else {
				bitmap = null;
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		if (isCache && (bitmap != null)) {
			try {
				Tool.saveCahceBitmapToFile(bitmap, imgName, Bitmap.CompressFormat.PNG);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bitmap;

	}

	@Override
	protected void onPostExecute(Bitmap result) {

		if (result != null) {
			// MyLog.ii("ImageView 图片加载成功 " + imageView);
			if (imageView != null) {
				if (!isWemedia) {
					imageView.setVisibility(View.VISIBLE);
					imageView.setScaleType(ImageView.ScaleType.FIT_XY);

				}
				imageView.setImageBitmap(result);
			}
			if (button != null) {
				@SuppressWarnings("deprecation")
				BitmapDrawable bd = new BitmapDrawable(result);
				bd.setBounds(0, 0, 110, 110);
				if (pos == 1) {
					button.setCompoundDrawables(bd, null, null, null);
				} else if (pos == 2) {
					button.setCompoundDrawables(null, bd, null, null);
				}
			}
			if (ivButton != null) {
				ivButton.setVisibility(View.VISIBLE);
				ivButton.setScaleType(ImageView.ScaleType.FIT_XY);
				ivButton.setImageBitmap(result);
			}
		}

		super.onPostExecute(result);
	}

}
