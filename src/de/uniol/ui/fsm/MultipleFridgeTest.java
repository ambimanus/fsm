/*
 * FSM Copyright (C) 2008 Christian Hinrichs
 * 
 * FSM is copyright under the GNU General Public License.
 * 
 * This file is part of FSM.
 * 
 * FSM is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * AdaptiveFridge is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FSM.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.uniol.ui.fsm;

import java.io.File;
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
import de.uniol.ui.fsm.projects.fridge.dsc.Extension_DSC;
import de.uniol.ui.fsm.projects.fridge.dsc_random.Extension_DSC_random;
import de.uniol.ui.fsm.projects.fridge.dsc_stateful.Extension_DSC_stateful;
import de.uniol.ui.fsm.projects.fridge.dsc_stateful_fullwidth.Extension_DSC_stateful_fullwidth;
import de.uniol.ui.fsm.projects.fridge.tlr.Extension_TLR;
import de.uniol.ui.fsm.projects.fridge.tlr_extension_random.TLR_extension_random;
import de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful.TLR_extension_stateful;
import de.uniol.ui.fsm.ui.LineChartDialog;
import de.uniol.ui.fsm.ui.ProgressComposite;
import de.uniol.ui.fsm.ui.StepChartDialog;
import de.uniol.ui.fsm.ui.TimeSeriesMultiMeanCollector;
import de.uniol.ui.fsm.util.ResultWriter;

/**
 * This is the main class of the FSM simulator which was used to produce the
 * results of the linear error analysis in the thesis. Random numbers are
 * created using a {@link Congruential} rng seeded by {@link Math#random()}.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
public class MultipleFridgeTest {

	/** Simulation length */
	private final static long steps = 60 * 60 * 20;
	
	private final static long dsc_load = 60 * 60 * 6;
	private final static double dsc_spread = 10.0;

	private final static long tlr_t_notify = 60 * 60 * 6;
	private final static double tlr_tau_preload = 30.0;
	private final static double tlr_tau_reduce = 90.0;
	
	private static File file = new File(System.getProperty("user.dir")
			+ File.separator + "data" + File.separator + "out.csv");

	public static void main(String[] args) {
//		test_DSC_load(true);
//		test_DSC_unload(true);
//		test_TLR(true);
		write();
	}
	
	private static void write() {
		Configuration conf = new Configuration();
		conf.POPULATION_SIZE = 5000;
		conf.variate_Tcurrent = Configuration.VARIATE.UNIFORM;
		conf.variate_mc = Configuration.VARIATE.NORMAL;
		conf.variate_A = Configuration.VARIATE.NORMAL;
		conf.variate_TO = Configuration.VARIATE.NORMAL;
		conf.variate_eta = Configuration.VARIATE.NORMAL;
		conf.title = "event simulation";
		
		TimeSeriesMultiMeanCollector[] results;
		results = run_TLR_random(conf);
		ResultWriter.writeResultsSimple(results[0].getResults(), results[1]
				.getResults(), file);
	}
	
	private static void test_DSC_load(boolean block) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		shell.setText("FSM Simulation results");

		// Prepare charts
		LineChartDialog lcd = new LineChartDialog(shell,
				"Temperature progress", "Time (h)", "Temperature (�C)", "min",
				"�C", 3.0, 8.0);
		StepChartDialog scd = new StepChartDialog(shell, "Load progress",
				"Time (h)", "Load (W)", "min", "W", 0.0, 70.0);

		// Fill charts
		Configuration conf = new Configuration();
		TimeSeriesMultiMeanCollector[] results;
//		// 1) BaseController
//		results = run_Base(conf);
//		lcd.addSeries("BaseController", results[0].getResults());
//		scd.addSeries("BaseController", results[1].getResults());
//		// 2) DSC
//		results = run_DSC_load(conf);
//		lcd.addSeries("Extension_DSC", results[0].getResults());
//		scd.addSeries("Extension_DSC", results[1].getResults());
		// 3) DSC-stateful-half
		results = run_DSC_load_stateful_half(conf);
		lcd.addSeries("Extension_DSC_stateful_half", results[0].getResults());
		scd.addSeries("Extension_DSC_stateful_half", results[1].getResults());
		// 4) DSC-stateful-full
		results = run_DSC_load_stateful_full(conf);
		lcd.addSeries("Extension_DSC_stateful_full", results[0].getResults());
		scd.addSeries("Extension_DSC_stateful_full", results[1].getResults());
		// 5) DSC-stateful-random
		results = run_DSC_load_random(conf);
		lcd.addSeries("Extension_DSC_random", results[0].getResults());
		scd.addSeries("Extension_DSC_random", results[1].getResults());

		// Finish charts
		lcd.create();
		scd.create();

		// Open shell
		shell.setSize(900, 800);
		shell.open();
		if (block) {
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		}
	}
	
	private static void test_DSC_unload(boolean block) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		shell.setText("FSM Simulation results");

		// Prepare charts
		LineChartDialog lcd = new LineChartDialog(shell,
				"Temperature progress", "Time (h)", "Temperature (�C)", "min",
				"�C", 3.0, 8.0);
		StepChartDialog scd = new StepChartDialog(shell, "Load progress",
				"Time (h)", "Load (W)", "min", "W", 0.0, 70.0);

		// Fill charts
		Configuration conf = new Configuration();
