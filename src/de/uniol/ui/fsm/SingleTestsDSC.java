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
import de.uniol.ui.fsm.projects.fridge.dsc.Extension_DSC;
import de.uniol.ui.fsm.projects.fridge.dsc_random.Extension_DSC_random;
import de.uniol.ui.fsm.projects.fridge.dsc_stateful_fullwidth.Extension_DSC_stateful_fullwidth;
import de.uniol.ui.fsm.ui.LineChartDialog;
import de.uniol.ui.fsm.ui.ProgressComposite;
import de.uniol.ui.fsm.ui.StepChartDialog;
import de.uniol.ui.fsm.ui.TimeSeriesMultiMeanCollector;

/**
 * This class was used to produce some single loadcurves with DSC signal. Random
 * numbers are created using a {@link Congruential} rng seeded by
 * {@link Math#random()}.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
public class SingleTestsDSC {

	/** Simulation length */
	private final static long steps = 60 * 60 * 20;
	
	private static long dsc_load = 60 * 330;
	private final static double dsc_spread = 0.0;
	
	public static void main(String[] args) {
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
		conf.POPULATION_SIZE = 1;
		conf.variate_A = VARIATE.NONE;
		conf.variate_eta = VARIATE.NONE;
		conf.variate_mc = VARIATE.NONE;
		conf.variate_TO = VARIATE.NONE;
		conf.variate_Tcurrent = VARIATE.NONE;
		conf.ACTIVE_AT_START_PROPABILITY  = 1.0;
		TimeSeriesMultiMeanCollector[] results;
//		// 1) Base
//		results = run_Base(conf);
//		lcd.addSeries("base", results[0].getResults());
//		scd.addSeries("base", results[1].getResults());
		// 0) DSC-stateful-full-30
//		dsc_load = 60 * 30;
//		results = run_DSC_load_stateful_full(conf);
		results = run_DSC_unload_random(conf);
		lcd.addSeries("stateful_random(0)", results[0].getResults());
		scd.addSeries("stateful_random(0)", results[1].getResults());
		// 2) DSC-stateful-full-30
//		dsc_load = 60 * 30;
//		results = run_DSC_load_stateful_full(conf);
		results = run_DSC_unload_random(conf);
		lcd.addSeries("stateful_random(1)", results[0].getResults());
		scd.addSeries("stateful_random(1)", results[1].getResults());
		// 3) DSC-stateful-full-60
//		dsc_load = 60 * 60;
//		results = run_DSC_load_stateful_full(conf);
		results = run_DSC_unload_random(conf);
		lcd.addSeries("stateful_random(2)", results[0].getResults());
		scd.addSeries("stateful_random(2)", results[1].getResults());
		// 4) DSC-stateful-full-90
//		dsc_load = 60 * 90;
//		results = run_DSC_load_stateful_full(conf);
		results = run_DSC_unload_random(conf);
		lcd.addSeries("stateful_random(3)", results[0].getResults());
		scd.addSeries("stateful_random(3)", results[1].getResults());
		// 5) DSC-stateful-full-120
//		dsc_load = 60 * 120;
//		results = run_DSC_load_stateful_full(conf);
		results = run_DSC_unload_random(conf);
		lcd.addSeries("stateful_random(4)", results[0].getResults());
		scd.addSeries("stateful_random(4)", results[1].getResults());
//		results = run_DSC_unload_random(conf);
//		lcd.addSeries("stateful_random(5)", results[0].getResults());
//		scd.addSeries("stateful_random(5)", results[1].getResults());
//		results = run_DSC_unload_random(conf);
//		lcd.addSeries("stateful_random(6)", results[0].getResults());
//		scd.addSeries("stateful_random(6)", results[1].getResults());
//		results = run_DSC_unload_random(conf);
//		lcd.addSeries("stateful_random(7)", results[0].getResults());
//		scd.addSeries("stateful_random(7)", results[1].getResults());
//		results = run_DSC_unload_random(conf);
//		lcd.addSeries("stateful_random(8)", results[0].getResults());
//		scd.addSeries("stateful_random(8)", results[1].getResults());
//		results = run_DSC_unload_random(conf);
//		lcd.addSeries("stateful_random(9)", results[0].getResults());
//		scd.addSeries("stateful_random(9)", results[1].getResults());

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