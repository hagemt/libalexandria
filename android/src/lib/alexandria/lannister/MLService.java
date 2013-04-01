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
import java.util.Map;
import java.util.Map.Entry;

import lib.alexandria.Model;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

public class MLService extends IntentService {
	public static final String LARGE_WORK_UNIT = "lib.alexandria.lannister.work.unit.large";
	public static final String SMALL_WORK_UNIT = "lib.alexandria.lannister.work.unit.small";

	public static final String STATUS  = "lib.alexandria.lannister.work.status";
	
	private static final String FAILURE = "lib.alexandria.lannister.work.invalid";
	private static final String INTERNAL_NAME = "lib.alexandria.lannister.service";
	
	private final Model.Stub binding;
	private Map<Bundle, Handler> pool;
	
	public MLService() {
		super(INTERNAL_NAME);
		pool = new HashMap<Bundle, Handler>();
		binding = new Model.Stub() {
			@Override
			public void run() throws RemoteException {
				// TODO Auto-generated method stub
			}
		};
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return binding;
	}

	@Override
	public void onDestroy() {
		for (Entry<Bundle, Handler> e : pool.entrySet()) {
			Handler h = e.getValue();
			Message msg = Message.obtain(h);
			msg.setData(e.getKey());
			h.handleMessage(msg);
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.v("MLService", "Handling intent: " + intent.toString());
		/* For each work unit received, we will send back an ACK */
		Bundle work_units = intent.getExtras();
		Intent broadcast = new Intent(MLReceiver.ACK);
		broadcast.addCategory(Intent.CATEGORY_DEFAULT);
		for (String label : work_units.keySet()) {
			/* Extract valid bundles */
			Homework hw = null;
			String status = FAILURE;
			Bundle unit = work_units.getBundle(label);
			if (unit != null && (hw = unit.getParcelable(Homework.UUID)) != null) {
				status = hw.toString();
				Handler handler = new MLHandler(hw);
				pool.put(unit, handler);
			}
			/* Include status info */
			broadcast.putExtra(STATUS, status);
			this.sendBroadcast(broadcast);
		}
	}
}
