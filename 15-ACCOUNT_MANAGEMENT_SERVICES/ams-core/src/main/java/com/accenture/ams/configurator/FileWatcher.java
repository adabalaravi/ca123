package com.accenture.ams.configurator;

import java.util.Observable;

import com.accenture.ams.configurator.ConfigFileDescriptor;

public class FileWatcher extends Observable {

	private ConfigFileDescriptor dataObj = null;
	private static FileWatcher singleInstance;

	// Singleton design pattern
	// usando questo pattern, siamo sicuri di avere
	// una sola istanza del nostro modello dati
	// e lo rendiamo reperibile agli utilizzatori tramite un metodo che ritorna
	// la sua istanza
	public static FileWatcher getInstance() {
		if (singleInstance == null) {
			singleInstance = new FileWatcher();
		}
		return singleInstance;
	}

	public ConfigFileDescriptor getDataObj() {
		return dataObj;
	}

	public void setMyData(ConfigFileDescriptor dataObj) {
		this.dataObj = dataObj;
		// l'oggetto viene marcato come modificato
		setChanged();
		// ogni volta che i dati cambiano, gli osservanti vengono informati
		// il metodo update verrà chiamato su di essi
		notifyObservers(this.dataObj);

	}

}// fine classe...
