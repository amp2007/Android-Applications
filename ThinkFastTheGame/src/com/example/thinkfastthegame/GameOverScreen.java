/**Class: GameOverScreen.java
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameOverScreen extends Fragment
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
