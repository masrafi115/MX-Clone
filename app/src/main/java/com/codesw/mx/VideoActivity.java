package com.codesw.mx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
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
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class VideoActivity extends  AppCompatActivity  { 
	
	
	private String path = "";
	private HashMap<String, Object> data = new HashMap<>();
	private String title = "";
	private double videoHeight = 0;
	private double videoWidth = 0;
	
	private LinearLayout linear1;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.video);
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
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
	}
	
	private void initializeLogic() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		getWindow().setStatusBarColor(Color.TRANSPARENT);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		data = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<HashMap<String, Object>>(){}.getType());
		videoHeight = Double.parseDouble(data.get("videoHeight").toString());
		videoWidth = Double.parseDouble(data.get("videoWidth").toString());
		if (videoWidth > videoHeight) {
			setRequestedOrientation(Build.VERSION.SDK_INT < 9 ?
			
			android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
			                  android.content.pm.ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		}
		View inflate = getLayoutInflater().inflate(R.layout.videoview, null);
		linear1.addView(inflate);
		path = data.get("videoPath").toString();
		title = Uri.parse(path).getLastPathSegment();
		videoView = inflate.findViewById(R.id.videoView);
		mediaController = inflate.findViewById(R.id.media_controller);
		videoView.setMediaController(mediaController);
		videoView.setVideoPath(path);
		videoView.requestFocus();
		videoView.start();
		mediaController.setTitle(title);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _library () {
	}
	com.universalvideoview.UniversalVideoView videoView;
	com.universalvideoview.UniversalMediaController mediaController;
	{
	}
	
	
	
}