//		conf.POPULATION_SIZE = 1;
//		conf.variate_A = VARIATE.NONE;
//		conf.variate_eta = VARIATE.NONE;
//		conf.variate_mc = VARIATE.NONE;
//		conf.variate_TO = VARIATE.NONE;
//		conf.variate_Tcurrent = VARIATE.NONE;
//		conf.ACTIVE_AT_START_PROPABILITY  = 0.22;
		TimeSeriesMultiMeanCollector[] results;
//		// 1) BaseController
//		results = run_Base(conf);
//		lcd.addSeries("BaseController", results[0].getResults());
//		scd.addSeries("BaseController", results[1].getResults());
//		// 2) DSC
//		results = run_DSC_unload(conf);
//		lcd.addSeries("Extension_DSC", results[0].getResults());
//		scd.addSeries("Extension_DSC", results[1].getResults());
		// 3) DSC-stateful-half
		results = run_DSC_unload_stateful_half(conf);
		lcd.addSeries("Extension_DSC_stateful_half", results[0].getResults());
		scd.addSeries("Extension_DSC_stateful_half", results[1].getResults());
		// 4) DSC-stateful-full
		results = run_DSC_unload_stateful_full(conf);
		lcd.addSeries("Extension_DSC_stateful_full", results[0].getResults());
		scd.addSeries("Extension_DSC_stateful_full", results[1].getResults());
		// 5) DSC-stateful-random
		results = run_DSC_unload_random(conf);
		lcd.addSeries("Extension_DSC_random", results[0].getResults());
		scd.addSeries("Extension_DSC_random", results[1].getResults());

		// Finish charts
		lcd.create();
		scd.create();

		// Open shell
		shell.setSize(900, 800);
		shell.open();
		if (block) {
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		}
	}

	private static void test_TLR(boolean block) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		shell.setText("FSM Simulation results");

		// Prepare charts
		LineChartDialog lcd = new LineChartDialog(shell,
				"Temperature progress", "Time (h)", "Temperature (�C)", "min",
				"�C", 3.0, 8.0);
		StepChartDialog scd = new StepChartDialog(shell, "Load progress",
				"Time (h)", "Load (W)", "min", "W", 0.0, 70.0);

		// Fill charts
		Configuration conf = new Configuration();
		TimeSeriesMultiMeanCollector[] results;
