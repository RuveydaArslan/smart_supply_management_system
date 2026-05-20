package service.singletion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class LogManager {

	private static LogManager instance;

	private final String dosyYolu = "log.txt";

	private LogManager() {

	}

	public static LogManager getInstance() {
		if (instance == null) {
			instance = new LogManager();
		}
		return instance;
	}

	public void log(String islem, String mesaj) {
		String logSatir = LocalDateTime.now() + " | " + islem + " | " + mesaj;

		//System.out.println("[LOG] " + logSatir);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosyYolu, true))) {
			writer.write(logSatir);
			writer.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
