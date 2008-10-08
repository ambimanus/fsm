package de.uniol.ui.fsm.model;

public interface SignalListener {

	public boolean signalReceived(String name, Object sender, Object... params);
}