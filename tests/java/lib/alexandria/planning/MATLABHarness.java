package lib.alexandria.planning;

/*
import lib.alexandria.functions.kernels.Kernel;
import lib.alexandria.functions.kernels.KernelType;
*/

import java.io.File;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import matlabcontrol.LoggingMatlabProxy;
import matlabcontrol.MatlabConnectionException;
import matlabcontrol.MatlabInvocationException;
import matlabcontrol.MatlabProxy;
import matlabcontrol.MatlabProxy.DisconnectionListener;
import matlabcontrol.MatlabProxyFactory;
import matlabcontrol.MatlabProxyFactoryOptions;
import matlabcontrol.extensions.MatlabTypeConverter;

public class MATLABHarness extends Harness implements DisconnectionListener {
	private static MatlabProxyFactoryOptions options;
	private static MatlabProxyFactory factory;
	static {
		//LoggingMatlabProxy.showInConsoleHandler();
		options = new MatlabProxyFactoryOptions.Builder()
				.setMatlabLocation("/opt/bin/matlab")
				.setMatlabStartingDirectory(new File("."))
				//.setUsePreviouslyControlledSession(true)
				.setLogFile("log/MATLAB.log")
				.setProxyTimeout(9001L * 2)
				.setHidden(true)
				.build();
		factory = new MatlabProxyFactory(options);
	}

	private MatlabProxy proxy;
	private boolean expected;
	private MatlabTypeConverter processor;

	public MATLABHarness(String label) throws MatlabConnectionException {
		super(label);
		proxy = new LoggingMatlabProxy(factory.getProxy());
		proxy.addDisconnectionListener(this);
		processor = new MatlabTypeConverter(proxy);
		expected = false;
	}

	public Future<Double> minimize(final Objective o) {
		return new FutureTask<Double>(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				return min(o);
			}
		});
	}

	private double min(final Objective o) throws MatlabInvocationException {
		String func = "@(x) (" + o.getExpression() + ")";
		proxy.eval("result = fminunc(" + func + ",10);");
		return ((double[]) proxy.getVariable("result"))[0];
	}

	public static void main(String... args) {
		try (MATLABHarness phil = new MATLABHarness("Phil")) {
			for (String s : args) {
				try /* (Kernel k = KernelType.GAUSS.getDefault()) */ {
					Objective objective = new Objective("fx");
					objective.setExpression(s);
					//Future<Double> result = phil.minimize(objective);
					//System.out.println("min(" + s + ") = " + result.get());
					System.out.println("min(" + s + ") = " + phil.min(objective));
					// phil.addUpperObjective(k);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (MatlabInvocationException mie) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void proxyDisconnected(MatlabProxy proxy) {
		if (!expected) {
			System.err.println("Hey! What gives?!");
		}
	}

	@Override
	public void close() throws InterruptedException, MatlabInvocationException {
		if (proxy.isConnected()) {
			expected = true;
			proxy.exit();
			//proxy.disconnect();
		}
	}
}
