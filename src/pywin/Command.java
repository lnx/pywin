package pywin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Command {

	static List<String> executeQuery(String cmd) {
		List<String> lines = new ArrayList<>();
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			if (process.waitFor() == 0) {
				InputStreamReader isr = null;
				try {
					isr = new InputStreamReader(process.getInputStream());
					BufferedReader br = new BufferedReader(isr);
					for (String line = br.readLine(); line != null; line = br.readLine()) {
						lines.add(line.trim());
					}
				} finally {
					if (isr != null) {
						isr.close();
					}
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return lines;
	}

	static int executeUpdate(String cmd) {
		int ret = -1;
		try {
			ret = Runtime.getRuntime().exec(cmd).waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
