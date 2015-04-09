/**Class: AccelerometerListener.java
 * @author Allen Perry
 * @version 
 * Course : ITEC 3150 Spring 2014
 * Written: Oct 30, 2014
 *
 *
 * This class – 
 *
 */
package com.example.thinkfastthegame;

public interface AccelerometerListener
{

	public void onAccelerationChanged(float x, float y, float z);

	public void onShake(float force);

}
