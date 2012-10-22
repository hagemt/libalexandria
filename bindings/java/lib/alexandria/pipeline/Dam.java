package lib.alexandria.pipeline;

import java.util.concurrent.Semaphore;

/**
 * An important locking mechanism from which native keys are managed.
 * TODO is there a way to get more than sixty-four? bitset DS?
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class Dam {
	private static final Semaphore refs;
	private static long flags;
	static {
		refs = new Semaphore(Long.SIZE);
		flags = ~(0L);
	}
	
	public static final synchronized long get() throws InterruptedException {
		refs.acquire();
		/* If we acquired through the semaphore, one bit must be available */
		assert(flags != 0L);
		long key = Long.highestOneBit(flags);
		/* Turn it off and return it */
		flags &= ~key;
		return key;
	}
	
	public static final synchronized boolean put(int key) {
		long new_flags = flags ^ key;
		/*
		 * !!! If the key changes flags, then it is a valid return.
		 * So, if it isn't the same flags, then we do
		 */
		if (new_flags != flags) {
			flags = new_flags;
			refs.release();
			return true;
		}
		return false;
	}
}
