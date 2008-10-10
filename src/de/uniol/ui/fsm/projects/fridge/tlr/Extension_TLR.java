package de.uniol.ui.fsm.projects.fridge.tlr;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.projects.fridge.BaseController;
import de.uniol.ui.fsm.util.Geometry;
import de.uniol.ui.fsm.util.Line;

@SuppressWarnings("unused")
public class Extension_TLR extends FSM {

	public final static String EV_REDUCE = "ev_reduce";

	private BaseController bc;
	
	private double tau_preload;
	private double tau_reduce;
	private double tau_gamma_scb;
	private double tau_gamma_sba;
	private double tau_gamma_scc1;
	private double tau_a;
	private double tau_b;
	private double tau_c1;
	private double T_activ;
	private double T_max_act;
	private double T_allowed;
	private boolean CC1 = false;
	private boolean BA = false;

	private State_idle idle;
	private State_reduce reduce;
	private State_intersectSBA intersectSBA;
	private State_intersectSCB intersectSCB;
	private State_intersectSCC1 intersectSCC1;

	private T_reduce_TO_intersectSBA t_reduce_ba;
	private T_reduce_TO_intersectSCB t_reduce_cb;
	private T_reduce_TO_intersectSCC1 t_reduce_cc1;
	private T_intersectSCB_TO_idle_EV_reduce t_cb_red;
	private T_intersectSCB_TO_idle_TActiv t_cb_act;
	private T_intersectSCB_TO_idle_TMin t_cb_min;
	private T_intersectSBA_TO_idle_EV_reduce t_ba_red;
	private T_intersectSBA_TO_idle_TActiv t_ba_act;
	private T_intersectSBA_TO_idle_TMaxact t_ba_maxact;
	private T_intersectSCC1_TO_idle_EV_reduce t_cc1_red;
	private T_intersectSCC1_TO_idle_TMax t_cc1_max;
	private T_intersectSCC1_TO_idle_TMax_timed t_cc1_max_timed;

	public Extension_TLR(BaseController bc) {
		super("Extension_TLR");
		this.bc = bc;

		idle = new State_idle(this);
		reduce = new State_reduce(this);
		intersectSBA = new State_intersectSBA(this);
		intersectSCB = new State_intersectSCB(this);
		intersectSCC1 = new State_intersectSCC1(this);
		
		T_default_TO_idle t_idle = new T_default_TO_idle(this, idle);
		T_idle_TO_reduce t_reduce = new T_idle_TO_reduce(this, idle, reduce);
		
		t_reduce_ba = new T_reduce_TO_intersectSBA(this, reduce, intersectSBA);
		t_reduce_cb = new T_reduce_TO_intersectSCB(this, reduce, intersectSCB);
		t_reduce_cc1 = new T_reduce_TO_intersectSCC1(this, reduce,
				intersectSCC1);

		t_cb_red = new T_intersectSCB_TO_idle_EV_reduce(this, intersectSCB,
				idle);
		t_cb_act = new T_intersectSCB_TO_idle_TActiv(this, intersectSCB, idle);
		t_cb_min = new T_intersectSCB_TO_idle_TMin(this, intersectSCB, idle);

		t_ba_red = new T_intersectSBA_TO_idle_EV_reduce(this, intersectSBA,
				idle);
		t_ba_act = new T_intersectSBA_TO_idle_TActiv(this, intersectSBA, idle);
		t_ba_maxact = new T_intersectSBA_TO_idle_TMaxact(this, intersectSBA,
				idle);

		t_cc1_red = new T_intersectSCC1_TO_idle_EV_reduce(this, intersectSCC1,
				idle);
		t_cc1_max = new T_intersectSCC1_TO_idle_TMax(this, intersectSCC1, idle);
		t_cc1_max_timed = new T_intersectSCC1_TO_idle_TMax_timed(this,
				intersectSCC1, idle);
	}

	/**
	 * Calculate time needed to reach temperature <code>t_dest</code>, starting
	 * at temperature <code>t_from</code>. Calculation is based on linear
	 * approximation by using precalculated tau_cooling or tau_warming.
	 * 
	 * @param t_from
	 * @param t_dest
	 * @return
	 */
	public double tau(double t_from, double t_dest) {
		// Calculate range from t_min to t_max
		double range = bc.getTmax() - bc.getTmin();
		// Check direction: warming or cooling
		if (t_from < t_dest) {
			// Calculate fraction of desired range by maximal range and return
			// resulting proportion of tau_warming
			return ((t_dest - t_from) / range) * bc.getTauWarming();
		} else {
			// Calculate fraction of desired range by maximal range and return
			// resulting proportion of tau_cooling
			return -((t_dest - t_from) / range) * bc.getTauCooling();
		}
	}

