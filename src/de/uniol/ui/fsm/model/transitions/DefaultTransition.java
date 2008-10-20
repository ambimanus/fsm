package de.uniol.ui.fsm.model.transitions;

import de.uniol.ui.fsm.model.FSM;
import de.uniol.ui.fsm.model.states.NullState;
import de.uniol.ui.fsm.model.states.State;

/**
 * Special transition which has no real source state (a {@link NullState} will
 * be used). Represents the default transition of an FSM.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
public class DefaultTransition extends Transition {

	public DefaultTransition(FSM fsm, State dest) {
		super(fsm, null, new NullState(fsm), dest);
	}

	public void action(Object... params) {
		// no-op
	}

	public boolean guard() {
		// no guard
		return true;
	}
}