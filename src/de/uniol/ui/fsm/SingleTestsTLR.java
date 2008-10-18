package de.uniol.ui.fsm;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import simkit.random.BernoulliVariate;
import simkit.random.Congruential;
import simkit.random.NormalVariate;
import simkit.random.RandomVariate;
import simkit.random.UniformVariate;
import de.uniol.ui.fsm.projects.fridge.BaseController;
import de.uniol.ui.fsm.projects.fridge.Configuration;
import de.uniol.ui.fsm.projects.fridge.Fridge;
import de.uniol.ui.fsm.projects.fridge.Configuration.VARIATE;
import de.uniol.ui.fsm.projects.fridge.tlr.Extension_TLR;
import de.uniol.ui.fsm.projects.fridge.tlr_extension_random.TLR_extension_random;
import de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful.TLR_extension_stateful;
import de.uniol.ui.fsm.ui.LineChartDialog;
import de.uniol.ui.fsm.ui.ProgressComposite;
import de.uniol.ui.fsm.ui.StepChartDialog;
import de.uniol.ui.fsm.ui.TimeSeriesMultiMeanCollector;

public class SingleTestsTLR {

	/** Simulation length */
	private final static long steps = 60 * 60 * 8;

	private final static long tlr_t_notify = 60 * 60;
	private final static double tlr_tau_preload = 30.0;
	private final static double tlr_tau_reduce = 90.0;
	
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		shell.setText("FSM Simulation results");

		// Prepare charts
		LineChartDialog lcd = new LineChartDialog(shell,
				"Temperature progress", "Time (h)", "Temperature (°C)", "min",
				"°C", 3.0, 8.0);
		StepChartDialog scd = new StepChartDialog(shell, "Load progress",
				"Time (h)", "Load (W)", "min", "W", 0.0, 70.0);

		// Fill charts
		Configuration conf = new Configuration();
		conf.POPULATION_SIZE = 1;
		conf.variate_A = VARIATE.NONE;
		conf.variate_eta = VARIATE.NONE;
		conf.variate_mc = VARIATE.NONE;
		conf.variate_TO = VARIATE.NONE;
		conf.variate_Tcurrent = VARIATE.NONE;
		conf.ACTIVE_AT_START_PROPABILITY  = 1.0;
		TimeSeriesMultiMeanCollector[] results;
		// 1) BaseController
		results = run_Base(conf);
		lcd.addSeries("BaseController", results[0].getResults());
		scd.addSeries("BaseController", results[1].getResults());
		// 2) Extension_TLR
		results = run_TLR(conf);
		lcd.addSeries("Extension_TLR", results[0].getResults());
		scd.addSeries("Extension_TLR", results[1].getResults());
		// 3) Extension_TLR-stateful
		results = run_TLR_stateful(conf);
		lcd.addSeries("Extension_TLR-stateful", results[0].getResults());
		scd.addSeries("Extension_TLR-stateful", results[1].getResults());
		// 1) Extension_TLR-random
		results = run_TLR_random(conf);
		lcd.addSeries("Extension_TLR-random", results[0].getResults());
		scd.addSeries("Extension_TLR-random", results[1].getResults());
//
//		results = run_TLR_random(conf);
//		lcd.addSeries("TLR-random-(1)", results[0].getResults());
//		scd.addSeries("TLR-random-(1)", results[1].getResults());
//
//		results = run_TLR_random(conf);
//		lcd.addSeries("TLR-random-(2)", results[0].getResults());
//		scd.addSeries("TLR-random-(2)", results[1].getResults());
//
//		results = run_TLR_random(conf);
//		lcd.addSeries("TLR-random-(3)", results[0].getResults());
//		scd.addSeries("TLR-random-(3)", results[1].getResults());
//
//		results = run_TLR_random(conf);
//		lcd.addSeries("TLR-random-(4)", results[0].getResults());
//		scd.addSeries("TLR-random-(4)", results[1].getResults());
//
//		results = run_TLR_random(conf);
//		lcd.addSeries("TLR-random-(5)", results[0].getResults());
//		scd.addSeries("TLR-random-(5)", results[1].getResults());

		// Finish charts
		lcd.create();
		scd.create();

