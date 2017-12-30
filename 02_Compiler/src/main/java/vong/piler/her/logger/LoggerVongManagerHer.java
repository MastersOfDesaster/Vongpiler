package vong.piler.her.logger;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerVongManagerHer {

	private static LoggerVongManagerHer instance;
	
	private LoggerVongManagerHer() {
		PropertyConfigurator.configure("src/main/java/vong/piler/her/logger/log4j.configuration");
	}
	
	public static Logger getLogger(Class calling) {
		if (instance == null)
			instance = new LoggerVongManagerHer();
		return LogManager.getLogger(calling);
	}
}
