package de.uniol.ui.fsm.model.states;

import de.uniol.ui.fsm.model.FSM;

public class NullState extends State {

	public NullState(FSM fsm) {
		super("null", fsm, null);
		// Activate this state
		entry(this);
	}

	protected void entryAction() {
		// no-op
	}

	protected void exitAction() {
		// no-op
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof NullState) {
			return ((NullState) obj).getFsm() == getFsm();
		}
		return false;
	}
}