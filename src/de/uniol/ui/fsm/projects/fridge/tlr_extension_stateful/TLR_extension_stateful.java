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
package de.uniol.ui.fsm.projects.fridge.tlr_extension_stateful;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.projects.fridge.BaseController;
import de.uniol.ui.fsm.projects.fridge.tlr.Extension_TLR;
import de.uniol.ui.fsm.projects.fridge.tlr.Extension_TLR.DeviceState;
import de.uniol.ui.fsm.util.Geometry;
import de.uniol.ui.fsm.util.Line;

@SuppressWarnings("unused")
public class TLR_extension_stateful extends FSM {

	public final static String EV_REDUCE = "ev_reduce";

	private Extension_TLR tlr;
	private BaseController bc;
	
	private double tau_preload;
	private double tau_reduce;
	private double tau_warmup;
	private double tau_gamma;
	private DeviceState oState;
	private DeviceState rState;
	private DeviceState dState;

	private T_wait_active_TO_calculate_action t_calculate_action;
	private T_calculate_action_TO_calculate_state_red t_calculate_red;
	private T_calculate_action_TO_calculate_state_warmup t_calculate_warmup;
	private T_calculate_state_TO_idle_cooling t_cooling;
	private T_calculate_state_TO_idle_warming t_warming;

	public TLR_extension_stateful(Extension_TLR tlr) {
		super("TLR_extension_stateful");
		this.tlr = tlr;
		this.bc = tlr.getBc();

		State_idle idle = new State_idle(this);
		State_wait_active wait_active = new State_wait_active(this);
		State_calculate_action calculate_action = new State_calculate_action(
				this);
		State_calculate_state calculate_state = new State_calculate_state(this);
		
		T_default_TO_idle t_def = new T_default_TO_idle(this, idle);
		T_idle_TO_wait_active t_wait_active = new T_idle_TO_wait_active(this,
				idle, wait_active);
		
		tlr.addSignalListener(t_wait_active);
		
		t_calculate_action = new T_wait_active_TO_calculate_action(this,
				wait_active, calculate_action);

		t_calculate_red = new T_calculate_action_TO_calculate_state_red(this,
				calculate_action, calculate_state);
		t_calculate_warmup = new T_calculate_action_TO_calculate_state_warmup(
				this, calculate_action, calculate_state);

		t_cooling = new T_calculate_state_TO_idle_cooling(this,
				calculate_state, idle);
		t_warming = new T_calculate_state_TO_idle_warming(this,
				calculate_state, idle);
	}

	/**
	 * Calculates the state of the fridge how it would be after the given
	 * timespan from now on. The timespan may include phase changes. The
	 * parameter <code>current</code> determines the state of the device at the
	 * current point of time.
	 * 
	 * @param current
	 * @param timespan
	 * @return
	 */
	public DeviceState stateAfter(DeviceState current, double timespan) {
		// Calculate base timespans
		double tau_phaseChange = tlr.tau(current.t, current.active ? bc
				.getTmin() : bc.getTmax());
		DeviceState ret = new DeviceState();
		if (timespan <= tau_phaseChange) {
			// No phase change occurs in the given timespan.
			ret.active = current.active;
			ret.t = tlr.TAfter(current.active, timespan, current.t);
		} else {
			// At least one phase change will occur in given timespan.
			// ts = remaining timespan after first phase change
			double ts = timespan - tau_phaseChange;
			// c = amount of complete full cycles (tw+tc) in ts
			int c = (int) Math.floor(ts
					/ (bc.getTauWarming() + bc.getTauCooling()));
			// remainder = remaining time in ts after last complete full cycle
			double remainder = ts
					- ((bc.getTauWarming() + bc.getTauCooling()) * (double) c);
			// Check type of the phase after timespan and determine resulting
			// state
			if (current.active) {
				// We started with cooling, so the first *full* phase began with
				// warming.
				if (remainder < bc.getTauWarming()) {
					// The remainder cannot contain a whole warming phase, so we
					// will still be warming after the timespan.
					ret.active = false;
					ret.t = tlr.TAfter(false, remainder, bc.getTmin());
				} else {
					// The remainder contains a whole warming phase, so we will
					// be cooling after the timespan.
					ret.active = true;
					ret.t = tlr.TAfter(true, remainder - bc.getTauWarming(), bc
							.getTmax());
				}
			} else {
				// We started with warming, so the first *full* phase began with
				// cooling.
				if (remainder < bc.getTauCooling()) {
					// The remainder cannot contain a whole cooling phase, so we
					// will still be cooling after the timespan.
					ret.active = true;
					ret.t = tlr.TAfter(true, remainder, bc.getTmax());
				} else {
					// The remainder contains a whole cooling phase, so we will
					// be warming after the timespan.
					ret.active = false;
					ret.t = tlr.TAfter(false, remainder - bc.getTauCooling(),
							bc.getTmin());
				}
			}
		}
		return ret;
	}

