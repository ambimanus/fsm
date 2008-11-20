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
package de.uniol.ui.fsm.projects.fridge.dsc;

import simkit.random.BernoulliVariate;
import simkit.random.Congruential;
import simkit.random.RandomVariate;
import simkit.random.UniformVariate;
import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class Extension_DSC extends FSM {

	public final static String EV_LOAD = "ev_load";
	public final static String EV_UNLOAD = "ev_unload";

	private BaseController bc;
	private static RandomVariate uniform;
	private double lastLow = Double.NaN;
	private double lastHigh = Double.NaN;
	private static RandomVariate bernoulli;
	private double lastPropability = Double.NaN;
	private boolean doUnload = false;
	private long delay;

	private State_idle idle;
	private State_wait_random wait_random;
	private T_wait_random_EV_load_TO_idle t_wait_load;
	private T_wait_random_EV_unload_TO_idle t_wait_unload;

	@SuppressWarnings("unused")
	public Extension_DSC(BaseController bc) {
		super("Extension_DSC");
		this.bc = bc;
		
		if (uniform == null) {
			uniform = new UniformVariate();
			Congruential cong = new Congruential();
			cong.setSeed(Math.round(Math.random() * 100000000.0));
			uniform.setRandomNumber(cong);
		}
		if (bernoulli == null) {
			bernoulli = new BernoulliVariate();
		}

		idle = new State_idle(this);
		wait_random = new State_wait_random(this);
		
		T_default_TO_idle t_def = new T_default_TO_idle(this, idle);

		T_idle_EV_load_TO_wait_random t_idle_load = new T_idle_EV_load_TO_wait_random(
				this, idle, wait_random);
		T_idle_EV_unload_TO_wait_random t_idle_unload = new T_idle_EV_unload_TO_wait_random(
				this, idle, wait_random);
		t_wait_load = new T_wait_random_EV_load_TO_idle(this, wait_random, idle);
		t_wait_unload = new T_wait_random_EV_unload_TO_idle(this, wait_random,
				idle);
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

	public boolean isDoUnload() {
		return doUnload;
	}

	public void setDoUnload(boolean doUnload) {
		this.doUnload = doUnload;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
		t_wait_load.setWaitDelay(60L * delay);
		t_wait_unload.setWaitDelay(60L * delay);
	}

	public BaseController getBc() {
		return bc;
	}

	public State_idle getIdle() {
		return idle;
	}

	public State_wait_random getWait_random() {
		return wait_random;
	}

	public T_wait_random_EV_load_TO_idle getT_wait_load() {
		return t_wait_load;
	}

	public T_wait_random_EV_unload_TO_idle getT_wait_unload() {
		return t_wait_unload;
	}
}