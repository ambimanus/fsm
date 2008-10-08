package de.uniol.ui.fsm.model.transitions;

import java.util.Arrays;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.SignalListener;
import de.uniol.ui.fsm.model.states.State;

public abstract class Transition implements SignalListener {

	private FSM fsm;
	private State source;
	private State dest;
	private String signal = null;
	
	public Transition(FSM fsm, String signal, State source, State dest) {
		this.dest = dest;
		this.fsm = fsm;
		this.signal = signal;
		this.source = source;
		
		fsm.addSignalListener(this);
	}

	public FSM getFsm() {
		return fsm;
	}

	public State getSource() {
		return source;
	}

	public State getDest() {
		return dest;
	}

	public String getSignal() {
		return signal;
	}

	public boolean signalReceived(String name, Object sender, Object... params) {
		if (source.isActive()
				&& sender.equals(source)
				&& (signal == name || (signal != null && signal.equals(name)))
				&& guard()) {
			System.out.println("Time=" + fsm.getClock() + " - "
					+ this.getClass().getSimpleName() + " received Signal <"
					+ signal + "> from " + sender.getClass().getSimpleName()
					+ ", params=" + Arrays.toString(params));
			source.exit(dest);
			action(params);
			dest.entry(dest);
			return true;
		}
		return false;
	}

	public abstract boolean guard();
	public abstract void action(Object... params);
}