package com.example.ece493assignment1;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	private Bitmap bm;
	private static final int SELECT_PICTURE = 1;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuItem buttonSettings = menu.add("Settings");  
	    buttonSettings.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
	    buttonSettings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

	        public boolean onMenuItemClick(MenuItem item) {
	            Intent intent = new Intent(MainActivity.this, SettingsActivity.class); 
	            MainActivity.this.startActivity(intent);
	            return false; 
	        }
	    });
	return true;
	}
	public void processImage(View v)
	{
		ImageView image = (ImageView)findViewById(R.id.imageView1);
		if(image.getDrawable() == null)
		{
			Toast.makeText(this, "Please select an image", Toast.LENGTH_LONG).show();
			return;
		}
		new ApplyFilterTask().execute(ImageHelper.getPix(bm));
	}

	public void loadImage(View v)
	{
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent,
				"Select Picture"), SELECT_PICTURE);
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				ImageView image = (ImageView) findViewById(R.id.imageView1);
				bm = ImageHelper.decodeSampledBitmapFromResource(50,50, getPath(selectedImageUri));
				image.setImageBitmap(bm);
			}
		}
	}



	private String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if( cursor != null ){
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		return uri.getPath();
	}
	private class ApplyFilterTask extends AsyncTask<int[][], Void, int[][]>
	{

		private ProgressDialog dialog;
		@Override
		protected void onPreExecute() 
		{
			dialog = ProgressDialog.show(MainActivity.this, "",
					"Loading...", false);
		}

		@Override
		protected int[][] doInBackground(int[][]... r)
		{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
			Integer size = Integer.parseInt(prefs.getString("Filter Size",null));
			//switch on is mean
			if(prefs.getBoolean("FilterType", false))
				return new ImageFilter(r[0],size).applyMeanFilter();
			else
				return new ImageFilter(r[0],size).applyMedianFilter();
		}

		@Override
		protected void onPostExecute(int[][] result)
		{
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			ImageView im = (ImageView)findViewById(R.id.imageView1);
			im.setImageBitmap(ImageHelper.setPix(result));
		}

	}



}
