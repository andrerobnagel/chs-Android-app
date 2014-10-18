package net.columbinerebels.rebelnet;

import android.app.Application;
import android.widget.Toast;

/*
Set our own defined base application class
This is so we can have global variables and functions that can be accessed in all activities
Trying to pass state variables from intent to intent throughout the app will be a mess
In the manifest, this has been set as the base application class
*/
public class MyApplication extends Application {
	//Everything is good (true) by default. If we can't get the resource, then we'll set it as false.
	private boolean gotStaffDirectory = true; //Whether or not the staff directory was retrieved
	private boolean gotSchoolInfo = true; //Whether or not the school information was retrieved
	
	public boolean getStaffDirectoryGot() {
		return gotStaffDirectory;
	}
	
	public void setStaffDirectoryGot(boolean gotIt) {
		this.gotStaffDirectory = gotIt;
	}


	public boolean getSchoolInfoGot() {
		return gotSchoolInfo;
	}
	
	public void setSchoolInfoGot(boolean gotIt) {
		this.gotSchoolInfo = gotIt;
	}
}
