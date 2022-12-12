package com.bhenta.project;

import android.Manifest;
import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.net.Uri;
import android.os.*;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import de.hdodenhof.circleimageview.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.*;
import org.json.*;

public class MainActivity extends Activity {
	
	private Timer _timer = new Timer();
	
	private HashMap<String, Object> coba = new HashMap<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear4;
	private LinearLayout linear9;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private ImageView imageview1;
	private TextView textview1;
	private ImageView imageview2;
	private TextView textview2;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private ImageView imageview3;
	private TextView textview3;
	private ImageView imageview4;
	private TextView textview4;
	private CircleImageView circleimageview1;
	private TextView textview5;
	
	private Intent i = new Intent();
	private SpeechRecognizer stt;
	private RequestNetwork net;
	private RequestNetwork.RequestListener _net_request_listener;
	private TimerTask time;
	private SharedPreferences save;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		initialize(_savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= 23) {
			if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
				requestPermissions(new String[] {Manifest.permission.RECORD_AUDIO}, 1000);
			} else {
				initializeLogic();
			}
		} else {
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
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear4 = findViewById(R.id.linear4);
		linear9 = findViewById(R.id.linear9);
		linear5 = findViewById(R.id.linear5);
		linear6 = findViewById(R.id.linear6);
		imageview1 = findViewById(R.id.imageview1);
		textview1 = findViewById(R.id.textview1);
		imageview2 = findViewById(R.id.imageview2);
		textview2 = findViewById(R.id.textview2);
		linear7 = findViewById(R.id.linear7);
		linear8 = findViewById(R.id.linear8);
		imageview3 = findViewById(R.id.imageview3);
		textview3 = findViewById(R.id.textview3);
		imageview4 = findViewById(R.id.imageview4);
		textview4 = findViewById(R.id.textview4);
		circleimageview1 = findViewById(R.id.circleimageview1);
		textview5 = findViewById(R.id.textview5);
		stt = SpeechRecognizer.createSpeechRecognizer(this);
		net = new RequestNetwork(this);
		save = getSharedPreferences("save", Activity.MODE_PRIVATE);
		
		linear5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setClass(getApplicationContext(), JadwalActivity.class);
				startActivity(i);
			}
		});
		
		linear6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setAction(Intent.ACTION_VIEW);
				i.setClass(getApplicationContext(), WarnapickActivity.class);
				startActivity(i);
			}
		});
		
		linear7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				coba = new HashMap<>();
				coba.put("r", "255");
				coba.put("g", "255");
				coba.put("b", "255");
				coba.put("&", "");
				net.setHeaders(coba);
				net.startRequestNetwork(RequestNetworkController.GET, "http://192.168.4.1/", "a", _net_request_listener);
			}
		});
		
		linear8.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				coba = new HashMap<>();
				coba.put("r", "0");
				coba.put("g", "0");
				coba.put("b", "0");
				coba.put("&", "");
				net.setHeaders(coba);
				net.startRequestNetwork(RequestNetworkController.GET, "http://192.168.4.1/", "a", _net_request_listener);
			}
		});
		
		imageview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		textview4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				net.startRequestNetwork(RequestNetworkController.POST, "http://192.168.4.1/?r0g0b0&", "a", _net_request_listener);
			}
		});
		
		circleimageview1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Intent _intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
				_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
				stt.startListening(_intent);
				time = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								stt.stopListening();
							}
						});
					}
				};
				_timer.schedule(time, (int)(5000));
			}
		});
		
		stt.setRecognitionListener(new RecognitionListener() {
			@Override
			public void onReadyForSpeech(Bundle _param1) {
			}
			
			@Override
			public void onBeginningOfSpeech() {
			}
			
			@Override
			public void onRmsChanged(float _param1) {
			}
			
			@Override
			public void onBufferReceived(byte[] _param1) {
			}
			
			@Override
			public void onEndOfSpeech() {
			}
			
			@Override
			public void onPartialResults(Bundle _param1) {
			}
			
			@Override
			public void onEvent(int _param1, Bundle _param2) {
			}
			
			@Override
			public void onResults(Bundle _param1) {
				final ArrayList<String> _results = _param1.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
				final String _result = _results.get(0);
				textview5.setText(_result);
			}
			
			@Override
			public void onError(int _param1) {
				final String _errorMessage;
				switch (_param1) {
					case SpeechRecognizer.ERROR_AUDIO:
					_errorMessage = "audio error";
					break;
					
					case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
					_errorMessage = "speech timeout";
					break;
					
					case SpeechRecognizer.ERROR_NO_MATCH:
					_errorMessage = "speech no match";
					break;
					
					case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
					_errorMessage = "recognizer busy";
					break;
					
					case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
					_errorMessage = "recognizer insufficient permissions";
					break;
					
					default:
					_errorMessage = "recognizer other error";
					break;
				}
				SketchwareUtil.showMessage(getApplicationContext(), "Error");
			}
		});
		
		_net_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				
			}
		};
	}
	
	private void initializeLogic() {
		_tombol(linear5, "#FFCF0A0A", "#FFEEEEEE");
		_tombol(linear6, "#FFCF0A0A", "#FFEEEEEE");
		_tombol(linear7, "#FFCF0A0A", "#FFEEEEEE");
		_tombol(linear8, "#FFCF0A0A", "#FFEEEEEE");
	}
	
	public void _tombol(final View _view, final String _warna, final String _ripple) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[] {Color.parseColor(_warna),Color.parseColor(_warna),});
		
		ab.setCornerRadii(new float[]{(int)50,(int)50,(int)50,(int)50,(int)50,(int)50,(int)50,(int)50});
		
		android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_ripple)}), ab, null); _view.setBackground(ripdr);
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}