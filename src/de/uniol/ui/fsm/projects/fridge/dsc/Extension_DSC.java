package de.uniol.ui.fsm.projects.fridge.dsc;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.projects.fridge.BaseController;

public class Extension_DSC extends FSM {

	public final static String EV_LOAD = "ev_load";
	public final static String EV_UNLOAD = "ev_unload";

	private BaseController bc;
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