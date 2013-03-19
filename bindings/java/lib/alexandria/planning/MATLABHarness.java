package lib.alexandria.planning;

/*
import lib.alexandria.functions.kernels.Kernel;
import lib.alexandria.functions.kernels.KernelType;
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import java.util.Date;
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
import matlabcontrol.extensions.MatlabNumericArray;
//import matlabcontrol.extensions.MatlabTypeConverter;
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
				.setProxyTimeout(60000L)
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
	
	public String result(String s) {
		if (s == null || s == "exit") {
			return "Cannot exit MATLAB...";
		}
		StringBuilder sb = new StringBuilder("ans =\n");
		try {
			proxy.eval(s);
			MatlabNumericArray ans = processor.getNumericArray("ans");
			int dim = ans.getDimensions();
			if (dim == 2) {
				double[][] results = ans.getRealArray2D();
				for (double[] row : results) {
					sb.append("[\t");
					for (double element : row) {
						sb.append(element);
						sb.append(",\t");
					}
					sb.append("]\n");
				}
			}
		} catch (MatlabInvocationException e) {
			return sb.append(e.toString()).toString();
		}
		return sb.toString();
	}

	private boolean canProcess(String line) {
		if (line == null || line.equals("exit")) {
			return false;
		}
		try {
			proxy.eval(line);
			if (!line.trim().endsWith(";")) {
				MatlabNumericArray ans = processor.getNumericArray("ans");
				int dim = ans.getDimensions();
				System.out.println("Answer is a tensor of rank = " + dim);
				if (dim == 2) {
					double[][] results = ans.getRealArray2D();
					for (double[] row : results) {
						System.out.print("[");
						for (double element : row) {
							System.out.print(" " + element);
						}
						System.out.println(" ]");
					}
				}
			}
		} catch (MatlabInvocationException e) {
			System.err.println("Error processing '" + line + "'");
		}
		return true;
	}
	
	public static void main(String... args) {
		System.out.println("[" + new Date().toString() + "] connecting to MATLAB...");
		try (
				MATLABHarness phil = new MATLABHarness("Phil");
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			) {
			do { System.out.print(">> "); }
			while (phil.canProcess(reader.readLine()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("[" + new Date().toString() + "] connection closed");
		/*
		for (String arg : args) {
			try (MATLABHarness phil = new MATLABHarness("Phil")) {
				try (Kernel k = KernelType.GAUSS.getDefault()) {
					Objective objective = new Objective("fx");
					objective.setExpression(arg);
					//Future<Double> result = phil.minimize(objective);
					//System.out.println("min(" + s + ") = " + result.get());
					System.out.println("min(" + arg + ") = " + phil.min(objective));
					//phil.addUpperObjective(k);
				} catch (MatlabInvocationException mie) {
					System.out.println("Cannot minimize: " + arg);
				} catch (RuntimeException re) {
					re.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		*/
	}

	@Override
	public void proxyDisconnected(MatlabProxy proxy) {
		if (!expected) {
			System.err.println("[MATLABHarness] Hey! What gives?!");
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
	
	public boolean isConnected() {
		return proxy.isConnected();
	}
}
