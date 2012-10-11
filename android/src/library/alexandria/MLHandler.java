package library.alexandria;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MLHandler extends Handler {
	private final Pingable target;
	
	public MLHandler(Pingable target) {
		this.target = target;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		Log.d("MLHandler", msg.toString());
		target.ping();
	}
}
