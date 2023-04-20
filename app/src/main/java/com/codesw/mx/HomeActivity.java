package com.codesw.mx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.content.Intent;
import android.content.ClipData;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.net.Uri;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class HomeActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_FP = 101;
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> mapvar = new HashMap<>();
	private double index = 0;
	private String path = "";
	private String directory = "";
	private double index1 = 0;
	private double folderExist = 0;
	private double size = 0;
	private String humanReadableSize = "";
	private double folderView = 0;
	private double duration = 0;
	private String videoDuration = "";
	private String selectedPath = "";
	private String videoPath = "";
	private String videoSize = "";
	private String videoHeight = "";
	private String videoWidth = "";
	private String videoOrientation = "";
	private String videoDate = "";
	private double createdTimeMillisec = 0;
	private String formattedDate = "";
	
	private ArrayList<HashMap<String, Object>> folders = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> videos = new ArrayList<>();
	private ArrayList<String> list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> allVideosList = new ArrayList<>();
	
	private LinearLayout linear1;
	private RecyclerView recyclerview1;
	private RecyclerView recyclerview2;
	
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private Calendar calendar = Calendar.getInstance();
	private Intent i = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.home);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
		recyclerview2 = (RecyclerView) findViewById(R.id.recyclerview2);
		fp.setType("*/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
	}
	
	private void initializeLogic() {
		_ToolBarTitlWithColor();
		
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View _v) {
								//do nothing
						}
				});
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
		_getAllVideos();
		folderView = 1;
		_getFolders();
		_SortMap(folders, "lowerCaseDirectoryName", false, true);
		recyclerview2.setVisibility(View.GONE);
		recyclerview1.setLayoutManager(new LinearLayoutManager(this));
		recyclerview2.setLayoutManager(new LinearLayoutManager(this));
		recyclerview1.setAdapter(new Recyclerview1Adapter(folders));
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (folderView == 0) {
			recyclerview1.setVisibility(View.VISIBLE);
			recyclerview2.setVisibility(View.GONE);
			folderView = 1;
		}
		else {
			finish();
		}
	}
	public void _getAllVideos () {
		Uri uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
		String[] projection = {
			android.provider.MediaStore.Video.VideoColumns.DATA,
			 android.provider.MediaStore.Video.VideoColumns.SIZE,
			 android.provider.MediaStore.Video.VideoColumns.HEIGHT,
			 android.provider.MediaStore.Video.VideoColumns.WIDTH,
			 android.provider.MediaStore.Video.VideoColumns.DURATION,
			 android.provider.MediaStore.Video.VideoColumns.DATE_MODIFIED
		};
		android.database.Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				videoPath = cursor.getString(0);
				videoSize = cursor.getString(1);
				videoHeight = cursor.getString(2);
				videoWidth = cursor.getString(3);
				videoDuration = cursor.getString(4);
				videoDate = cursor.getString(5);
				mapvar = new HashMap<>();
				mapvar.put("videoPath", videoPath);
				mapvar.put("videoSize", videoSize);
				mapvar.put("videoHeight", videoHeight);
				mapvar.put("videoWidth", videoWidth);
				mapvar.put("videoDate", videoDate);
				mapvar.put("videoDuration", videoDuration);
				createdTimeMillisec = new java.io.File(videoPath).lastModified();
				calendar.setTimeInMillis((long)(createdTimeMillisec));
				formattedDate = new SimpleDateFormat("dd MMM").format(calendar.getTime());
				mapvar.put("formattedDate", formattedDate.toUpperCase());
				allVideosList.add(mapvar);
			}
			cursor.close();
		}
	}
	
	
	public void _getFolders () {
		index = 0;
		for(int _repeat12 = 0; _repeat12 < (int)(allVideosList.size()); _repeat12++) {
			path = allVideosList.get((int)index).get("videoPath").toString();
			java.io.File file = new java.io.File(path);
			directory = file.getParent();
			index1 = 0;
			folderExist = 0;
			for(int _repeat36 = 0; _repeat36 < (int)(folders.size()); _repeat36++) {
				if (directory.equals(folders.get((int)index1).get("directory").toString())) {
					folderExist = 1;
					folders.get((int)index1).put("items", String.valueOf((long)(Double.parseDouble(folders.get((int)index1).get("items").toString()) + 1)));
					folders.get((int)index1).put("size", String.valueOf((long)(Double.parseDouble(folders.get((int)index1).get("size").toString()) + FileUtil.getFileLength(path))));
				}
				index1++;
			}
			if (folderExist == 0) {
				mapvar = new HashMap<>();
				mapvar.put("directory", directory);
				mapvar.put("directoryName", Uri.parse(directory).getLastPathSegment());
				mapvar.put("lowerCaseDirectoryName", Uri.parse(directory).getLastPathSegment().toLowerCase());
				mapvar.put("items", "1");
				mapvar.put("size", String.valueOf((long)(FileUtil.getFileLength(path))));
				folders.add(mapvar);
			}
			index++;
		}
	}
	
	
	public void _SortMap (final ArrayList<HashMap<String, Object>> _listMap, final String _key, final boolean _isNumber, final boolean _Ascending) {
		Collections.sort(_listMap, new Comparator<HashMap<String,Object>>(){
			public int compare(HashMap<String,Object> _compareMap1, HashMap<String,Object> _compareMap2){
				if (_isNumber) {
					int _count1 = Integer.valueOf(_compareMap1.get(_key).toString());
					int _count2 = Integer.valueOf(_compareMap2.get(_key).toString());
					if (_Ascending) {
						return _count1 < _count2 ? -1 : _count1 < _count2 ? 1 : 0;
					}
					else {
						return _count1 > _count2 ? -1 : _count1 > _count2 ? 1 : 0;
					}
				}
				else {
					if (_Ascending) {
						return (_compareMap1.get(_key).toString()).compareTo(_compareMap2.get(_key).toString());
					}
					else {
						return (_compareMap2.get(_key).toString()).compareTo(_compareMap1.get(_key).toString());
					}
				}
			}});
	}
	
	
	public void _library () {
	}
	private String bytesIntoHumanReadable(long bytes) {
		    long kilobyte = 1024;
		    long megabyte = kilobyte * 1024;
		    long gigabyte = megabyte * 1024;
		    long terabyte = gigabyte * 1024;
		
		    if ((bytes >= 0) && (bytes < kilobyte)) {
			        return bytes + " B";
			
			    } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
			        return (bytes / kilobyte) + " KB";
			
			    } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
			        return (bytes / megabyte) + " MB";
			
			    } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
			        return (bytes / gigabyte) + " GB";
			
			    } else if (bytes >= terabyte) {
			        return (bytes / terabyte) + " TB";
			
			    } else {
			        return bytes + " Bytes";
			    }
	}
	{
	}
	private String stringForTime(int timeMs) {
		StringBuilder mFormatBuilder = null;
		Formatter mFormatter = null;
		mFormatBuilder = new StringBuilder();
		mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
		        int totalSeconds = timeMs / 1000;
		
		        int seconds = totalSeconds % 60;
		        int minutes = (totalSeconds / 60) % 60;
		        int hours = totalSeconds / 3600;
		
		        mFormatBuilder.setLength(0);
		        if (hours > 0) {
			            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
			        } else {
			            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
			        }
		    }
	{
	}
	
	
	public void _shapeRadius (final View _v, final String _color, final double _radius) {
		android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
		  shape.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
		
		shape.setCornerRadius((int)_radius);
		
		shape.setColor(Color.parseColor(_color));
		_v.setBackgroundDrawable(shape);
	}
	
	
	public void _getVideos (final String _directory) {
		videos.clear();
		index = 0;
		for(int _repeat11 = 0; _repeat11 < (int)(allVideosList.size()); _repeat11++) {
			path = allVideosList.get((int)index).get("videoPath").toString();
			java.io.File file = new java.io.File(path);
			directory = file.getParent();
			if (directory.equals(_directory)) {
				mapvar = allVideosList.get((int)index);
				videos.add(mapvar);
			}
			index++;
		}
		recyclerview1.setVisibility(View.GONE);
		recyclerview2.setAdapter(new Recyclerview2Adapter(videos));
		recyclerview2.setVisibility(View.VISIBLE);
	}
	
	
	public void _ToolBarTitlWithColor () {
		getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Video Player</font>"));
	}
	
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.folder_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			_shapeRadius(textview3, "#E0E0E0", 5);
			textview1.setText(folders.get((int)_position).get("directoryName").toString());
			textview2.setText(folders.get((int)_position).get("items").toString().concat(" videos"));
			size = Double.parseDouble(folders.get((int)_position).get("size").toString());
			humanReadableSize = bytesIntoHumanReadable((long)size);
			textview3.setText(humanReadableSize);
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					folderView = 0;
					_getVideos(folders.get((int)_position).get("directory").toString());
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}
		
	}
	
	public class Recyclerview2Adapter extends RecyclerView.Adapter<Recyclerview2Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.video_item, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear7 = (LinearLayout) _view.findViewById(R.id.linear7);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			final TextView textview4 = (TextView) _view.findViewById(R.id.textview4);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			_shapeRadius(textview1, "#000000", 5);
			_shapeRadius(textview3, "#E0E0E0", 5);
			_shapeRadius(textview4, "#E0E0E0", 5);
			duration = Double.parseDouble(videos.get((int)_position).get("videoDuration").toString());
			videoDuration = stringForTime((int)duration);
			textview1.setText(videoDuration);
			path = videos.get((int)_position).get("videoPath").toString();
			//using cardview programmatically inside listview is not good. use recyclerview instead of listview.
			androidx.cardview.widget.CardView cardview1 = new androidx.cardview.widget.CardView(HomeActivity.this);
			cardview1.setCardElevation(0);
			cardview1.setRadius(10);
			ViewGroup imageParent = ((ViewGroup)imageview1.getParent()); imageParent.removeView(imageview1);
			cardview1.addView(imageview1);
			imageParent.addView(cardview1);
			textview2.setText(Uri.parse(path).getLastPathSegment());
			size = Double.parseDouble(videos.get((int)_position).get("videoSize").toString());
			humanReadableSize = bytesIntoHumanReadable((long)size);
			textview3.setText(humanReadableSize);
			com.bumptech.glide.Glide.with(getApplicationContext())
			.load(path)
			.into(imageview1);
			textview4.setText(videos.get((int)_position).get("formattedDate").toString());
			linear1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					i.setClass(getApplicationContext(), VideoActivity.class);
					mapvar = videos.get((int)_position);
					i.putExtra("data", new Gson().toJson(mapvar));
					startActivity(i);
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}
		
	}
	
	
}