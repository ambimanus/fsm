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