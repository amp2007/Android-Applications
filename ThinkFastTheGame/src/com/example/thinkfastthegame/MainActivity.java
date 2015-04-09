package com.example.thinkfastthegame;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends FragmentActivity
{
	AnimationDrawable brainAnimation;
	private static final int CONTENT_VIEW_ID = 10101010;
	@SuppressLint("Recycle")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//FrameLayout frame = new FrameLayout(this);
	    //frame.setId(CONTENT_VIEW_ID);
	    //setContentView(frame, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setContentView(R.layout.fragment_main);
		Button b = (Button) findViewById(R.id.mainScreenButton);
		ImageView imgRotate = (ImageView) findViewById(R.id.animImage);
		imgRotate.startAnimation(AnimationUtils.loadAnimation(this,
				R.anim.rotate));
		b.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				 /*FragmentManager fragmentManager = getSupportFragmentManager();
			        FragmentTransaction fragmentTransaction = fragmentManager
			                .beginTransaction();
			        fragmentTransaction.replace(R.layout.fragment_main, new FactScreen());
			        fragmentTransaction.commit();*/
				Fragment fragment = new FactScreen();
		        FragmentManager fragmentManager = getSupportFragmentManager();
		        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		        fragmentTransaction.replace(R.id.main_screen, fragment);
		        fragmentTransaction.commit();
			        
			}
			
		});
		/* FragmentManager fragmentManager = getSupportFragmentManager();
	        FragmentTransaction fragmentTransaction = fragmentManager
	                .beginTransaction();
	        fragmentTransaction.add(R.layout.fragment_fact_screen, new FactScreen()); */

	   
			//FactScreen factScreen = new FactScreen();
			//CategoryScreen categoryScreen = new CategoryScreen();
			//QuestionScreen questionScreen = new QuestionScreen();
			//NextQuestionScreen nextQuestionScreen = new NextQuestionScreen();
			//GameOverScreen gameOverScreen = new GameOverScreen();
			
			//transaction.add(R.id.main, new MainScreen());
			//transaction.add(R.id.container, factScreen);
			//transaction.add(R.id.container, questionScreen);
			//transaction.add(R.id.container, categoryScreen);
			//transaction.add(R.id.container, nextQuestionScreen);
			//transaction.add(R.id.container, gameOverScreen);
			//transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Fragment creating MainScreen
	 */
	/*public static class MainScreen extends Fragment
	{
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			Button b = (Button) getView().findViewById(R.id.mainScreenButton);
			ImageView imgRotate = (ImageView) getView().findViewById(R.id.animImage);
			imgRotate.startAnimation(AnimationUtils.loadAnimation(getActivity(),
					R.anim.rotate));
			b.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					startActivity(new Intent(getActivity(), FactScreen.class));
				}
				
			});
			return rootView;
		}
	}*/
	
	/**
	 * Fragment creating the FactScreen
	 */
	public static class FactScreen extends Fragment implements OnInitListener
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
			container.removeAllViews();
			View rootView = inflater.inflate(R.layout.fragment_fact_screen, container,
					false);
			final WebView mWebView = (WebView) rootView.findViewById(R.id.webView);
			tts = new TextToSpeech(rootView.getContext(), this);
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

			/*TimerTask task = new TimerTask()
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
			*/
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
	
	/**
	 * Fragment creating the CategoryScreen
	 */
	public static class CategoryScreen extends Fragment implements AccelerometerListener,
	OnInitListener
	{
		private TextToSpeech tts;
		private static final int REQUEST_CODE = 100;
		private static final int REQUEST_CODE_2 = 200;
		public static int score = 0;
		public static final int highScore = 0;
		Integer catNum = null;
		Integer[] categories =
		{ R.drawable.sports2, R.drawable.biology, R.drawable.history,
				R.drawable.politics, R.drawable.geography };
		ImageView pic;
		String cat;
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			tts = new TextToSpeech(getActivity(), null);
			Toast.makeText(getActivity(), "Select a category",
					Toast.LENGTH_SHORT).show();
			GridView gr = (GridView) getView().findViewById(R.id.gridView1);
			final ImageView pic = (ImageView) getView().findViewById(R.id.imgLarge);
			gr.setAdapter(new ImageAdapter(getActivity()));
			gr.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3)
				{
					catNum = arg2;
					displayCategory();
					Toast.makeText(getActivity(), "Selected category: " + cat,
							Toast.LENGTH_SHORT).show();
					pic.setImageResource(categories[arg2]);
				}

			});
			Button button = (Button) getView().findViewById(R.id.categoryButton);
			button.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					tts.speak(
							"Shake the device to recieve your question and begin your timer. You will have 60 seconds",
							TextToSpeech.QUEUE_FLUSH, null);
					// CategoryScreen.this.finish();
				}

			});
			View rootView = inflater.inflate(R.layout.fragment_category_screen, container,
					false);
			return rootView;
		}
		
		public void onAccelerationChanged(float x, float y, float z)
		{
			// TODO Auto-generated method stub

		}

		public void onShake(float force)
		{
			if (catNum != null)
			{
				Intent intent = new Intent(getActivity(),
						QuestionScreen.class);
				startActivity(intent);
			}
			else
			{
				//Toast.makeText(getApplicationContext(), "Must select a category",
					//	Toast.LENGTH_SHORT).show();
			}
			// Called when Motion Detected
			// Toast.makeText(getBaseContext(), "Motion detected",
			// Toast.LENGTH_SHORT)
			// .show();
		}

		@Override
		public void onResume()
		{
			super.onResume();
			// Toast.makeText(getBaseContext(), "onResume Accelerometer Started",
			// Toast.LENGTH_SHORT).show();

			// Check device supported Accelerometer senssor or not
			if (AccelerometerManager.isSupported(getActivity()))
			{

				// Start Accelerometer Listening
				AccelerometerManager.startListening(this);
			}
		}

		@Override
		public void onStop()
		{
			super.onStop();

			// Check device supported Accelerometer senssor or not
			if (AccelerometerManager.isListening())
			{

				// Start Accelerometer Listening
				AccelerometerManager.stopListening();

				// Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped",
				// Toast.LENGTH_SHORT).show();
			}

		}

		@Override
		public void onDestroy()
		{
			super.onDestroy();
			Log.i("Sensor", "Service  destroy");
			tts.shutdown();
			// Check device supported Accelerometer senssor or not
			if (AccelerometerManager.isListening())
			{

				// Start Accelerometer Listening
				AccelerometerManager.stopListening();

				// Toast.makeText(getBaseContext(),
				// "onDestroy Accelerometer Stoped",
				// Toast.LENGTH_SHORT).show();
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

			}
			else
			{
				tts = null;
				Toast.makeText(getActivity(), "Failed to initialize TTS engine.",
						Toast.LENGTH_SHORT).show();
			}

		}

		public void displayCategory()
		{
			if (catNum == 0)
			{
				cat = "Sports";
			}
			else if (catNum == 1)
			{
				cat = "Biology";
			}
			else if (catNum == 2)
			{
				cat = "History";
			}
			else if (catNum == 3)
			{
				cat = "Politics";
			}
			else if (catNum == 4)
			{
				cat = "Geography";
			}
		}

		public class ImageAdapter extends BaseAdapter
		{
			private Context context;

			/**
			 * @param mainActivity
			 */
			public ImageAdapter(Context c)
			{
				context = c;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.widget.Adapter#getCount()
			 */
			@Override
			public int getCount()
			{
				return categories.length;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.widget.Adapter#getItem(int)
			 */
			@Override
			public Object getItem(int arg0)
			{
				// TODO Auto-generated method stub
				return null;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.widget.Adapter#getItemId(int)
			 */
			@Override
			public long getItemId(int position)
			{
				// TODO Auto-generated method stub
				return 0;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see android.widget.Adapter#getView(int, android.view.View,
			 * android.view.ViewGroup)
			 */
			@Override
			public View getView(int arg0, View arg1, ViewGroup arg2)
			{
				pic = new ImageView(context);
				pic.setImageResource(categories[arg0]);
				pic.setScaleType(ImageView.ScaleType.FIT_XY);
				pic.setLayoutParams(new GridView.LayoutParams(188, 200));
				return pic;
			}
		}
	}

	
	/**
	 * Fragment creating QuestionScreen
	 */
	public static class QuestionScreen extends Fragment
	{
		TextView timer;
		int currentScore;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			currentScore = CategoryScreen.score;
			timer = (TextView) getView().findViewById(R.id.timerView);
			final Intent intent = new Intent(getActivity(), GameOverScreen.class);
			final Intent intent2 = new Intent(getActivity(), NextQuestionScreen.class);
			new CountDownTimer(10000, 1000)
			{

				public void onTick(long millisUntilFinished)
				{
					timer.setText("seconds remaining: " + millisUntilFinished
							/ 1000);
				}

				public void onFinish()
				{
					timer.setText("DONE!");

					intent.putExtra("score", currentScore);
					startActivity(intent);

				}
			}.start();
			Button submit = (Button) getView().findViewById(R.id.answerSubmit);
			submit.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					currentScore += 2;
					intent2.putExtra("score", currentScore);
					startActivity(intent2);
				}

			});
			View rootView = inflater.inflate(R.layout.fragment_question_screen,
					container, false);
			return rootView;
		}
		
	}
	
	/**
	 * Fragment creating NextQuestionScreen
	 */
	public static class NextQuestionScreen extends Fragment
	{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			final Intent intent = new Intent(getActivity(), CategoryScreen.class);
			Button button = (Button) getView().findViewById(R.id.nextQuestionButton);
			button.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					startActivity(intent);
				}
			});
			View rootView = inflater.inflate(R.layout.fragment_nextquestion_screen, container,
					false);
			return rootView;
		}
	}
	
	/**
	 * Fragment creating GameOverScreen
	 */
	public static class GameOverScreen extends Fragment
	{
		TextView finalScore;
		TextView highScore;
		int fScore;
		int hScore;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			finalScore = (TextView) getView().findViewById(R.id.actualFinalScore);
			highScore = (TextView) getView().findViewById(R.id.actualHighScore);
			Button tryAgain = (Button) getView().findViewById(R.id.tryAgainButton);
			final Intent intent = new Intent(getActivity(), CategoryScreen.class);
			tryAgain.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					CategoryScreen.score = 0;
					// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					startActivity(intent);
				}
			});
			Button quit = (Button) getView().findViewById(R.id.quitButton);
			quit.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					System.exit(0);
				}
			});
			View rootView = inflater.inflate(R.layout.fragment_gameover_screen,
					container, false);
			return rootView;
		}
	}


}
