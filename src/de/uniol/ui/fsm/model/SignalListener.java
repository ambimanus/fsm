package de.uniol.ui.fsm.model;

/**
 * This interface defines the ability to receive signals.
 * 
 * @author <a href=
 *         "mailto:Christian%20Hinrichs%20%3Cchristian.hinrichs@uni-oldenburg.de%3E"
 *         >Christian Hinrichs, christian.hinrichs@uni-oldenburg.de</a>
 * 
 */
public interface SignalListener {

	public boolean signalReceived(String name, Object sender, Object... params);
}