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
package de.uniol.ui.fsm.projects.fridge;

import simkit.random.RandomVariate;
import de.uniol.ui.fsm.model.ClockListener;
import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.ui.TimeSeriesMultiMeanCollector;

/**
 * This class represents a virtual fridge. It contains device parameters and
 * statistical collectors. Will be notified upon system clock changes to be able
 * to update the internal temperature, based on the current load. The load will
 * be updated based on the <code>active</code> field, wich will be set set upon
 * external calls to {@link #enableCooling()} or {@link #disableCooling()}.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
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
	protected double eps = Double.NaN; // = Math.exp(-(tau * a) / m_c)

	/** defines the current phase: cooling/warming */
	private boolean active = true;
	/** curent system clock */
	private double currentClock = 0.0;
	/** current temperature */
	private double temperature = 0.0;
	/** reference to FSM which sends the clock signals */
	private FSM parent;

	/** statistical temperature collector */
	private TimeSeriesMultiMeanCollector tempCol;
	/** statistical load collector */
	private TimeSeriesMultiMeanCollector loadCol;

	public Fridge(FSM parent, double initialTemperature,
			TimeSeriesMultiMeanCollector tempCol,
			TimeSeriesMultiMeanCollector loadCol) {
		this.parent = parent;
		this.parent.addClockListener(this);
		this.temperature = initialTemperature;
		this.tempCol = tempCol;
		this.loadCol = loadCol;
	}

	/**
	 * Increases the clock counter and updates the internal temperature.
	 */
	public void clock(long clock) {
		currentClock = clock;
		// Calculate eps based on clock = 1Hz
		if (Double.isNaN(eps)) {
			eps = Math.exp(-(1.0 / 3600.0) * (a / m_c));
		}
		// Update current temperature
		if (active) {
			temperature = (eps * temperature)
					+ ((1 - eps) * (t_surround - (eta * (q_cooling / a))));
			loadCol.newObservation(currentClock / 60.0, q_cooling);
		} else {
			temperature = (eps * temperature)
					+ ((1 - eps) * (t_surround - (eta * (q_warming / a))));
			loadCol.newObservation(currentClock / 60.0, q_warming);
		}
		tempCol.newObservation(currentClock / 60.0, temperature);
		// System.out.println("Time=" + parent.getClock()
		// + " - Fridge temperature = " + temperature);
	}

	/**
	 * Enables the cooling.
	 */
	public void enableCooling() {
		active = true;
	}

	/**
	 * Disables the cooling.
	 */
	public void disableCooling() {
		active = false;
	}

	/**
	 * @return the current temperature
	 */
	public double getTemperature() {
		return temperature;
	}

	/*
	 * Getters, setters
	 */
	
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

	public boolean isActive() {
		return active;
	}

	/*
	 * Parameter variance util methods:
	 */

	/**
	 * Performs a distribution of the parameter values using random numbers
	 * produced sequentially by the given variate.
	 */
	public void variateAllSequential(RandomVariate rv, double t_min,
			double t_max) {
		variate_a(rv);
		variate_eta(rv);
		variate_mC(rv);
		variate_tSurround(rv);
		variate_temperature(rv, t_min, t_max);
	}

	/**
	 * Performs a distribution of the parameter values using the first random
	 * number produced by the given variate for all values.
	 * 
	 * @param rv
	 */
	public void variateAllParallel(RandomVariate rv, double t_min, double t_max) {
		double var = rv.generate();
		if (var < 0) {
			var = 0;
		}
		a *= var;
		if (a == 0.0) {
			a = 0.001;
		}
		eta *= var;
		m_c *= var;
		if (m_c == 0.0) {
			m_c = 0.001;
		}
		t_surround *= var;
		temperature *= var;

		if (temperature < t_min) {
			temperature = t_min;
		}
		if (temperature > t_max) {
			temperature = t_max;
		}
	}

	/**
	 * Performs a distribution of the parameter '<code>a</code>' using a random
	 * number produced by the given variate.
	 * 
	 * @param rv
	 */
	public void variate_a(RandomVariate rv) {
		double var = rv.generate();
		if (var < 0) {
			var = 0.001;
		}
		a *= var;
	}

	/**
	 * Performs a distribution of the parameter '<code>eta</code>' using a
	 * random number produced by the given variate.
	 * 
	 * @param rv
	 */
	public void variate_eta(RandomVariate rv) {
		double var = rv.generate();
		if (var < 0) {
			var = 0;
		}
		eta *= var;
	}

	/**
	 * Performs a distribution of the parameter '<code>m_c</code>' using a
	 * random number produced by the given variate.
	 * 
	 * @param rv
	 */
	public void variate_mC(RandomVariate rv) {
		double var = rv.generate();
		if (var < 0) {
			var = 0.001;
		}
		m_c *= var;
	}

	/**
	 * Performs a distribution of the parameter '<code>t_surround</code>' using
	 * a random number produced by the given variate.
	 * 
	 * @param rv
	 */
	public void variate_tSurround(RandomVariate rv) {
		double var = rv.generate();
		if (var < 0) {
			var = 0;
		}
		t_surround *= var;
	}

	/**
	 * Performs a distribution of the parameter '<code>t_current</code>' using a
	 * random number produced by the given variate.
	 * 
	 * @param rv
	 */
	public void variate_temperature(RandomVariate rv, double t_min, double t_max) {
		temperature *= rv.generate();
		if (temperature < t_min) {
			temperature = t_min;
		}
		if (temperature > t_max) {
			temperature = t_max;
		}
	}

	/**
	 * Performs a distribution by setting the parameters to the values produced
	 * by the given variate.
	 * 
	 * @param rv
	 */
	public void generateAllSequential(RandomVariate rv, double t_min,
			double t_max) {
		generate_a(rv);
		generate_eta(rv);
		generate_mC(rv);
		generate_tSurround(rv);
		generate_temperature(rv, t_min, t_max);
	}

	/**
	 * Performs a distribution of the parameter '<code>a</code>' by setting it
	 * to the value produced next by the given variate.
	 * 
	 * @param rv
	 */
	public void generate_a(RandomVariate rv) {
		double var = rv.generate();
		if (var < 0) {
			var = 0.001;
		}
		a = var;
	}

	/**
	 * Performs a distribution of the parameter '<code>eta</code>' by setting it
	 * to the value produced next by the given variate.
	 * 
	 * @param rv
	 */
	public void generate_eta(RandomVariate rv) {
		double var = rv.generate();
		if (var < 0) {
			var = 0;
		}
		eta = var;
	}

	/**
	 * Performs a distribution of the parameter '<code>m_c</code>' by setting it
	 * to the value produced next by the given variate.
	 * 
	 * @param rv
	 */
	public void generate_mC(RandomVariate rv) {
		double var = rv.generate();
		if (var < 0) {
			var = 0.001;
		}
		m_c = var;
	}

	/**
	 * Performs a distribution of the parameter '<code>t_surround</code>' by
	 * setting it to the value produced next by the given variate.
	 * 
	 * @param rv
	 */
	public void generate_tSurround(RandomVariate rv) {
		double var = rv.generate();
		if (var < 0) {
			var = 0;
		}
		t_surround = var;
	}

	/**
	 * Performs a distribution of the parameter '<code>t_current</code>' by
	 * setting it to the value produced next by the given variate.
	 * 
	 * @param rv
	 */
	public void generate_temperature(RandomVariate rv, double t_min,
			double t_max) {
		temperature = rv.generate();
		if (temperature < t_min) {
			temperature = t_min;
		}
		if (temperature > t_max) {
			temperature = t_max;
		}
	}
}