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
