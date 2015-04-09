/**Class: MainScreen.java
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
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainScreen extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
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
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		return rootView;
	}
}
