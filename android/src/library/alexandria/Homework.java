package library.alexandria;

import java.util.concurrent.FutureTask;

import libalexandria.LearningModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Homework extends FutureTask<Void> implements Parcelable, Pingable {
	public static final String UUID = "library.alexandria.action.homework";
	
	private final LearningModel model;
	
	public Homework(final LearningModel model) {
		super(new Runnable() {
			@Override
			public void run() {
				if (model != null) {
					model.benchmark();
				}
			}
		}, null);
		this.model = model;
	}
	
	public LearningModel getModel() {
		return model;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ping() {
		Log.v("Homework", model.getLabel() + " pinged");
	}
	
	@Override
	public String toString() {
		return "Benchmark: " + model.getLabel();
	}
}
