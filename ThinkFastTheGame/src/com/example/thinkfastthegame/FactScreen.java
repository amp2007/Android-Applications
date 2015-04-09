/**Class: FactScreen.java
 * @author Allen Perry
 * @version 
 * Course : ITEC 3150 Spring 2014
 * Written: Nov 8, 2014
 *
 *
 * This class – 
 *
 */
package com.example.thinkfastthegame;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class FactScreen extends Fragment implements OnInitListener
{
	private TextToSpeech tts;
	private static final String LOG_TAG = "WebViewDemo";
	private WebView mWebView;
	private Handler mHandler = new Handler();
	
	@SuppressLint({ "JavascriptInterface", "ClickableViewAccessibility", "SetJavaScriptEnabled" })
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		final WebView mWebView = (WebView) getView().findViewById(R.id.webView);
		tts = new TextToSpeech(getActivity(), this);
		WebSettings webSettings = mWebView.getSettings();

		webSettings.setSaveFormData(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(false);
		mWebView.setWebChromeClient(new MyWebChromeClient());

		mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "demo");

		mWebView.loadUrl("http://fact-daily.com/");
		mWebView.setLongClickable(false);

		// Disable the haptic feedback so that handling the long click doesnt
		// make the phone vibrate
		mWebView.setHapticFeedbackEnabled(false);
		// Intercept long click events so that they dont reach the webview
		mWebView.setOnLongClickListener(new OnLongClickListener()
		{
			@Override
			public boolean onLongClick(final View v)
			{
				return true;
			}
		});
		// Intercept any touch event not related to scrolling/zooming
		mWebView.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(final View v, final MotionEvent event)
			{
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					// Change ACTION_DOWN location to outside of the webview so
					// that it doesnt affect
					// pressable element in the webview (buttons receiving
					// PRESS" will change appearance)
					//event.setLocation(mWebView.getWidth() + 1,
							//mWebView.getHeight() + 1);
				}
				// Intercept any "up" event
				return event.getAction() == MotionEvent.ACTION_UP;
			}
		});

		TimerTask task = new TimerTask()
		{

			@Override
			public void run()
			{
				Intent intent = new Intent(getActivity(), CategoryScreen.class);
				startActivity(intent);
			}

		};
		Timer opening = new Timer();
		opening.schedule(task, 10000);
		View rootView = inflater.inflate(R.layout.fragment_fact_screen, container,
				false);
		return rootView;
	}

	final class DemoJavaScriptInterface
	{

		DemoJavaScriptInterface()
		{
		}

		/**
		 * This is not called on the UI thread. Post a runnable to invoke
		 * loadUrl on the UI thread.
		 */
		public void clickOnAndroid()
		{
			mHandler.post(new Runnable()
			{
				public void run()
				{
					mWebView.loadUrl("javascript:wave()");
				}
			});

		}
	}
	
	final class MyWebChromeClient extends WebChromeClient
	{
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result)
		{
			Log.d(LOG_TAG, message);
			result.confirm();
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.speech.tts.TextToSpeech.OnInitListener#onInit(int)
	 */
	@Override
	public void onInit(int status)
	{
		if (status == TextToSpeech.SUCCESS)
		{
			tts.setLanguage(Locale.getDefault());
			tts.setPitch(-4);
			tts.speak("This fact will self destruct in 5 seconds",
					TextToSpeech.QUEUE_FLUSH, null);

		}
		else
		{
			tts = null;
			Toast.makeText(getActivity(), "Failed to initialize TTS engine.",
					Toast.LENGTH_SHORT).show();
		}
	}

}
