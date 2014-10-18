package net.columbinerebels.rebelnet;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class Home extends Activity implements OnItemClickListener {
	private ListView activitiesList;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		//TODO Get the title color working and set depending on the day
		
		activitiesList = (ListView) findViewById(R.id.activitiesList);
		activitiesList.setOnItemClickListener(this);
	}



	@Override
	protected void onPause() {
		super.onPause();
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent activity = null;
		
		//Use a hard-coded switch statement to decide on the activity to open from the list view.
		//We manually have to line it up with the string array used for the entries in the string resource file
		// TODO Make this dynamically update with the list or some other centralized thing. It isn't that important, since this is so small.
		switch(position) {
			case 0:
				activity = new Intent(Home.this, Directory.class);
				break;
			case 1:
				activity = new Intent(this, Calendars.class);
				break;
			case 2:
				activity = new Intent(this, Schedule.class);
				break;
			default:
				Toast.makeText(this, "Error: Invalid choice.", Toast.LENGTH_SHORT).show();
		}
		
		if(activity != null) startActivity(activity);
	}



	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.home_menu, menu);

		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.schoolInfo:
				Intent about = new Intent(this, About.class);
				startActivity(about);
				
				break;
		}

		return false;
	}
}
