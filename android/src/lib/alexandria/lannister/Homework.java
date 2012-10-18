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

import java.util.concurrent.FutureTask;

import lib.alexandria.LearningModel;
import lib.alexandria.supervised.KSVM;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Homework extends FutureTask<Void> implements Parcelable, Pingable {
	public static final String UUID = "library.alexandria.action.homework";
	
	private final int mark;
	private final LearningModel model;
	
	public static final Parcelable.Creator<Homework> CREATOR = new Parcelable.Creator<Homework>() {
		public Homework createFromParcel(Parcel in) {
			int mark = in.readInt();
			LearningModel model = new KSVM(in.readString());
			return new Homework(mark, model);
		}
		public Homework[] newArray(int size) {
			return new Homework[size];
		}
	};
	
	private Homework(int mark, final LearningModel model) {
		super(new Runnable() {
			@Override
			public void run() {
				if (model != null) {
					model.benchmark();
				}
			}
		}, null);
		this.mark = mark;
		this.model = model;
	}
	
	public Homework(LearningModel model) {
		this(0, model);
	}
	
	public LearningModel getModel() {
		return model;
	}

	@Override
	public int describeContents() {
		return mark;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(toString());
	}

	@Override
	public void ping() {
		Log.v("Homework", toString() + " pinged");
	}
	
	@Override
	public String toString() {
		return (model != null) ? "Model: " + model.getLabel() : "Invalid model";
	}
}
