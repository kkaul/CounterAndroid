package edu.hofstra.cs.threadedcounter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TwoThreadsActivity extends Activity implements Runnable, OnClickListener{

	Handler handler;
	TextView textview;
	double counter;
	Button start, stop,clear;
	private boolean continue_clock = false;
    @Override
    
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        handler = new Handler();
        this.setTitle("Stop-Clock App");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        
        textview = new TextView(this);
        textview.setText("Press Start");
        textview.setGravity(Gravity.CENTER);
        
        start = new Button(this);
        start.setText("Start");
        start.setOnClickListener(this);
        
        stop = new Button(this);
        stop.setText("Stop");
        stop.setOnClickListener(this);
        
        clear = new Button(this);
        clear.setText("Clear");
        clear.setOnClickListener(this);
        
        layout.addView(textview);
        layout.addView(start);
        layout.addView(stop);
        layout.addView(clear);
        
        setContentView(layout);
    }
    @Override
    
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        menu.add(0, 1, 0, "Exit").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) 
    {

		switch (item.getItemId()) 
		{
		case 1:
			finish();
			break;
		}
		return true;
	}

	public void run() 
	{
		
		while ( continue_clock )
		{
			counter += 0.1;
			handler.post(new Runnable()
			{

				public void run() 
				{
					counter = Math.round(100*counter)/((double)100);
				}
			});
			try 
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void onClick(View v) 
	{
		if ( v == start)
		{
			textview.setText("Press Stop to see your time");
			continue_clock = true;
			Thread t = new Thread(this);
			t.start();
		}
		else if ( v == stop )
		{
			textview.setText("Time elapsed: "+counter + " seconds.");
			continue_clock  = false;
		}
		
		else if( v == clear)
		{
			textview.setText("Press Start");
			counter = 0;
		}
		
	} 
}