	/**
	 * Calculates the delay to the first point in future time where the
	 * temperature curves of the two given DeviceStates will cross.
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public double gamma(DeviceState s1, DeviceState s2) {
		double[] is = new double[2];
		int ret;
		// Create line segments
		Line mod = new Line();
		Line reg = new Line();
		Line modNext = new Line();
		Line regNext = new Line();
		mod.x1 = 0;
		mod.y1 = s1.t;
		if (s1.active) {
			mod.x2 = mod.x1
					+ tlr.tau(s1.t, bc.getTmin());
			mod.y2 = bc.getTmin();
			modNext.x1 = mod.x2;
			modNext.y1 = mod.y2;
			modNext.x2 = modNext.x1
					+ tlr.tau(modNext.y1, bc.getTmax());
			modNext.y2 = bc.getTmax();
		} else {
			mod.x2 = mod.x1
					+ tlr.tau(s1.t, bc.getTmax());
			mod.y2 = bc.getTmax();
			modNext.x1 = mod.x2;
			modNext.y1 = mod.y2;
			modNext.x2 = modNext.x1
					+ tlr.tau(modNext.y1, bc.getTmin());
			modNext.y2 = bc.getTmin();
		}
		reg.x1 = 0;
		reg.y1 = s2.t;
		if (s2.active) {
			reg.x2 = reg.x1
					+ tlr.tau(s2.t, bc.getTmin());
			reg.y2 = bc.getTmin();
			regNext.x1 = reg.x2;
			regNext.y1 = reg.y2;
			regNext.x2 = regNext.x1
					+ tlr.tau(regNext.y1, bc.getTmax());
			regNext.y2 = bc.getTmax();
		} else {
			reg.x2 = reg.x1
					+ tlr.tau(s2.t, bc.getTmax());
			reg.y2 = bc.getTmax();
			regNext.x1 = reg.x2;
			regNext.y1 = reg.y2;
			regNext.x2 = regNext.x1
					+ tlr.tau(regNext.y1, bc.getTmin());
			regNext.y2 = bc.getTmin();
		}
		// Check intersection of mod & reg
		ret = Geometry.findLineSegmentIntersection(mod, reg, is);
		if (ret != 1) {
			// Check intersection of mod & regNext
			ret = Geometry.findLineSegmentIntersection(mod, regNext, is);
		}
		if (ret != 1) {
			// Check intersection of modNext & reg
			ret = Geometry.findLineSegmentIntersection(modNext, reg, is);
		}
		if (ret != 1) {
			// Check intersection of modNext & regNext
			ret = Geometry.findLineSegmentIntersection(modNext, regNext, is);
		}
		if (ret != 1) {
			// Should not happen:
			System.err.println(getName() + " - No crossing point found: " + s1
					+ " vs. " + s2);
		}
		// Check rounding error (negative delay)
		if (is[0] < 0) {
			System.err
					.println("Negative delay fixed from " + is[0] + " to 0.0");
			return 0.0;
		} else {
			return is[0];
		}
	}

	public BaseController getBc() {
		return bc;
	}

	public double getTau_preload() {
		return tau_preload;
	}

	public void setTau_preload(double tau_preload) {
		this.tau_preload = tau_preload;
		this.t_calculate_action.setWaitDelay(60L * Math.round(tau_preload));
	}

	public double getTau_reduce() {
		return tau_reduce;
	}

	public void setTau_reduce(double tau_reduce) {
		this.tau_reduce = tau_reduce;
		this.t_calculate_red.setWaitDelay(60L * Math.round(tau_reduce));
	}

	public double getTau_warmup() {
		return tau_warmup;
	}

	public void setTau_warmup(double tau_warmup) {
		this.tau_warmup = tau_warmup;
		this.t_calculate_warmup.setWaitDelay(60L * Math.round(tau_warmup));
	}

	public double getTau_gamma() {
		return tau_gamma;
	}

	public void setTau_gamma(double tau_gamma) {
		this.tau_gamma = tau_gamma;
		this.t_cooling.setWaitDelay(60L * Math.round(tau_gamma));
		this.t_warming.setWaitDelay(60L * Math.round(tau_gamma));
	}

	public DeviceState getOState() {
		return oState;
	}

	public void setOState(DeviceState state) {
		oState = state;
	}

	public DeviceState getRState() {
		return rState;
	}

	public void setRState(DeviceState state) {
		rState = state;
	}

	public DeviceState getDState() {
		return dState;
	}

	public void setDState(DeviceState state) {
		dState = state;
	}
}