package de.uniol.ui.fsm.ui;

import java.util.ArrayList;

import simkit.stat.SimpleStatsTally;

/**
 * This collector is able to receive multiple values per clock, and stores the
 * mean of the received values.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
public class TimeSeriesMultiMeanCollector {

	/** holds the timestamps (x-values) */
	private ArrayList<Double> times = new ArrayList<Double>();
	/** holds the collected values (y-values) */
	private ArrayList<Double> values = new ArrayList<Double>();;
	/** stats tally from SimKit library which efficiently calculates the mean values */
	private SimpleStatsTally sst = new SimpleStatsTally();
	/** clock counter */
	private double currentClock = -1L;

	/**
	 * Adds a new observation to this collector. If the given clock is the same
	 * since last call, the mean of all values of this clock will be used as
	 * end value.
	 * 
	 * @param clock
	 * @param value
	 */
	public void newObservation(double clock, double value) {
		// Init
		if (currentClock == -1L) {
			currentClock = clock * 60000.0;
		}
		// If the clock has changed, store the mean of the values from last
		// clock, and reset the stats tally
		if (currentClock != clock * 60000.0 && clock > 0L) {
			// Store values of last clock
			times.add(currentClock);
			values.add(sst.getMean());
			// Init new phase
			sst = new SimpleStatsTally();
			currentClock = clock * 60000.0;
		}
		// Add current observation
		sst.newObservation(value);
	}
	
	/**
	 * @return the collected data as time series.
	 */
	public double[][] getResults() {
		// Store values of last clock
		times.add((double) currentClock);
		values.add(sst.getMean());
		// Create results
		double[] t = new double[times.size()];
		double[] v = new double[values.size()];
		for (int i = 0; i < times.size(); i++) {
			t[i] = times.get(i);
			v[i] = values.get(i);
		}
		return new double[][] { t, v };
	}
}