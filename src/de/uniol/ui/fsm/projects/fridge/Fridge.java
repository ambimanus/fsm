package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.ClockListener;
import de.uniol.ui.fsm.model.FSM;

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
	private double temperature = 0.0;
	private FSM parent;
	
	public Fridge(FSM parent, double initialTemperature) {
		this.parent = parent;
		this.parent.addClockListener(this);
		this.temperature = initialTemperature;
	}
	
	public void clock(long clock) {
		// Calculate eps based on clock = 1Hz
		eps = Math.exp(-(1.0 / 3600.0) * (a / m_c));
		// Update current temperature
		if (active) {
			temperature = (eps * temperature)
					+ ((1 - eps) * (t_surround - (eta * (q_cooling / a))));
		} else {
			temperature = (eps * temperature)
					+ ((1 - eps) * (t_surround - (eta * (q_warming / a))));
		}
		System.out.println("Time=" + parent.getClock()
				+ " - Fridge temperature = " + temperature);
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
}