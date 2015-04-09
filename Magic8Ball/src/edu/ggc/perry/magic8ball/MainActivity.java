package edu.ggc.perry.magic8ball;

import java.util.Locale;
import java.util.Random;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		SensorEventListener, OnInitListener
{
	private TextToSpeech tts;
	private float mGZ = 0;// gravity acceleration along the z axis
	private int mEventCountSinceGZChanged = 0;
	private static final int MAX_COUNT_GZ_CHANGE = 10;
	private SensorManager mSensorManager;
	private String response;
	private TextView view;
	static MediaPlayer alert;
	int playing = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view = (TextView) findViewById(R.id.question);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
		alert = new MediaPlayer();
		alert = MediaPlayer.create(this, R.raw.phoneover);
		tts = new TextToSpeech(this, this);

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

	public void determineFuture()
	{
		Random r = new Random();

		int choice = r.nextInt(19);

		if (choice == 0)
			response = "I doubt it";
		else if (choice == 1)
			response = "It is certain";
		else if (choice == 2)
			response = "It is decidedly so";
		else if (choice == 3)
			response = "Without a doubt";
		else if (choice == 4)
			response = "Yes - definitely";
		else if (choice == 5)
			response = "You may rely on it";
		else if (choice == 6)
			response = "As I see it, yes";
		else if (choice == 7)
			response = "Most likely";
		else if (choice == 8)
			response = "Outlook good";
		else if (choice == 9)
			response = "Signs point to yes";
		else if (choice == 10)
			response = "Yes";
		else if (choice == 11)
			response = "Reply hazy, try again";
		else if (choice == 12)
			response = "Ask again later";
		else if (choice == 13)
			response = "Better not tell you now";
		else if (choice == 14)
			response = "Cannot predict now";
		else if (choice == 15)
			response = "Concentrate and ask again";
		else if (choice == 16)
			response = "Don't ask that question";
		else if (choice == 17)
			response = "You can count on it";
		else if (choice == 18)
			response = "Not in this lifetime";
		else if (choice == 19)
			response = "There is a possibility";
		else
			response = "8-BALL ERROR!";

	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		int type = event.sensor.getType();
		if (type == Sensor.TYPE_ACCELEROMETER)
		{
			float gz = event.values[2];
			if (mGZ == 0)
			{
				mGZ = gz;
			}
			else
			{
				if ((mGZ * gz) < 0)
				{
					mEventCountSinceGZChanged++;
					if (mEventCountSinceGZChanged == MAX_COUNT_GZ_CHANGE)
					{
						mGZ = gz;
						mEventCountSinceGZChanged = 0;
						if (gz > 0)
						{
							determineFuture();
							view.setText(response);
							if (tts != null && response != null)
							{
								if (!tts.isSpeaking())
								{
									tts.speak(response,
											TextToSpeech.QUEUE_FLUSH, null);
								}
							}
						}
						else if (gz < 0)
						{
							alert.start();
							if (tts != null)
							{
								if (!tts.isSpeaking())
								{
									tts.speak(
											"Turn the phone back over for response",
											TextToSpeech.QUEUE_FLUSH, null);
								}
							}
						}
					}
				}
				else
				{
					if (mEventCountSinceGZChanged > 0)
					{
						mGZ = gz;
						mEventCountSinceGZChanged = 0;
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.hardware.SensorEventListener#onAccuracyChanged(android.hardware
	 * .Sensor, int)
	 */
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.speech.tts.TextToSpeech.OnInitListener#onInit(int)
	 */
	@Override
	public void onInit(int code)
	{
		if (code == TextToSpeech.SUCCESS)
		{
			tts.setLanguage(Locale.getDefault());

		}
		else
		{
			tts = null;
			Toast.makeText(this, "Failed to initialize TTS engine.",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy()
	{
		if (tts != null)
		{
			tts.stop();

			tts.shutdown();
		}
		alert.stop();
		super.onDestroy();
	}

	public void onBackPressed()
	{
		finish();
		super.onBackPressed();
	}
}
