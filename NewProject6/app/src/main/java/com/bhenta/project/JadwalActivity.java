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
import android.widget.*;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.*;
import org.json.*;
import android.text.format.DateFormat;

public class JadwalActivity extends Activity {
	
	private HashMap<String, Object> waktu = new HashMap<>();
	private String waktuku = "";
	private TimePickerDialog timePickerDialog;
	
	private ArrayList<String> timeku = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear1;
	private LinearLayout linear5;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private AnalogClock analogclock1;
	private TextView textview3;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private TextView textview2;
	private TextView textview1;
	private Button button1;
	
	private Calendar cal = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.jadwal);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		vscroll1 = findViewById(R.id.vscroll1);
		linear1 = findViewById(R.id.linear1);
		linear5 = findViewById(R.id.linear5);
		linear2 = findViewById(R.id.linear2);
		linear3 = findViewById(R.id.linear3);
		linear6 = findViewById(R.id.linear6);
		linear7 = findViewById(R.id.linear7);
		analogclock1 = findViewById(R.id.analogclock1);
		textview3 = findViewById(R.id.textview3);
		linear8 = findViewById(R.id.linear8);
		linear9 = findViewById(R.id.linear9);
		textview2 = findViewById(R.id.textview2);
		textview1 = findViewById(R.id.textview1);
		button1 = findViewById(R.id.button1);
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_picker();
			}
		});
	}
	
	private void initializeLogic() {
		linear6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFCF0A0A));
		linear7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)30, 0xFFCF0A0A));
		_tombol(button1, "#FFCF0A0A", "#FFEEEEEE");
	}
	
	public void _tombol(final View _view, final String _warna, final String _ripple) {
		android.graphics.drawable.GradientDrawable ab = new android.graphics.drawable.GradientDrawable(GradientDrawable.Orientation.BR_TL,new int[] {Color.parseColor(_warna),Color.parseColor(_warna),});
		
		ab.setCornerRadii(new float[]{(int)50,(int)50,(int)50,(int)50,(int)50,(int)50,(int)50,(int)50});
		
		android.graphics.drawable.RippleDrawable ripdr = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor(_ripple)}), ab, null); _view.setBackground(ripdr);
	}
	
	
	public void _picker() {
		timeku.clear();
		Calendar calendar = Calendar.getInstance();
		timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
			 @Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
				newDate.set(Calendar.MINUTE, minute);
				
				waktuku = new SimpleDateFormat("HH:mm").format(newDate.getTime());
				timeku = new ArrayList<String>(Arrays.asList(waktuku.split(":")));
				waktu = new HashMap<>();
				waktu.put("jam", timeku.get((int)(0)));
				waktu.put("menit", timeku.get((int)(1)));
				textview3.setText(waktu.get("jam").toString().concat(":".concat(waktu.get("menit").toString())));
			}
		},
		calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
		DateFormat.is24HourFormat(this));
		timePickerDialog.show();
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