package net.columbinerebels.rebelnet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

//Opened as a dialog box on the home page when the menu item "about" is chosen
public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Having the title in the dialog box is unnecessary and distracting
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.about);
	}
}
