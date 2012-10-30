package lib.alexandria.pipeline;

import java.util.concurrent.ThreadFactory;

import static lib.alexandria.Generate.LOG;
import static lib.alexandria.Generate.randomString;

import static lib.alexandria.ModelConstants.DEFAULT_TASK_PRIORITY;

import lib.alexandria.naming.LabelledEntity;

public class Spool extends LabelledEntity implements ThreadFactory {
	public Spool(String prefix) {
		super(prefix);
	}
	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setName(this.plus(randomString()));
		t.setPriority(DEFAULT_TASK_PRIORITY);
		LOG.v(this, "made thread named: " + t.getName());
		return t;
	}

	public Spool given(String prefix) {
		return new Spool(this.plus(prefix));
	}
}
