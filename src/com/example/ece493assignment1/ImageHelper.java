package com.example.ece493assignment1;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;


public class ImageHelper
{

	public static int[][] getPix(Bitmap bitmap)
	{
		int[][] r = new int[bitmap.getHeight()][bitmap.getWidth()];
		for(int i = 0; i < bitmap.getHeight();i++)
		{
			for(int j = 0; j < bitmap.getWidth();j++)
			{
				int p = bitmap.getPixel(j, i);
				r[i][j] = p;
			}
		}
		return r;
	}

	public static Bitmap decodeSampledBitmapFromResource(
			int reqWidth, int reqHeight, String selectedImagePath) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(selectedImagePath, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(selectedImagePath, options);
	}


	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			final int halfHeight = height / 2;
			final int halfWidth = width / 2;
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}
		return inSampleSize;
	}
	public static Bitmap setPix(int [][] n)
	{
		Bitmap bm = Bitmap.createBitmap(n[0].length,n.length,Config.ARGB_8888);
		for (int i = 0; i < n.length; i++) {
			for (int j = 0; j < n[i].length; j++) {
				bm.setPixel(j, i, n[i][j]);
			}
		}
		return bm;
	}

}
