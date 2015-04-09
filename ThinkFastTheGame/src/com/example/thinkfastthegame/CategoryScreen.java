/**Class: CategoryScreen.java
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

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class CategoryScreen extends Fragment implements AccelerometerListener,
OnInitListener
{
	private TextToSpeech tts;
	private static final int REQUEST_CODE = 100;
	private static final int REQUEST_CODE_2 = 200;
	public static int score = 0;
	public static int highScore = 0;
	public static int categoryNumber;
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
