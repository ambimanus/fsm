package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.ClockListener;
import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.ui.TimeseriesCollector;

public class Fridge implements ClockListener {

	/* device parameters */
	/** surrounding temperature */
	protected double t_surround = 20.0;
	/** insulation */
	protected double a = 3.21;
	/** load in cooling phase */
	protected double q_cooling = 70.0;
	/** load in warming phase */
	protected double q_warming = 0.0;
	/** thermal mass */
	protected double m_c = 19.95;
	/** efficiency */
	protected double eta = 3.0;
	/** system intertia, calculated value */
	protected double eps; // = Math.exp(-(tau * a) / m_c)
	
	private boolean active = true;
	private double currentClock = 0.0;
	private double temperature = 0.0;
	private FSM parent;
	
	private TimeseriesCollector tempCol;
	private TimeseriesCollector loadCol;
	
	public Fridge(FSM parent, double initialTemperature) {
		this.parent = parent;
		this.parent.addClockListener(this);
		this.temperature = initialTemperature;
		
		tempCol = new TimeseriesCollector("temperature");
		loadCol = new TimeseriesCollector("load");
	}
	
	public void clock(long clock) {
		currentClock = clock;
		// Calculate eps based on clock = 1Hz
		eps = Math.exp(-(1.0 / 3600.0) * (a / m_c));
		// Update current temperature
		if (active) {
			temperature = (eps * temperature)
					+ ((1 - eps) * (t_surround - (eta * (q_cooling / a))));
			loadCol.addObservation(currentClock / 60.0, q_cooling);
		} else {
			temperature = (eps * temperature)
					+ ((1 - eps) * (t_surround - (eta * (q_warming / a))));
			loadCol.addObservation(currentClock / 60.0, q_warming);
		}
		tempCol.addObservation(currentClock / 60.0, temperature);
//		System.out.println("Time=" + parent.getClock()
//				+ " - Fridge temperature = " + temperature);
	}
	
	public void enableCooling() {
		active = true;
	}
	
	public void disableCooling() {
		active = false;
	}

	public double getTemperature() {
		return temperature;
	}

	public TimeseriesCollector getTempCol() {
		return tempCol;
	}

	public TimeseriesCollector getLoadCol() {
		return loadCol;
	}

	public double getT_surround() {
		return t_surround;
	}

	public void setT_surround(double t_surround) {
		this.t_surround = t_surround;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getQ_cooling() {
		return q_cooling;
	}

	public void setQ_cooling(double q_cooling) {
		this.q_cooling = q_cooling;
	}

	public double getQ_warming() {
		return q_warming;
	}

	public void setQ_warming(double q_warming) {
		this.q_warming = q_warming;
	}

	public double getM_c() {
		return m_c;
	}

	public void setM_c(double m_c) {
		this.m_c = m_c;
	}

	public double getEta() {
		return eta;
	}

	public void setEta(double eta) {
		this.eta = eta;
	}

	public FSM getParent() {
		return parent;
	}
}