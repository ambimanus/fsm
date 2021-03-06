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
 * FSM is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FSM.  If not, see <http://www.gnu.org/licenses/>.
 */
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