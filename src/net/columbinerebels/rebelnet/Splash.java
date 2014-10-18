package net.columbinerebels.rebelnet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Splash extends Activity {
	private TextView currentlyDownloading;
	private SharedPreferences persistantStatuses;
	private FileOutputStream fileOut;
	private FileInputStream fileIn;
	private String STAFF_DIRECTORY_FILE = "staffDirectory";
	private String SCHOOL_INFO_FILE = "schoolInfo";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// TODO Set title bar color depending on day

		setContentView(R.layout.splash);

		currentlyDownloading = (TextView) findViewById(R.id.currentlyDownloading);

		//These statuses persist while the app exists on the phone
		persistantStatuses = getSharedPreferences("persistantStatuses", 0);
		SharedPreferences.Editor persistantStatsEditor = persistantStatuses.edit();


		//Date and calendar objects to work with deciding on when to redownload resources
		Calendar currentCal = new GregorianCalendar();

		long lastDownloadDate = persistantStatuses.getLong("lastYearlyChangingDownloadTime", 0);
		Calendar lastDownloadCal = Calendar.getInstance();
		lastDownloadCal.setTime(new Date(lastDownloadDate));


		//If there are any errors loading anything, we set a global state variable so the rest of the app knows


		//Check if we have passed the date to redownload information, and the last time the information was downloaded was before that date
		//Using calendar objects may be a little bulky, but it makes checking for redownload pretty simple.
		//TODO Add second date check for jan 1
		if (
			(currentCal.get(Calendar.MONTH) > R.integer.yearly_redownload_month && currentCal.get(Calendar.DAY_OF_MONTH) > R.integer.yearly_redownload_day)
			&& (lastDownloadCal.get(Calendar.MONTH) < R.integer.yearly_redownload_month && lastDownloadCal.get(Calendar.DAY_OF_WEEK) < R.integer.yearly_redownload_day)
			|| !getBaseContext().getFileStreamPath(STAFF_DIRECTORY_FILE).exists()
		) {
			currentlyDownloading.setText("Downloading staff directory");			
			

			try {
				//Download the staff directory. Save the entire table in a file.
				Document staffDirectoryPage = Jsoup.connect("https://sites.google.com/a/jeffcoschools.us/chs-test-4/home/FacultyStaff-Directory").get();
				Elements staffDirectoryTable = staffDirectoryPage.select("#" + R.string.staff_directory_table);

				fileOut = openFileOutput(STAFF_DIRECTORY_FILE, Context.MODE_PRIVATE);
				fileOut.write(staffDirectoryTable.toString().getBytes());
				fileOut.close();
			} catch (Exception e) {
				((MyApplication) this.getApplication()).setStaffDirectoryGot(false);
			}

			currentlyDownloading.setText("Downloading school information");
			
			//Set the last download time to now
			persistantStatsEditor.putLong("LastYearlyChangingDownloadTime", currentCal.getTimeInMillis());
			persistantStatsEditor.commit();
		}

		((MyApplication) this.getApplication()).setStaffDirectoryGot(false);

		
		
		//Thread is here only for sleep purposes. Will be removed when we no longer need to see the splash screen for a long time.
		Thread init = new Thread() {
			public void run() {
				try {
					sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					Intent home = new Intent(Splash.this, Home.class);
					startActivity(home);
					finish(); //So the user can't press the back button and come back to the splash screen
				}
			}
		};

		init.start();
	}
}
