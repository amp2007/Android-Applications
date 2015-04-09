/**Class: QuestionScreen.java
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

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class QuestionScreen extends Fragment
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