		// Open shell
		shell.setSize(900, 800);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}
	
	private static TimeSeriesMultiMeanCollector[] run_Base(Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					
					pc.setProgress((int) ((l + 1L) * 100L / steps));
				}
			}
		};
		long start = System.currentTimeMillis();
		sim.start();
		pc.open();
		long dur = System.currentTimeMillis() - start;
		long min = dur / 60000l;
		long sec = (dur % 60000l) / 1000l;
		long ms = (dur % 60000l) % 1000l;
		System.out.println(steps + " steps finished in " + min + "m" + sec
				+ "s" + ms + "ms");

		return new TimeSeriesMultiMeanCollector[] { tempCol, loadCol };
	}
	
	private static TimeSeriesMultiMeanCollector[] run_TLR(Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_TLR> tlrs = new ArrayList<Extension_TLR>();
		for (BaseController bc : bcs) {
			tlrs.add(new Extension_TLR(bc));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_TLR tlr : tlrs) {
						tlr.clock();
						if (l == tlr_t_notify) {
							tlr.signal(Extension_TLR.EV_REDUCE, tlr.getIdle(),
									tlr_tau_preload, tlr_tau_reduce);
							tlr.dispatchSignals(tlr.getIdle());
						}
					}
					
					pc.setProgress((int) ((l + 1L) * 100L / steps));
				}
			}
		};
		long start = System.currentTimeMillis();
		sim.start();
		pc.open();
		long dur = System.currentTimeMillis() - start;
		long min = dur / 60000l;
		long sec = (dur % 60000l) / 1000l;
		long ms = (dur % 60000l) % 1000l;
		System.out.println(steps + " steps finished in " + min + "m" + sec
				+ "s" + ms + "ms");

		return new TimeSeriesMultiMeanCollector[] { tempCol, loadCol };
	}
	
	private static TimeSeriesMultiMeanCollector[] run_TLR_stateful(Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_TLR> tlrs = new ArrayList<Extension_TLR>();
		final ArrayList<TLR_extension_stateful> exts = new ArrayList<TLR_extension_stateful>();
		for (BaseController bc : bcs) {
			Extension_TLR tlr = new Extension_TLR(bc);
			tlrs.add(tlr);
			exts.add(new TLR_extension_stateful(tlr));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_TLR tlr : tlrs) {
						tlr.clock();
						if (l == tlr_t_notify) {
							tlr.signal(Extension_TLR.EV_REDUCE, tlr.getIdle(),
									tlr_tau_preload, tlr_tau_reduce);
							tlr.dispatchSignals(tlr.getIdle());
						}
					}
					for (TLR_extension_stateful ext : exts) {
						ext.clock();
					}
					
					pc.setProgress((int) ((l + 1L) * 100L / steps));
				}
			}
		};
		long start = System.currentTimeMillis();
		sim.start();
		pc.open();
		long dur = System.currentTimeMillis() - start;
		long min = dur / 60000l;
		long sec = (dur % 60000l) / 1000l;
		long ms = (dur % 60000l) % 1000l;
		System.out.println(steps + " steps finished in " + min + "m" + sec
				+ "s" + ms + "ms");

		return new TimeSeriesMultiMeanCollector[] { tempCol, loadCol };
	}
	
	private static TimeSeriesMultiMeanCollector[] run_TLR_random(Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_TLR> tlrs = new ArrayList<Extension_TLR>();
		final ArrayList<TLR_extension_random> exts = new ArrayList<TLR_extension_random>();
		for (BaseController bc : bcs) {
			Extension_TLR tlr = new Extension_TLR(bc);
			tlrs.add(tlr);
			exts.add(new TLR_extension_random(tlr));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_TLR tlr : tlrs) {
						tlr.clock();
						if (l == tlr_t_notify) {
							tlr.signal(Extension_TLR.EV_REDUCE, tlr.getIdle(),
									tlr_tau_preload, tlr_tau_reduce);
							tlr.dispatchSignals(tlr.getIdle());
						}
					}
					for (TLR_extension_random ext : exts) {
						ext.clock();
					}
					
					pc.setProgress((int) ((l + 1L) * 100L / steps));
				}
			}
		};
		long start = System.currentTimeMillis();
		sim.start();
		pc.open();
		long dur = System.currentTimeMillis() - start;
		long min = dur / 60000l;
		long sec = (dur % 60000l) / 1000l;
		long ms = (dur % 60000l) % 1000l;
		System.out.println(steps + " steps finished in " + min + "m" + sec
				+ "s" + ms + "ms");

		return new TimeSeriesMultiMeanCollector[] { tempCol, loadCol };
	}
	
	private static ArrayList<BaseController> createBaseControllers(
			Configuration conf, TimeSeriesMultiMeanCollector tempCol,
			TimeSeriesMultiMeanCollector loadCol) {
		// Create distinct random variates
		RandomVariate tVariate = null;
		switch (conf.variate_Tcurrent) {
		case UNIFORM: {
			tVariate = new UniformVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			tVariate.setRandomNumber(cong);
			tVariate.setParameters(conf.variate_Tcurrent_min,
					conf.variate_Tcurrent_max);
			break;
		}
		case NORMAL: {
			tVariate = new NormalVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			tVariate.setRandomNumber(cong);
			tVariate.setParameters(conf.variate_Tcurrent_default,
					conf.variate_Tcurrent_sdev);
			break;
		}
		default: {
			tVariate = null;
			break;
		}
		}
		RandomVariate mcVariate = null;
		switch (conf.variate_mc) {
		case UNIFORM: {
			mcVariate = new UniformVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			mcVariate.setRandomNumber(cong);
			mcVariate.setParameters(conf.variate_mc_min, conf.variate_mc_max);
			break;
		}
		case NORMAL: {
			mcVariate = new NormalVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			mcVariate.setRandomNumber(cong);
			mcVariate.setParameters(conf.variate_mc_default,
					conf.variate_mc_sdev);
			break;
		}
		default: {
			mcVariate = null;
			break;
		}
		}
		RandomVariate aVariate = null;
		switch (conf.variate_A) {
		case UNIFORM: {
			aVariate = new UniformVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			aVariate.setRandomNumber(cong);
			aVariate.setParameters(conf.variate_A_min, conf.variate_A_max);
			break;
		}
		case NORMAL: {
			aVariate = new NormalVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			aVariate.setRandomNumber(cong);
			aVariate.setParameters(conf.variate_A_default, conf.variate_A_sdev);
			break;
		}
		default: {
			aVariate = null;
			break;
		}
		}

		RandomVariate toVariate = null;
		switch (conf.variate_TO) {
		case UNIFORM: {
			toVariate = new UniformVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			toVariate.setRandomNumber(cong);
			toVariate.setParameters(conf.variate_TO_min, conf.variate_TO_max);
			break;
		}
		case NORMAL: {
			toVariate = new NormalVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			toVariate.setRandomNumber(cong);
			toVariate.setParameters(conf.variate_TO_default,
					conf.variate_TO_sdev);
			break;
		}
		default: {
			toVariate = null;
			break;
		}
		}
		RandomVariate etaVariate = null;
		switch (conf.variate_eta) {
		case UNIFORM: {
			etaVariate = new UniformVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			etaVariate.setRandomNumber(cong);
			etaVariate
					.setParameters(conf.variate_eta_min, conf.variate_eta_max);
			break;
		}
		case NORMAL: {
			etaVariate = new NormalVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			etaVariate.setRandomNumber(cong);
			etaVariate.setParameters(conf.variate_eta_default,
					conf.variate_eta_sdev);
			break;
		}
		default: {
			etaVariate = null;
			break;
		}
		}
		// Create [0|1] variate for starting states
		RandomVariate activityAtStartVariate = new BernoulliVariate();
		activityAtStartVariate.setParameters(conf.ACTIVE_AT_START_PROPABILITY);
		// Create fridges
		ArrayList<BaseController> bcs = new ArrayList<BaseController>();
		for (int i = 0; i < conf.POPULATION_SIZE; i++) {
			// Make (ACTIVE_AT_START_PROPABILITY*100)% of the fridges active
			BaseController bc = new BaseController(activityAtStartVariate
					.generate() > 0, tempCol, loadCol);
			// Configure fridge device
			Fridge f = bc.getFridge();
			if (conf.variate_Tcurrent != Configuration.VARIATE.NONE) {
				f.generate_temperature(tVariate, bc.getTmin(), bc.getTmax());
			}
			if (conf.variate_mc != Configuration.VARIATE.NONE) {
				f.generate_mC(mcVariate);
			}
			if (conf.variate_A != Configuration.VARIATE.NONE) {
				f.generate_a(aVariate);
			}
			if (conf.variate_TO != Configuration.VARIATE.NONE) {
				f.generate_tSurround(toVariate);
			}
			if (conf.variate_eta != Configuration.VARIATE.NONE) {
				f.generate_eta(etaVariate);
			}
			// Add to list
			bcs.add(bc);
		}

		// Return
		return bcs;
	}
}