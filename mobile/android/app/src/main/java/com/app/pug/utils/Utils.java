package com.app.pug.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Locale;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

public final class Utils {
	
	private static final String TAG = "BMPOS";
	
	public static final void l(String s) {
		Log.d(TAG, s);
	}
	
	public static final void e(String s) {
		Log.e(TAG, s);
	}
	
	public static String toDecimal(int value) {
		return toDecimal(String.valueOf(value));
	}
	
	public static String toDecimal(double value) {
		return toDecimal(String.format(Locale.getDefault(), "%.2f", value));
	}
	
	/**
	 * Converts or casts a string to a decimal
	 * @param value The string to be converted
	 * @return String
	 */
	public static String toDecimal(String value) {
		if (value.contains(".")) {
			String before = addDecimal(value.substring(0, value.indexOf(".")));
			String after = addDecimal(value.substring(value.indexOf(".")+ 1));
			return String.format(Locale.getDefault(), "%s.%s", before, after);
		}
		return addDecimal(value);
	}
	
	public static String fromDecimal(String value) {
		if (!value.contains(",")) return value;
		String output = "";
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (c == ',') continue;
			output += value.charAt(i);
		}
		return output;
	}
	
	private static String addDecimal(String value) {
		String work = value;
		boolean negative = work.startsWith("-");
		work = negative ? work.substring(1) : work;
		String output = "";
		if (work.length() <= 3) return String.format(Locale.getDefault(), "%s%s", negative ? "-" : "", work);
		for (int i = work.length() - 1, j = 1; i >= 0; i--, j++) {
			output = work.charAt(i) + output;
			output = (j % 3 == 0 && i != 0 ? "," : "") + output;
		}
		return String.format(Locale.getDefault(), "%s%s", negative ? "-" : "", output);
	}
	
	public static void runTask(Task task, String label) {
		new Runner(task, label).start();
	}
	
	public interface Task {
		public void run() throws Exception;
	}
	
	public static String capitalizeFirst(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}
	
	public static final void pause(long l) {
		try {
			Thread.sleep(l);
		} catch (Exception e) {}
	}
	
	private static class Runner extends Thread {
		private Task task;
		
		public Runner (Task task, String label) {
			super(label);
			this.task = task;
		}
		
		@Override
		public void run() {
			try {
				task.run();
			} catch (Exception e) {
				e(">> Could not run Task [" + getName() + "]:");
				e.printStackTrace();
			}
		}
	}
	
	public static <T> void runTask(Activity a, ResultTask<T> task, String label) {
		new ResultRunner<T>(a, task, label).start();
	}
	
	public interface ResultTask<T> {
		public T run() throws Exception;
		public void onFinish(T result);
	}
	
	private static class ResultRunner<T> extends Thread {
		private ResultTask<T> task;
		private Activity a;
		
		public ResultRunner(Activity a, ResultTask<T> task, String label) {
			super(label);
			this.a = a;
			this.task = task;
		}
		
		@Override
		public void run() {
			try {
				final T result = task.run();
				if (a == null) {
					task.onFinish(result);
				} else {
					a.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							task.onFinish(result);
						}
					});
				}
			} catch (Exception e) {
				e(">> Could not run ResultTask [" + getName() + "]:");
				e.printStackTrace();
			}
		}
	}

    /**
     * Returns the Device number of the device running this app.
     * @param c Context
     * @return String
     */
	public static String getDeviceId(Context c) {
		TelephonyManager telephonyManager = (TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = telephonyManager.getDeviceId();
		if (deviceId == null) {
			deviceId = getSerialNo();
		}
		return deviceId;
    }

    /**
     *Returns the Serial number of the device running this app
     * @return String
     */
	public static String getSerialNo() {
		String serial = null;
		try {
			Class<?> c = Class.forName("android.os.SystemProperties");
			Method get = c.getMethod("get", String.class, String.class);
			serial = (String) get.invoke(c, "ril.serialnumber", "unknown-serial-no");
		} catch (Exception e) {}
		return serial;
	}

    /**
     * Called to Hide the Keyboard for the EditText
     * @param c Context
     * @param input The EditText
     */
	public static void hideKeyboard(Context c, EditText input) {
		InputMethodManager imm = (InputMethodManager)c.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
	}

    /**
     * Check if the Device is connected.
     * @param c Context
     * @return boolean
     */
	public static boolean isConnected(Context c) {
		ConnectivityManager manager = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		return (info != null && info.isConnected());
	}

    /**
     * Generates a DateStamp that can be easily stored in a database
     * @return String
     */
	public static String getDatabaseTimeStamp() {
		Calendar c = Calendar.getInstance();
		return String.format(Locale.getDefault(), "%d-%02d-%02d %02d:%02d:%02d", c.get(YEAR), c.get(MONTH) + 1, c.get(DAY_OF_MONTH),
				c.get(HOUR_OF_DAY), c.get(MINUTE), c.get(SECOND));
	}
}
