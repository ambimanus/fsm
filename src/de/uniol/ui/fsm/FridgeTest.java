package de.uniol.ui.fsm;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.uniol.ui.fsm.projects.fridge.BaseController;
import de.uniol.ui.fsm.projects.fridge.dsc_random.Extension_DSC_random;
import de.uniol.ui.fsm.ui.LineChartDialog;
import de.uniol.ui.fsm.ui.StepChartDialog;

public class FridgeTest {

	private final static long steps = 60 * 60 * 10;
	/** Simulation speed, min=1, max=1000 */
	private final static double speed = 1000.0;

	public static void main(String[] args) {
		BaseController bc = new BaseController();
		Extension_DSC_random dsc = new Extension_DSC_random(bc);

		long start = System.currentTimeMillis();
		for (long l = 0L; l < steps; l++) {
			bc.clock();
			dsc.clock();
			if (l == 4 * 60 * 60) {
				dsc.signal(Extension_DSC_random.EV_LOAD, dsc.getIdle(), 0.0);
				dsc.dispatchSignals(dsc.getIdle());
			}

			// Delay
			if (speed < 1000.0 && speed >= 1.0) {
				try {
					Thread.sleep(Math.round(1000.0 / speed));
				} catch (InterruptedException e) {
				}
			}
		}
		long dur = System.currentTimeMillis() - start;
		long min = dur / 60000l;
		long sec = (dur % 60000l) / 1000l;
		long ms = (dur % 60000l) % 1000l;
		System.out.println(steps + " steps finished in " + min + "m" + sec
				+ "s" + ms + "ms");

		showResults(true, bc);
	}

	private static void showResults(boolean block, BaseController bc) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		shell.setText("FSM Simulation results");
		int numberOfCharts = 0;

		// Temperature chart
		LineChartDialog lcd = new LineChartDialog(shell,
				"Temperature progress", "Time (h)", "Temperature (°C)", "min",
				"°C", bc.getTmin(), bc.getTmax());
		lcd.addSeries(bc.getFridge().getTempCol());
		lcd.create();
		numberOfCharts++;

		// Load chart
		StepChartDialog scd = new StepChartDialog(shell, "Load progress",
				"Time (h)", "Load (W)", "min", "W", 0.0, 70.0);
		scd.addSeries(bc.getFridge().getLoadCol());
		scd.create();
		numberOfCharts++;

		// Open shell
		shell.setSize(900, numberOfCharts * 400);
		shell.open();
		if (block) {
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		}
	}
}