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
package de.uniol.ui.fsm.projects.fridge.tlr_extension_random;

import simkit.random.BernoulliVariate;
import simkit.random.Congruential;
import simkit.random.RandomVariate;
import simkit.random.UniformVariate;
import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.projects.fridge.BaseController;
import de.uniol.ui.fsm.projects.fridge.tlr.Extension_TLR;
import de.uniol.ui.fsm.projects.fridge.tlr.Extension_TLR.DeviceState;
import de.uniol.ui.fsm.util.Geometry;
import de.uniol.ui.fsm.util.Line;

@SuppressWarnings("unused")
public class TLR_extension_random extends FSM {

	public final static String EV_REDUCE = "ev_reduce";

	private Extension_TLR tlr;
	private BaseController bc;
	private static RandomVariate uniform;
	private double lastLow = Double.NaN;
	private double lastHigh = Double.NaN;
	private static RandomVariate bernoulli;
	private double lastPropability = Double.NaN;
	
	private double tau_preload;
	private double tau_reduce;
	private double TDest;

	private T_wait_restore_TO_idle_cooling t_cooling;
	private T_wait_restore_TO_idle_warming t_warming;

	public TLR_extension_random(Extension_TLR tlr) {
		super("TLR_extension_random");
		this.tlr = tlr;
		this.bc = tlr.getBc();
		
		if (uniform == null) {
			uniform = new UniformVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			uniform.setRandomNumber(cong);
		}
		if (bernoulli == null) {
			bernoulli = new BernoulliVariate();
		}

		State_idle idle = new State_idle(this);
		State_wait_restore wait_restore = new State_wait_restore(this);
		
		T_default_TO_idle t_def = new T_default_TO_idle(this, idle);
		T_idle_TO_wait_restore t_wait_restore = new T_idle_TO_wait_restore(this,
				idle, wait_restore);
		
		tlr.addSignalListener(t_wait_restore);

		t_cooling = new T_wait_restore_TO_idle_cooling(this,
				wait_restore, idle);
		t_warming = new T_wait_restore_TO_idle_warming(this,
				wait_restore, idle);
	}
	
	public double drawUniformRandom(double low, double high) {
		if (lastLow != low || lastHigh != high) {
			uniform.setParameters(low, high);
			lastLow = low;
			lastHigh = high;
		}
		return uniform.generate();
	}
	
	protected double drawBernoulli(double propability) {
		if (lastPropability != propability) {
			bernoulli.setParameters(propability);
			lastPropability = propability;
		}
		return bernoulli.generate();
	}

	public BaseController getBc() {
		return bc;
	}

	public double getTau_preload() {
		return tau_preload;
	}

	public void setTau_preload(double tau_preload) {
		this.tau_preload = tau_preload;
	}

	public double getTau_reduce() {
		return tau_reduce;
	}

	public void setTau_reduce(double tau_reduce) {
		this.tau_reduce = tau_reduce;
	}

	public double getTDest() {
		return TDest;
	}

	public void setTDest(double dest) {
		TDest = dest;
	}

	public T_wait_restore_TO_idle_cooling getT_cooling() {
		return t_cooling;
	}

	public T_wait_restore_TO_idle_warming getT_warming() {
		return t_warming;
	}
}