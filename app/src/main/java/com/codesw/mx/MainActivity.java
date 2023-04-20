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
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class MainActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	
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
	private ImageView imageview1;
	
	private Intent i = new Intent();
	private TimerTask t;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
	}
	
	private void initializeLogic() {
		t = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						i.setClass(getApplicationContext(), HomeActivity.class);
						startActivity(i);
					}
				});
			}
		};
		_timer.schedule(t, (int)(1000));
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	
}