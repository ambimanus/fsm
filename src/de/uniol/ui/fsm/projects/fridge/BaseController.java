package de.uniol.ui.fsm.projects.fridge;

import de.uniol.ui.fsm.model.FSM;

public class BaseController extends FSM {

	public final static String EV_COOLING = "ev_cooling";
	public final static String EV_WARMING = "ev_warming";

	private Fridge fridge = new Fridge(this, 5.5);
	private double Tmin = 3.0;
	private double Tmax = 8.0;

	private double Tfrom;
	private double Tdest;
	private double tauSwitch;
	private double counter;
	private double tauCooling = 37.0;
	private double tauWarming = 129.0;
	
	private State_cooling cooling;
	private State_c_polling c_polling;
	private State_warming warming;
	private State_w_polling w_polling;

	@SuppressWarnings("unused")
	public BaseController() {
		super("BaseController");

		cooling = new State_cooling(this);
		c_polling = new State_c_polling(this, cooling);
		warming = new State_warming(this);
		w_polling = new State_w_polling(this, warming);

		T_default_TO_cooling t_def = new T_default_TO_cooling(this, cooling);

		T_default_cooling_TO_c_polling t_def_c = new T_default_cooling_TO_c_polling(
				this, cooling, c_polling);
		T_c_polling_TO_c_polling t_c_c = new T_c_polling_TO_c_polling(this,
				c_polling);
		T_c_polling_TO_warming t_c_w = new T_c_polling_TO_warming(this,
				c_polling, warming);

		T_default_warming_TO_w_polling t_def_w = new T_default_warming_TO_w_polling(
				this, warming, w_polling);
		T_w_polling_TO_w_polling t_w_w = new T_w_polling_TO_w_polling(this,
				w_polling);
		T_w_polling_TO_cooling t_w_c = new T_w_polling_TO_cooling(this,
				w_polling, cooling);
	}

	public double ac() {
		return (Tmin - Tmax) / tauCooling;
	}

	public double aw() {
		return (Tmax - Tmin) / tauWarming;
	}

	public double tau_reqc(double Tfrom, double Tdest) {
		return (Tdest - Tfrom) / ac();
	}

	public double tau_reqw(double Tfrom, double Tdest) {
		return (Tdest - Tfrom) / aw();
	}

	public Fridge getFridge() {
		return fridge;
	}

	public double getTmin() {
		return Tmin;
	}

	public double getTmax() {
		return Tmax;
	}

	public double getTfrom() {
		return Tfrom;
	}

	public void setTFrom(double from) {
		Tfrom = from;
	}

	public double getTdest() {
		return Tdest;
	}

	public void setTDest(double dest) {
		Tdest = dest;
	}

	public double getTauSwitch() {
		return tauSwitch;
	}

	public void setTauSwitch(double tauSwitch) {
		this.tauSwitch = tauSwitch;
	}

	public double getCounter() {
		return counter;
	}

	public void setCounter(double counter) {
		this.counter = counter;
	}

	public double getTauCooling() {
		return tauCooling;
	}

	public void setTauCooling(double tauCooling) {
		this.tauCooling = tauCooling;
	}

	public double getTauWarming() {
		return tauWarming;
	}

	public void setTauWarming(double tauWarming) {
		this.tauWarming = tauWarming;
	}

	public State_cooling getCooling() {
		return cooling;
	}

	public State_c_polling getC_polling() {
		return c_polling;
	}

	public State_warming getWarming() {
		return warming;
	}

	public State_w_polling getW_polling() {
		return w_polling;
	}
}