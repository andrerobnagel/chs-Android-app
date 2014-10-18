package net.columbinerebels.rebelnet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Schedule extends Activity implements OnClickListener {
	private static final short NUM_PERIODS = 8; //A constant for the number of periods we have
	private Button save, clear;
	private SharedPreferences scheduleInfo;

	//Use arrays and loops to access all of the edit texts and spinners to prevent excessive code
	private EditText[] classes = new EditText[NUM_PERIODS];
	private int[] classIds = {R.id.pd1ClassName, R.id.pd2ClassName, R.id.pd3ClassName, R.id.pd4ClassName, R.id.pd5ClassName, R.id.pd6ClassName, R.id.pd7ClassName, R.id.pd8ClassName};

	private Spinner[] classrooms = new Spinner[NUM_PERIODS];
	private int[] classroomIds = {R.id.pd1ClassRoom, R.id.pd2ClassRoom, R.id.pd3ClassRoom, R.id.pd4ClassRoom, R.id.pd5ClassRoom, R.id.pd6ClassRoom, R.id.pd7ClassRoom, R.id.pd8ClassRoom};

	DialogInterface.OnClickListener clearScheduleListener;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);

		ArrayAdapter<CharSequence> adapt = ArrayAdapter.createFromResource(this, R.array.classrooms, android.R.layout.simple_spinner_item);

		if (getBaseContext().getFileStreamPath("scheduleInfo").exists()) {
			Toast.makeText(this, "Is made", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Not made", Toast.LENGTH_SHORT).show();
		}

		save = (Button) findViewById(R.id.save);
		save.setOnClickListener(this);
		
		clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(this);


		for (int i = 0; i < NUM_PERIODS; i++) {
			classes[i] = (EditText) findViewById(classIds[i]);
			classrooms[i] = (Spinner) findViewById(classroomIds[i]);
			classrooms[i].setAdapter(adapt);
		}

		scheduleInfo = getSharedPreferences("scheduleInfo",0);
		SharedPreferences.Editor edit = scheduleInfo.edit();
		edit.putString("tacos", "flubber");
		edit.commit();
		
		scheduleInfo = getSharedPreferences("scheduleInfo", 0);
		Toast.makeText(this, scheduleInfo.getString("taco", "mcnugget"), Toast.LENGTH_SHORT).show();


		//This is called if the user chooses yes on the dialog to clear their schedule
		//I can't really have this in the clear schedule function, so this just clears the schedule
		clearScheduleListener = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == DialogInterface.BUTTON_POSITIVE) {
					Toast.makeText(Schedule.this, "Schedule cleared.", Toast.LENGTH_SHORT).show();
				}
			}
		};
	}



	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.schedule_menu, menu);

		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.about:
				Intent aboutSchedule = new Intent(this, AboutSchedule.class);
				startActivity(aboutSchedule);

				break;
			case R.id.menuSave:
				saveSchedule();

				break;
			case R.id.menuClearSchedule:
				clearSchedule();
				
				break;
		}

		return false;
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.save:
				saveSchedule();

				break;
			case R.id.clear:
				clearSchedule();

				break;
		}
	}



	//Use a function because there's two ways to save
	private void saveSchedule() {
		SharedPreferences.Editor scheduleEditor = scheduleInfo.edit();
		
		
		for (int currentEditText = 0; currentEditText < NUM_PERIODS; currentEditText++) {
			scheduleEditor.putString("classNamePd" + currentEditText, classes[currentEditText].getText().toString());
			scheduleEditor.putString("classRoomPd" + currentEditText, classrooms[currentEditText].getSelectedItem().toString());
		}
		
		scheduleEditor.commit();
		
		Toast.makeText(this, "Schedule saved!", Toast.LENGTH_SHORT).show();
	}



	private void clearSchedule() {
		//Don't set the listener for no, as we do nothing.
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Clear your schedule?")
				.setPositiveButton("Yes", clearScheduleListener)
				.setNegativeButton("No", null).show();
	}
}
