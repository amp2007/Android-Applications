/**Class: NextQuestionScreen.java
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

public class NextQuestionScreen extends Fragment
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
