package net.columbinerebels.rebelnet;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Directory extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		boolean directoryGot = ((MyApplication) this.getApplication()).getStaffDirectoryGot();
		
		if (directoryGot == false) {
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.error_toast, (ViewGroup) findViewById(R.id.toast_layout_root));

			TextView errorText = (TextView) layout.findViewById(R.id.text);
	        errorText.setText("Could not load directory. Some items may be outdated.");

			Toast directoryOutdated = new Toast(this);
			directoryOutdated.setDuration(Toast.LENGTH_LONG);
			directoryOutdated.setView(layout);
			directoryOutdated.show();
			
			Toast.makeText(this, "A sample toast", Toast.LENGTH_LONG).show();
		}
	}
}
