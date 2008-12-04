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
 * FSM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FSM.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.uniol.ui.fsm;

/**
 * This class was used to produce the single controller validation results in
 * the thesis.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 * @deprecated Replaced by {@link MultipleFridgeTest}.
 */
public class FridgeTest {

//	/** Simulation length */
//	private final static long steps = 60 * 60 * 8;
//	/** Simulation speed, min=1, max=1000 */
//	private final static double speed = 1000.0;
//
//	private final static long dsc_load = 60 * 90;
//	private final static long dsc_unload = 60 * 170;
//	private final static double dsc_spread = 10.0;
//	
//	private final static long tlr_t_notify = 60 * 60;
//	private final static double tlr_tau_preload = 30.0;
//	private final static double tlr_tau_reduce = 90.0;

	public static void main(String[] args) {
//		test_DSC_load(true);
//		test_DSC_unload(true);
//		test_TLR(true);
	}
	
//	private static void test_DSC_load(boolean block) {
//		Display display = Display.getDefault();
//		Shell shell = new Shell(display);
//		shell.setLayout(new FillLayout(SWT.VERTICAL));
//		shell.setText("FSM Simulation results");
//
//		// Prepare charts
//		LineChartDialog lcd = new LineChartDialog(shell,
//				"Temperature progress", "Time (h)", "Temperature (�C)", "min",
//				"�C", 3.0, 8.0);
//		StepChartDialog scd = new StepChartDialog(shell, "Load progress",
//				"Time (h)", "Load (W)", "min", "W", 0.0, 70.0);
//
//		// Fill charts
//		BaseController bc;
//		// 1) BaseController
//		bc = run_Base();
//		lcd.addSeries("BaseController", bc.getFridge().getTempCol()
//				.getResults());
//		scd.addSeries("BaseController", bc.getFridge().getLoadCol()
//				.getResults());
////		// 2) DSC (load)
////		bc = run_DSC_load();
////		lcd.addSeries("Extension_DSC (load)", bc.getFridge().getTempCol()
////				.getResults());
////		scd.addSeries("Extension_DSC (load)", bc.getFridge().getLoadCol()
////				.getResults());
//		// 3) DSC_stateful (load)
//		bc = run_DSC_load_stateful();
//		lcd.addSeries("Extension_DSC_stateful (load)", bc.getFridge()
//				.getTempCol().getResults());
//		scd.addSeries("Extension_DSC_stateful (load)", bc.getFridge()
//				.getLoadCol().getResults());
//		// 4) DSC_stateful_fullwidth (load)
//		bc = run_DSC_load_stateful_fullwidth();
//		lcd.addSeries("Extension_DSC_stateful_fullwidth (load)", bc.getFridge()
//				.getTempCol().getResults());
//		scd.addSeries("Extension_DSC_stateful_fullwidth (load)", bc.getFridge()
//				.getLoadCol().getResults());
////		// 5) DSC_random (load
////		bc = run_DSC_load_random();
////		lcd.addSeries("Extension_DSC_random (load)", bc.getFridge()
////				.getTempCol().getResults());
////		scd.addSeries("Extension_DSC_random (load)", bc.getFridge()
////				.getLoadCol().getResults());
//
//		// Finish charts
//		lcd.create();
//		scd.create();
//
//		// Open shell
//		shell.setSize(900, 800);
//		shell.open();
//		if (block) {
//			while (!shell.isDisposed()) {
//				if (!display.readAndDispatch())
//					display.sleep();
//			}
//		}
//	}
//	
//	private static void test_DSC_unload(boolean block) {
//		Display display = Display.getDefault();
//		Shell shell = new Shell(display);
//		shell.setLayout(new FillLayout(SWT.VERTICAL));
//		shell.setText("FSM Simulation results");
//
//		// Prepare charts
//		LineChartDialog lcd = new LineChartDialog(shell,
//				"Temperature progress", "Time (h)", "Temperature (�C)", "min",
//				"�C", 3.0, 8.0);
//		StepChartDialog scd = new StepChartDialog(shell, "Load progress",
//				"Time (h)", "Load (W)", "min", "W", 0.0, 70.0);
//
//		// Fill charts
//		BaseController bc;
//		// 1) BaseController
//		bc = run_Base();
//		lcd.addSeries("BaseController", bc.getFridge().getTempCol()
//				.getResults());
//		scd.addSeries("BaseController", bc.getFridge().getLoadCol()
//				.getResults());
//		// 2) DSC (load)
//		bc = run_DSC_unload();
//		lcd.addSeries("Extension_DSC (unload)", bc.getFridge().getTempCol()
//				.getResults());
//		scd.addSeries("Extension_DSC (unload)", bc.getFridge().getLoadCol()
//				.getResults());
//		// 3) DSC_stateful (load)
//		bc = run_DSC_unload_stateful();
//		lcd.addSeries("Extension_DSC_stateful (unload)", bc.getFridge()
//				.getTempCol().getResults());
//		scd.addSeries("Extension_DSC_stateful (unload)", bc.getFridge()
//				.getLoadCol().getResults());
//		// 4) DSC_stateful_fullwidth (load)
//		bc = run_DSC_unload_stateful_fullwidth();
//		lcd.addSeries("Extension_DSC_stateful_fullwidth (unload)", bc.getFridge()
//				.getTempCol().getResults());
//		scd.addSeries("Extension_DSC_stateful_fullwidth (unload)", bc.getFridge()
//				.getLoadCol().getResults());
//		// 5) DSC_random (load
//		bc = run_DSC_unload_random();
//		lcd.addSeries("Extension_DSC_random (unload)", bc.getFridge()
//				.getTempCol().getResults());
//		scd.addSeries("Extension_DSC_random (unload)", bc.getFridge()
//				.getLoadCol().getResults());
//
//		// Finish charts
//		lcd.create();
//		scd.create();
//
//		// Open shell
//		shell.setSize(900, 800);
//		shell.open();
//		if (block) {
//			while (!shell.isDisposed()) {
//				if (!display.readAndDispatch())
//					display.sleep();
//			}
//		}
//	}
//	
//	private static void test_TLR(boolean block) {
//		Display display = Display.getDefault();
//		Shell shell = new Shell(display);
//		shell.setLayout(new FillLayout(SWT.VERTICAL));
//		shell.setText("FSM Simulation results");
//
//		// Prepare charts
//		LineChartDialog lcd = new LineChartDialog(shell,
//				"Temperature progress", "Time (h)", "Temperature (�C)", "min",
//				"�C", 3.0, 8.0);
//		StepChartDialog scd = new StepChartDialog(shell, "Load progress",
//				"Time (h)", "Load (W)", "min", "W", 0.0, 70.0);
//
//		// Fill charts
//		BaseController bc;
//		// 1) BaseController
//		bc = run_Base();
//		lcd.addSeries("BaseController", bc.getFridge().getTempCol()
//				.getResults());
//		scd.addSeries("BaseController", bc.getFridge().getLoadCol()
//				.getResults());
//		// 2) TLR
//		bc = run_TLR();
//		lcd.addSeries("Extension_TLR", bc.getFridge().getTempCol()
//				.getResults());
//		scd.addSeries("Extension_TLR", bc.getFridge().getLoadCol()
//				.getResults());
//		// 3) TLR_stateful
//		bc = run_TLR_stateful();
//		lcd.addSeries("TLR_extension_stateful", bc.getFridge().getTempCol()
//				.getResults());
//		scd.addSeries("TLR_extension_stateful", bc.getFridge().getLoadCol()
//				.getResults());
//		// 4) TLR_random
//		bc = run_TLR_random();
//		lcd.addSeries("TLR_extension_random", bc.getFridge().getTempCol()
//				.getResults());
//		scd.addSeries("TLR_extension_random", bc.getFridge().getLoadCol()
//				.getResults());
//
//		// Finish charts
//		lcd.create();
//		scd.create();
//
//		// Open shell
//		shell.setSize(900, 800);
//		shell.open();
//		if (block) {
//			while (!shell.isDisposed()) {
//				if (!display.readAndDispatch())
//					display.sleep();
//			}
//		}
//	}
//
//	private static BaseController run_Base() {
//		BaseController bc = new BaseController(true);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//
//		return bc;
//	}
//
//	private static BaseController run_DSC_load() {
//		BaseController bc = new BaseController(true);
//		Extension_DSC dsc = new Extension_DSC(bc);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			dsc.clock();
//			if (l == dsc_load) {
//				dsc.signal(Extension_DSC.EV_LOAD, dsc.getIdle(), dsc_spread);
//				dsc.dispatchSignals(dsc.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//
//		return bc;
//	}
//
//	private static BaseController run_DSC_load_stateful() {
//		BaseController bc = new BaseController(true);
//		Extension_DSC_stateful dsc = new Extension_DSC_stateful(bc);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			dsc.clock();
//			if (l == dsc_load) {
//				dsc.signal(Extension_DSC_stateful.EV_LOAD, dsc.getIdle(),
//						dsc_spread);
//				dsc.dispatchSignals(dsc.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//
//		return bc;
//	}
//
//	private static BaseController run_DSC_load_stateful_fullwidth() {
//		BaseController bc = new BaseController(true);
//		Extension_DSC_stateful_fullwidth dsc = new Extension_DSC_stateful_fullwidth(
//				bc);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			dsc.clock();
//			if (l == dsc_load) {
//				dsc.signal(Extension_DSC_stateful_fullwidth.EV_LOAD, dsc
//						.getIdle(), dsc_spread);
//				dsc.dispatchSignals(dsc.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//
//		return bc;
//	}
//	
//	private static BaseController run_DSC_load_random() {
//		BaseController bc = new BaseController(true);
//		Extension_DSC_random dsc = new Extension_DSC_random(bc);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			dsc.clock();
//			if (l == dsc_load) {
//				dsc.signal(Extension_DSC_random.EV_LOAD, dsc.getIdle(),
//						dsc_spread);
//				dsc.dispatchSignals(dsc.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//
//		return bc;
//	}
//
//	private static BaseController run_DSC_unload() {
//		BaseController bc = new BaseController(true);
//		Extension_DSC dsc = new Extension_DSC(bc);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			dsc.clock();
//			if (l == dsc_unload) {
//				dsc.signal(Extension_DSC.EV_UNLOAD, dsc.getIdle(), dsc_spread);
//				dsc.dispatchSignals(dsc.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//
//		return bc;
//	}
//
//	private static BaseController run_DSC_unload_stateful() {
//		BaseController bc = new BaseController(true);
//		Extension_DSC_stateful dsc = new Extension_DSC_stateful(bc);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			dsc.clock();
//			if (l == dsc_unload) {
//				dsc.signal(Extension_DSC_stateful.EV_UNLOAD, dsc.getIdle(),
//						dsc_spread);
//				dsc.dispatchSignals(dsc.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//		
//		return bc;
//	}
//	
//	private static BaseController run_DSC_unload_stateful_fullwidth() {
//		BaseController bc = new BaseController(true);
//		Extension_DSC_stateful_fullwidth dsc = new Extension_DSC_stateful_fullwidth(
//				bc);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			dsc.clock();
//			if (l == dsc_unload) {
//				dsc.signal(Extension_DSC_stateful_fullwidth.EV_UNLOAD, dsc
//						.getIdle(), dsc_spread);
//				dsc.dispatchSignals(dsc.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//
//		return bc;
//	}
//	
//	private static BaseController run_DSC_unload_random() {
//		BaseController bc = new BaseController(true);
//		Extension_DSC_random dsc = new Extension_DSC_random(bc);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			dsc.clock();
//			if (l == dsc_unload) {
//				dsc.signal(Extension_DSC_random.EV_UNLOAD, dsc.getIdle(),
//						dsc_spread);
//				dsc.dispatchSignals(dsc.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//
//		return bc;
//	}
//	
//	private static BaseController run_TLR() {
//		BaseController bc = new BaseController(true);
//		Extension_TLR tlr = new Extension_TLR(bc);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			tlr.clock();
//			if (l == tlr_t_notify) {
//				tlr.signal(Extension_TLR.EV_REDUCE, tlr.getIdle(),
//						tlr_tau_preload, tlr_tau_reduce);
//				tlr.dispatchSignals(tlr.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//		
//		return bc;
//	}
//	
//	private static BaseController run_TLR_stateful() {
//		BaseController bc = new BaseController(true);
//		Extension_TLR tlr = new Extension_TLR(bc);
//		TLR_extension_stateful ext = new TLR_extension_stateful(tlr);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			tlr.clock();
//			ext.clock();
//			if (l == tlr_t_notify) {
//				tlr.signal(Extension_TLR.EV_REDUCE, tlr.getIdle(),
//						tlr_tau_preload, tlr_tau_reduce);
//				tlr.dispatchSignals(tlr.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//		
//		return bc;
//	}
//	
//	private static BaseController run_TLR_random() {
//		BaseController bc = new BaseController(true);
//		Extension_TLR tlr = new Extension_TLR(bc);
//		TLR_extension_random ext = new TLR_extension_random(tlr);
//
//		long start = System.currentTimeMillis();
//		for (long l = 0L; l < steps; l++) {
//			bc.clock();
//			tlr.clock();
//			ext.clock();
//			if (l == tlr_t_notify) {
//				tlr.signal(Extension_TLR.EV_REDUCE, tlr.getIdle(),
//						tlr_tau_preload, tlr_tau_reduce);
//				tlr.dispatchSignals(tlr.getIdle());
//			}
//
//			// Delay
//			if (speed < 1000.0 && speed >= 1.0) {
//				try {
//					Thread.sleep(Math.round(1000.0 / speed));
//				} catch (InterruptedException e) {
//				}
//			}
//		}
//		long dur = System.currentTimeMillis() - start;
//		long min = dur / 60000l;
//		long sec = (dur % 60000l) / 1000l;
//		long ms = (dur % 60000l) % 1000l;
//		System.out.println(steps + " steps finished in " + min + "m" + sec
//				+ "s" + ms + "ms");
//		
//		return bc;
//	}
}