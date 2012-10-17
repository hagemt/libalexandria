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
