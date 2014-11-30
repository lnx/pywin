package pywin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Registry {

	static String query(String key, String value) {
		String data = null;
		String cmd = String.format("reg query \"%s\" /v \"%s\"", key, value);
		List<String> lines = Command.executeQuery(cmd);
		for (String line : lines) {
			if (line.contains(value)) {
				String[] splits = line.split("[ ]+");
				if (splits.length >= 3) {
					data = String.join(" ", Arrays.copyOfRange(splits, 2, splits.length));
					break;
				}
			}
		}
		return data;
	}

	static String queryDefault(String key) {
		String data = null;
		String cmd = String.format("reg query \"%s\" /ve", key);
		List<String> lines = Command.executeQuery(cmd);
		for (String line : lines) {
			String[] splits = line.split("[ ]+");
			if (splits.length >= 3) {
				data = String.join(" ", Arrays.copyOfRange(splits, 2, splits.length));
				break;
			}
		}
		return data;
	}

	static List<String> queryList(String key) {
		List<String> ret = new ArrayList<>();
		String cmd = String.format("reg query \"%s\"", key);
		List<String> lines = Command.executeQuery(cmd);
		for (String line : lines) {
			if (!line.equals("")) {
				ret.add(line);
			}
		}
		return ret;
	}

	static int add(String key, String value, String data) {
		String cmd = String.format("reg add \"%s\" /v \"%s\" /d \"%s\" /f", key, value, data);
		return Command.executeUpdate(cmd);
	}

}
