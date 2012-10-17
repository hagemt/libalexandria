package lib.alexandria;

import java.util.concurrent.FutureTask;

import libalexandria.LearningModel;
import libalexandria.supervised.KSVM;

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
