package de.uniol.ui.fsm.ui;

import java.util.ArrayList;

import simkit.stat.SimpleStatsTally;

public class TimeSeriesMultiMeanCollector {

	private ArrayList<Double> times = new ArrayList<Double>();
	private ArrayList<Double> values = new ArrayList<Double>();;
	private SimpleStatsTally sst = new SimpleStatsTally();
	private double currentClock = -1L;
	
	public void newObservation(double clock, double value) {
		if (currentClock == -1L) {
			currentClock = clock * 60000.0;
		}
		if (currentClock != clock * 60000.0 && clock > 0L) {
			// Store values of last clock
			times.add(currentClock);
			values.add(sst.getMean());
			// Init new phase
			sst = new SimpleStatsTally();
			currentClock = clock * 60000.0;
		}
		sst.newObservation(value);
	}
	
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