//		// 1) BaseController
//		results = run_Base(conf);
//		lcd.addSeries("BaseController", results[0].getResults());
//		scd.addSeries("BaseController", results[1].getResults());
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

		// Finish charts
		lcd.create();
		scd.create();

		// Open shell
		shell.setSize(900, 800);
		shell.open();
		if (block) {
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
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
	
	private static TimeSeriesMultiMeanCollector[] run_DSC_load(Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_DSC> dscs = new ArrayList<Extension_DSC>();
		for (BaseController bc : bcs) {
			dscs.add(new Extension_DSC(bc));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_DSC dsc : dscs) {
						dsc.clock();
						if (l == dsc_load) {
							dsc.signal(Extension_DSC.EV_LOAD, dsc.getIdle(),
									dsc_spread);
							dsc.dispatchSignals(dsc.getIdle());
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
	
	private static TimeSeriesMultiMeanCollector[] run_DSC_load_stateful_half(
			Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_DSC_stateful> dscs = new ArrayList<Extension_DSC_stateful>();
		for (BaseController bc : bcs) {
			dscs.add(new Extension_DSC_stateful(bc));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_DSC_stateful dsc : dscs) {
						dsc.clock();
						if (l == dsc_load) {
							dsc.signal(Extension_DSC.EV_LOAD, dsc.getIdle(),
									dsc_spread);
							dsc.dispatchSignals(dsc.getIdle());
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
	
	private static TimeSeriesMultiMeanCollector[] run_DSC_load_stateful_full(
			Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_DSC_stateful_fullwidth> dscs = new ArrayList<Extension_DSC_stateful_fullwidth>();
		for (BaseController bc : bcs) {
			dscs.add(new Extension_DSC_stateful_fullwidth(bc));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_DSC_stateful_fullwidth dsc : dscs) {
						dsc.clock();
						if (l == dsc_load) {
							dsc.signal(Extension_DSC.EV_LOAD, dsc.getIdle(),
									dsc_spread);
							dsc.dispatchSignals(dsc.getIdle());
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
	
	private static TimeSeriesMultiMeanCollector[] run_DSC_load_random(
			Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_DSC_random> dscs = new ArrayList<Extension_DSC_random>();
		for (BaseController bc : bcs) {
			dscs.add(new Extension_DSC_random(bc));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_DSC_random dsc : dscs) {
						dsc.clock();
						if (l == dsc_load) {
							dsc.signal(Extension_DSC.EV_LOAD, dsc.getIdle(),
									dsc_spread);
							dsc.dispatchSignals(dsc.getIdle());
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
	
	private static TimeSeriesMultiMeanCollector[] run_DSC_unload(Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_DSC> dscs = new ArrayList<Extension_DSC>();
		for (BaseController bc : bcs) {
			dscs.add(new Extension_DSC(bc));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_DSC dsc : dscs) {
						dsc.clock();
						if (l == dsc_load) {
							dsc.signal(Extension_DSC.EV_UNLOAD, dsc.getIdle(),
									dsc_spread);
							dsc.dispatchSignals(dsc.getIdle());
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
	
	private static TimeSeriesMultiMeanCollector[] run_DSC_unload_stateful_half(
			Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_DSC_stateful> dscs = new ArrayList<Extension_DSC_stateful>();
		for (BaseController bc : bcs) {
			dscs.add(new Extension_DSC_stateful(bc));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_DSC_stateful dsc : dscs) {
						dsc.clock();
						if (l == dsc_load) {
							dsc.signal(Extension_DSC.EV_UNLOAD, dsc.getIdle(),
									dsc_spread);
							dsc.dispatchSignals(dsc.getIdle());
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
	
	private static TimeSeriesMultiMeanCollector[] run_DSC_unload_stateful_full(
			Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_DSC_stateful_fullwidth> dscs = new ArrayList<Extension_DSC_stateful_fullwidth>();
		for (BaseController bc : bcs) {
			dscs.add(new Extension_DSC_stateful_fullwidth(bc));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_DSC_stateful_fullwidth dsc : dscs) {
						dsc.clock();
						if (l == dsc_load) {
							dsc.signal(Extension_DSC.EV_UNLOAD, dsc.getIdle(),
									dsc_spread);
							dsc.dispatchSignals(dsc.getIdle());
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
	
	private static TimeSeriesMultiMeanCollector[] run_DSC_unload_random(
			Configuration conf) {
		TimeSeriesMultiMeanCollector tempCol = new TimeSeriesMultiMeanCollector();
		TimeSeriesMultiMeanCollector loadCol = new TimeSeriesMultiMeanCollector();
		final ArrayList<BaseController> bcs = createBaseControllers(conf,
				tempCol, loadCol);
		final ArrayList<Extension_DSC_random> dscs = new ArrayList<Extension_DSC_random>();
		for (BaseController bc : bcs) {
			dscs.add(new Extension_DSC_random(bc));
		}
		final ProgressComposite pc = new ProgressComposite();
		Thread sim = new Thread() {
			public void run() {
				for (long l = 0L; l < steps; l++) {
					for (BaseController bc : bcs) {
						bc.clock();
					}
					for (Extension_DSC_random dsc : dscs) {
						dsc.clock();
						if (l == dsc_load) {
							dsc.signal(Extension_DSC.EV_UNLOAD, dsc.getIdle(),
									dsc_spread);
							dsc.dispatchSignals(dsc.getIdle());
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
			cong.setSeed(conf.variate_Tcurrent_seed);
			tVariate.setRandomNumber(cong);
			tVariate.setParameters(conf.variate_Tcurrent_min,
					conf.variate_Tcurrent_max);
			break;
		}
		case NORMAL: {
			tVariate = new NormalVariate();
			Congruential cong = new Congruential();
			cong.setSeed(conf.variate_Tcurrent_seed);
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
			cong.setSeed(conf.variate_mc_seed);
			mcVariate.setRandomNumber(cong);
			mcVariate.setParameters(conf.variate_mc_min, conf.variate_mc_max);
			break;
		}
		case NORMAL: {
			mcVariate = new NormalVariate();
			Congruential cong = new Congruential();
			cong.setSeed(conf.variate_mc_seed);
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
			cong.setSeed(conf.variate_A_seed);
			aVariate.setRandomNumber(cong);
			aVariate.setParameters(conf.variate_A_min, conf.variate_A_max);
			break;
		}
		case NORMAL: {
			aVariate = new NormalVariate();
			Congruential cong = new Congruential();
			cong.setSeed(conf.variate_A_seed);
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
			cong.setSeed(conf.variate_TO_seed);
			toVariate.setRandomNumber(cong);
			toVariate.setParameters(conf.variate_TO_min, conf.variate_TO_max);
			break;
		}
		case NORMAL: {
			toVariate = new NormalVariate();
			Congruential cong = new Congruential();
			cong.setSeed(conf.variate_TO_seed);
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
			cong.setSeed(conf.variate_eta_seed);
			etaVariate.setRandomNumber(cong);
			etaVariate
					.setParameters(conf.variate_eta_min, conf.variate_eta_max);
			break;
		}
		case NORMAL: {
			etaVariate = new NormalVariate();
			Congruential cong = new Congruential();
			cong.setSeed(conf.variate_eta_seed);
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