/*
 *    This file is part of libalexandria.
 *
 *    libalexandria is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    libalexandria is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with libalexandria.  If not, see <http://www.gnu.org/licenses/>.
 */
package lib.alexandria.lannister;

import java.util.HashMap;
import java.util.LinkedList;

import library.alexandria.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Lannister extends Activity {
	private MLReceiver receiver;

	private SimpleAdapter model;
	private LinkedList<HashMap<String, String>> data;

	private static final String[] data_labels = { "Action", "Type" };
	private static final int[] data_views = { android.R.id.text1, android.R.id.text2 };

	public void addItem(String label) {
		HashMap<String, String> info = new HashMap<String, String>();
		info.put(data_labels[0], "Learning Model -- KSVM");
		info.put(data_labels[1], label);
		data.add(info);
		model.notifyDataSetChanged();
	}
	
	public void onPushButton(View target) {
		/* Spawn a top-level intent */
		Intent intent = new Intent(this, MLService.class);
		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		/* Bundle a new work unit */
		Bundle bundle = new Bundle(1);
		// bundle.putParcelable(Homework.UUID, new Homework(new KSVM()));
		intent.putExtra(MLService.SMALL_WORK_UNIT, bundle);
		/* Wrap in a prompt TODO do first? */
		Intent request = Intent.createChooser(intent, "Adding KSVM...");
		this.startService(request);
		Toast.makeText(this, "Okay, we'll get right on that.", Toast.LENGTH_SHORT).show();
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lannister);

        /* Set up a receiver with no special permissions TODO security? */
		IntentFilter filter = new IntentFilter(Intent.CATEGORY_DEFAULT);
		filter.addCategory(MLReceiver.REQ);
		filter.addCategory(MLReceiver.ACK);
		receiver = new MLReceiver(this);
		this.registerReceiver(receiver, filter, null, new MLHandler(receiver));
		
		data = new LinkedList<HashMap<String, String>>();
		/* TODO remove, add a dummy entry */
		HashMap<String, String> dummy = new HashMap<String, String>();
		dummy.put(data_labels[0], "dummy0");
		dummy.put(data_labels[1], "dummy1");
		data.add(dummy);
		/* Create a model for the raw data */
		model = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, data_labels, data_views);
		ListView main_list = (ListView) (this.findViewById(R.id.main_intents));
		main_list.setAdapter(model);
		model.notifyDataSetChanged();
    }
    
    @Override
    public void onDestroy() {
    	this.unregisterReceiver(receiver);
    	super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lannister, menu);
        return true;
    }
}
