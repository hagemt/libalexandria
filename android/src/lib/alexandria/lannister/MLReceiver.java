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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MLReceiver extends BroadcastReceiver implements Pingable {
	public static final String REQ = "lib.alexandria.lannister.action.requested";
	public static final String ACK = "lib.alexandria.lannister.action.accepted";
	
	private boolean enabled;
	private Lannister activity;
	
	public MLReceiver(Lannister activity) {
		this(activity, true);
	}
	
	public MLReceiver(Lannister activity, boolean state) {
		this.activity = activity;
		this.enabled = state;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean setEnabled(boolean state) {
		boolean old = enabled;
		enabled = state;
		return old;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (!enabled) { return; }
		Log.d("MLReceiver", intent.toString());
		if (ACK.equals(intent.getAction())) {
			activity.addItem(intent.getStringExtra(MLService.STATUS));
		}
	}

	@Override
	public void ping() {
		Log.d("MLReceiver", activity.getPackageName() + " pinged");
	}
}
