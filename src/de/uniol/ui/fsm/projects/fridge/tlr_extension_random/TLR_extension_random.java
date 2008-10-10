package de.uniol.ui.fsm.projects.fridge.tlr_extension_random;

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
	
	private double tau_preload;
	private double tau_reduce;
	private double TDest;

	private T_wait_restore_TO_idle_cooling t_cooling;
	private T_wait_restore_TO_idle_warming t_warming;

	public TLR_extension_random(Extension_TLR tlr) {
		super("TLR_extension_random");
		this.tlr = tlr;
		this.bc = tlr.getBc();

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