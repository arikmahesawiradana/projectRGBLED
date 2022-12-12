package com.bhenta.project;

import android.animation.*;
import android.app.*;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class WarnapickActivity extends Activity {
	
	private String col = "";
	private String merah = "";
	private String hijau = "";
	private String biru = "";
	private String url = "";
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private TextView textview11;
	private LinearLayout linear10;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private SeekBar seekbar1;
	private TextView textview1;
	private LinearLayout linear8;
	private SeekBar seekbar2;
	private TextView textview2;
	private LinearLayout linear9;
	private SeekBar seekbar3;
	private TextView textview3;
	private TextView textview4;
	private TextView textview5;
	private TextView textview6;
	private TextView textview7;
	private TextView textview8;
	private TextView textview9;
	private WebView webview1;
	private Button button1;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.warnapick);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear4 = findViewById(R.id.linear4);
		linear5 = findViewById(R.id.linear5);
		textview11 = findViewById(R.id.textview11);
		linear10 = findViewById(R.id.linear10);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		seekbar1 = findViewById(R.id.seekbar1);
		textview1 = findViewById(R.id.textview1);
		linear8 = findViewById(R.id.linear8);
		seekbar2 = findViewById(R.id.seekbar2);
		textview2 = findViewById(R.id.textview2);
		linear9 = findViewById(R.id.linear9);
		seekbar3 = findViewById(R.id.seekbar3);
		textview3 = findViewById(R.id.textview3);
		textview4 = findViewById(R.id.textview4);
		textview5 = findViewById(R.id.textview5);
		textview6 = findViewById(R.id.textview6);
		textview7 = findViewById(R.id.textview7);
		textview8 = findViewById(R.id.textview8);
		textview9 = findViewById(R.id.textview9);
		webview1 = findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		button1 = findViewById(R.id.button1);
		
		linear10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				col = textview5.getText().toString() + textview7.getText().toString() + textview9.getText().toString();
				String str = col;
				StringBuffer sb = new StringBuffer();
				
				char ch[] = str.toCharArray();
				for(int i = 0; i < ch.length; i++) {
					    int num = ch[i] - '0';
					    String hexString = Integer.toHexString(num);
					    sb.append(hexString);
				}
				String result = sb.toString();
				textview9.setText(result);
				/*
_warna(linear10, "#" + result);
*/
			}
		});
		
		seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				merah = "r".concat(String.valueOf((long)(_progressValue)));
				textview5.setText(String.format("%03d", _progressValue));
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				hijau = "g".concat(String.valueOf((long)(_progressValue)));
				textview7.setText(String.format("%03d", _progressValue));
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar _param1, int _param2, boolean _param3) {
				final int _progressValue = _param2;
				biru = "b".concat(String.valueOf((long)(_progressValue)));
				textview9.setText(String.format("%03d", _progressValue));
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar _param1) {
				
			}
			
			@Override
			public void onStopTrackingTouch(SeekBar _param2) {
				
			}
		});
		
		//webviewOnProgressChanged
		webview1.setWebChromeClient(new WebChromeClient() {
				@Override public void onProgressChanged(WebView view, int _newProgress) {
					
				}
		});
		
		//OnDownloadStarted
		webview1.setDownloadListener(new DownloadListener() {
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				DownloadManager.Request webview1a = new DownloadManager.Request(Uri.parse(url));
				String webview1b = CookieManager.getInstance().getCookie(url);
				webview1a.addRequestHeader("cookie", webview1b);
				webview1a.addRequestHeader("User-Agent", userAgent);
				webview1a.setDescription("Downloading file...");
				webview1a.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
				webview1a.allowScanningByMediaScanner(); webview1a.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); webview1a.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
				
				DownloadManager webview1c = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
				webview1c.enqueue(webview1a);
				showMessage("Downloading File....");
				BroadcastReceiver onComplete = new BroadcastReceiver() {
					public void onReceive(Context ctxt, Intent intent) {
						showMessage("Download Complete!");
						unregisterReceiver(this);
						
					}};
				registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
			}
		});
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				url = "http://192.168.4.1/?" + merah + hijau + biru;
				webview1.loadUrl(url);
			}
		});
	}
	
	private void initializeLogic() {
		linear2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFF000000));
		linear3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFF000000));
		linear4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFF000000));
		linear10.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)50, 0xFF000000));
		_tombol(button1, "#FF000000", "#FFEEEEEE");
	}
	
	public void _tombol(final View _view, final String _warna, final String _ripple) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[] {Color.parseColor(_warna),Color.parseColor(_warna),});
		
		ab.setCornerRadii(new float[]{(int)50,(int)50,(int)50,(int)50,(int)50,(int)50,(int)50,(int)50});
		
		android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_ripple)}), ab, null); _view.setBackground(ripdr);
	}
	
	
	public void _warna(final View _view, final String _warna) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[] {Color.parseColor(_warna),Color.parseColor(_warna),});
		_view.setBackground(ab);
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