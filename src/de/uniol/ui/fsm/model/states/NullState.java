package de.uniol.ui.fsm.model.states;

import de.uniol.ui.fsm.model.FSM;

/**
 * This class represents a special state which has no parent and no actions and
 * exists just to be able to dispatch default transitions. This state will
 * immediately be active after creation.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
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