package de.uniol.ui.fsm.projects.fridge.dsc_random;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class Extension_DSC_random extends FSM {

	public final static String EV_LOAD = "ev_load";
	public final static String EV_UNLOAD = "ev_unload";

	private BaseController bc;
	private boolean doUnload = false;
	private long delay;
	private double T_dest;
	private long tau_restore;

	private State_idle idle;
	private State_wait_random wait_random;
	private State_wait_restore wait_restore;
	private T_wait_random_EV_load_TO_restore t_wait_load;
	private T_wait_random_EV_unload_TO_restore t_wait_unload;
	private T_wait_restore_cooling_TO_idle t_restore_cooling;
	private T_wait_restore_warming_TO_idle t_restore_warming;

	@SuppressWarnings("unused")
	public Extension_DSC_random(BaseController bc) {
		super("Extension_DSC_stateful");
		this.bc = bc;

		idle = new State_idle(this);
		wait_random = new State_wait_random(this);
		wait_restore = new State_wait_restore(this);
		
		T_default_TO_idle t_def = new T_default_TO_idle(this, idle);

		T_idle_EV_load_TO_wait_random t_idle_load = new T_idle_EV_load_TO_wait_random(
				this, idle, wait_random);
		T_idle_EV_unload_TO_wait_random t_idle_unload = new T_idle_EV_unload_TO_wait_random(
				this, idle, wait_random);
		t_wait_load = new T_wait_random_EV_load_TO_restore(this, wait_random,
				wait_restore);
		t_wait_unload = new T_wait_random_EV_unload_TO_restore(this,
				wait_random, wait_restore);
		t_restore_cooling = new T_wait_restore_cooling_TO_idle(this, wait_restore,
				idle);
		t_restore_warming = new T_wait_restore_warming_TO_idle(this,
				wait_restore, idle);
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
		t_wait_load.setWaitDelay(delay);
		t_wait_unload.setWaitDelay(delay);
	}

	public double getT_dest() {
		return T_dest;
	}

	public void setT_dest(double t_dest) {
		T_dest = t_dest;
	}

	public long getTau_restore() {
		return tau_restore;
	}

	public void setTau_restore(long tau_restore) {
		this.tau_restore = tau_restore;
		t_restore_cooling.setWaitDelay(tau_restore);
		t_restore_warming.setWaitDelay(tau_restore);
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

	public T_wait_random_EV_load_TO_restore getT_wait_load() {
		return t_wait_load;
	}

	public T_wait_random_EV_unload_TO_restore getT_wait_unload() {
		return t_wait_unload;
	}
}