	/**
	 * Calculates the temperature how it would be after the given elapsed time
	 * from now on, based on the specified starting temperature. The parameter
	 * <code>active</code> specifies the phase of this device. <b>No phase
	 * changes will be considered!</b>
	 * 
	 * @param active
	 * @param elapsedTime
	 * @param previousTemperature
	 * 
	 * @return
	 */
	public double TAfter(boolean active, double elapsedTime,
			double previousTemperature) {
		double ret;
		if (active) {
			ret = previousTemperature
					+ (((bc.getTmin() - bc.getTmax()) / bc.getTauCooling()) * elapsedTime);
		} else {
			ret = previousTemperature
					+ (((bc.getTmax() - bc.getTmin()) / bc.getTauWarming()) * elapsedTime);
		}
		return ret;
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
	public State stateAfter(State current, double timespan) {
		// Calculate base timespans
		double tau_phaseChange = tau(current.t, current.active ? bc.getTmin()
				: bc.getTmax());
		State ret = new State();
		if (timespan <= tau_phaseChange) {
			// No phase change occurs in the given timespan.
			ret.active = current.active;
			ret.t = TAfter(current.active, timespan, current.t);
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
					ret.t = TAfter(false, remainder, bc.getTmin());
				} else {
					// The remainder contains a whole warming phase, so we will
					// be cooling after the timespan.
					ret.active = true;
					ret.t = TAfter(true, remainder - bc.getTauWarming(), bc
							.getTmax());
				}
			} else {
				// We started with warming, so the first *full* phase began with
				// cooling.
				if (remainder < bc.getTauCooling()) {
					// The remainder cannot contain a whole cooling phase, so we
					// will still be cooling after the timespan.
					ret.active = true;
					ret.t = TAfter(true, remainder, bc.getTmax());
				} else {
					// The remainder contains a whole cooling phase, so we will
					// be warming after the timespan.
					ret.active = false;
					ret.t = TAfter(false, remainder - bc.getTauCooling(), bc
							.getTmin());
				}
			}
		}
		return ret;
	}

	/**
	 * Helper class which represents the tupel (temperature, activation) as a
	 * state of the fridge device.
	 * 
	 * @author Chh
	 */
	private static class State {
		
		public double t = Double.NaN;
		public boolean active;

		public State() {
		}
		
		public State(double temperature, boolean active) {
			this.t = temperature;
			this.active = active;
		}
		
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (this == obj) {
				return true;
			}
			if (obj instanceof State) {
				State casted = (State) obj;
				if (casted.t == t && casted.active == active) {
					return true;
				}
			}
			return false;
		}
	}

	public double calculate_tau_gamma_scc1() {
		double now = (double) bc.getClock() / 60.0;
		double tAct = now + tau_preload;
		double tauW = bc.getTauWarming();
		double tauC = bc.getTauCooling();
		double tC1 = tAct + tau_reduce - tauW;
		double aw = bc.aw();
		double TMin = bc.getTmin();
		double TMax = bc.getTmax();
		double T_maxact = TMin + (aw * (tAct - tC1));
		double TCurrent = bc.getFridge().getTemperature();
		// Find intersection with sCC1
		Line s = new Line();
		s.x1 = tC1;
		s.y1 = TMin;
		s.x2 = tAct;
		s.y2 = T_maxact;
		Line c = new Line();
		if (!bc.getFridge().isActive()) {
			c.x1 = now + tau(TCurrent, TMax);
			c.y1 = TMax;
			c.x2 = c.x1 + tauC;
			c.y2 = TMin;
		} else {
			c.x1 = now;
			c.y1 = TCurrent;
			c.x2 = c.x1 + tau(c.y1, TMin);
			c.y2 = TMin;
		}
		double[] is = new double[2];
		int code = Geometry.findLineSegmentIntersection(c, s, is);
		if (code == 1) {
			return is[0] - now;
		} else {
			// No intersection found.
			return -1;
		}
	}

	public double calculate_tau_gamma_sba() {
		double now = (double) bc.getClock() / 60.0;
		double tAct = now + tau_preload;
		double tauW = bc.getTauWarming();
		double tC1 = tAct + tau_reduce - tauW;
		double aw = bc.aw();
		double ac = bc.ac();
		double TMin = bc.getTmin();
		double TMax = bc.getTmax();
		double T_maxact = TMin + (aw * (tAct - tC1));
		double TCurrent = bc.getFridge().getTemperature();
		double tA = tAct - ((T_maxact - TMax) / ac);
		// Find intersection with sBA
		Line s = new Line();
		s.x1 = tA;
		s.y1 = TMax;
		s.x2 = tAct;
		s.y2 = T_maxact;
		Line c = new Line();
		if (bc.getFridge().isActive()) {
			c.x1 = now + tau(TCurrent, TMin);
			c.y1 = TMin;
			c.x2 = c.x1 + tauW;
			c.y2 = TMax;
		} else {
			c.x1 = now;
			c.y1 = TCurrent;
			c.x2 = tAct;
			c.y2 = TAfter(false, tau_preload, TCurrent);
		}
		double[] is = new double[2];
		int code = Geometry.findLineSegmentIntersection(c, s, is);
		if (code == 1) {
			return is[0] - now;
		} else {
			// No intersection found.
			return -1;
		}
	}

	public double calculate_tau_gamma_scb() {
		double now = (double) bc.getClock() / 60.0;
		double tAct = now + tau_preload;
		double tauW = bc.getTauWarming();
		double tauC = bc.getTauCooling();
		double tB = tAct - tauC;
		double TMin = bc.getTmin();
		double TMax = bc.getTmax();
		double TCurrent = bc.getFridge().getTemperature();
		// Find intersection with sCB
		Line s = new Line();
		s.x1 = tB;
		s.y1 = TMax;
		s.x2 = tAct;
		s.y2 = TMin;
		Line c = new Line();
		if (bc.getFridge().isActive()) {
			c.x1 = now + tau(TCurrent, TMin);
			c.y1 = TMin;
			c.x2 = c.x1 + tauW;
			c.y2 = TMax;
		} else {
			c.x1 = now;
			c.y1 = TCurrent;
			c.x2 = c.x1 + tau(c.y1, TMax);
			c.y2 = TMax;
		}
		double[] is = new double[2];
		int code = Geometry.findLineSegmentIntersection(c, s, is);
		if (code == 1) {
			return is[0] - now;
		} else {
			// No intersection found.
			return -1;
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
	}

	public double getTau_reduce() {
		return tau_reduce;
	}

	public void setTau_reduce(double tau_reduce) {
		this.tau_reduce = tau_reduce;
	}

	public double getTau_gamma_scb() {
		return tau_gamma_scb;
	}

	public void setTau_gamma_scb(double tau_gamma_scb) {
		this.tau_gamma_scb = tau_gamma_scb;
		this.t_cb_min.setWaitDelay(60L * Math.round(tau_gamma_scb));
	}

	public double getTau_gamma_sba() {
		return tau_gamma_sba;
	}

	public void setTau_gamma_sba(double tau_gamma_sba) {
		this.tau_gamma_sba = tau_gamma_sba;
		this.t_ba_maxact.setWaitDelay(60L * Math.round(tau_gamma_sba));
	}

	public double getTau_gamma_scc1() {
		return tau_gamma_scc1;
	}

	public void setTau_gamma_scc1(double tau_gamma_scc1) {
		this.tau_gamma_scc1 = tau_gamma_scc1;
		this.t_cc1_max_timed.setWaitDelay(60L * Math.round(tau_gamma_scc1));
	}

	public double getTau_a() {
		return tau_a;
	}

	public void setTau_a(double tau_a) {
		this.tau_a = tau_a;
		this.t_ba_red.setWaitDelay(60L * Math.round(tau_a));
	}

	public double getTau_b() {
		return tau_b;
	}

	public void setTau_b(double tau_b) {
		this.tau_b = tau_b;
		this.t_cb_red.setWaitDelay(60L * Math.round(tau_b));
	}

	public double getTau_c1() {
		return tau_c1;
	}

	public void setTau_c1(double tau_c1) {
		this.tau_c1 = tau_c1;
		this.t_cc1_red.setWaitDelay(60L * Math.round(tau_gamma_scc1));
	}

	public double getT_activ() {
		return T_activ;
	}

	public void setT_activ(double t_activ) {
		T_activ = t_activ;
	}

	public double getT_max_act() {
		return T_max_act;
	}

	public void setT_max_act(double t_max_act) {
		T_max_act = t_max_act;
	}

	public double getT_allowed() {
		return T_allowed;
	}

	public void setT_allowed(double t_allowed) {
		T_allowed = t_allowed;
	}

	public boolean isCC1() {
		return CC1;
	}

	public void setCC1(boolean cc1) {
		CC1 = cc1;
	}

	public boolean isBA() {
		return BA;
	}

	public void setBA(boolean ba) {
		BA = ba;
	}

	public State_idle getIdle() {
		return idle;
	}

	public State_reduce getReduce() {
		return reduce;
	}

	public void setReduce(State_reduce reduce) {
		this.reduce = reduce;
	}

	public State_intersectSBA getIntersectSBA() {
		return intersectSBA;
	}

	public void setIntersectSBA(State_intersectSBA intersectSBA) {
		this.intersectSBA = intersectSBA;
	}

	public State_intersectSCB getIntersectSCB() {
		return intersectSCB;
	}

	public void setIntersectSCB(State_intersectSCB intersectSCB) {
		this.intersectSCB = intersectSCB;
	}

	public State_intersectSCC1 getIntersectSCC1() {
		return intersectSCC1;
	}

	public void setIntersectSCC1(State_intersectSCC1 intersectSCC1) {
		this.intersectSCC1 = intersectSCC1;
	}
}