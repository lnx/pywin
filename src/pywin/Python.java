package pywin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Python {

	final static List<String> BASES = new ArrayList<>();

	static {
		BASES.add("/");
		BASES.add("/program files");
		BASES.add("/program files (x86)");
	}

	static List<String> getPythons() {
		Set<String> paths = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
		searchDirectory(paths);
		searchRegistry(paths);
		return new ArrayList<>(paths);
	}

	static void searchDirectory(Set<String> paths) {
		searchDirectory(new File(System.getProperty("user.home")), paths);
		for (char c = 'c'; c <= 'g'; ++c) {
			for (String base : BASES) {
				File root = new File(c + ":" + base);
				searchDirectory(root, paths);
			}
		}
	}

	static void searchDirectory(File root, Set<String> paths) {
		if (root.isDirectory()) {
			File[] dirs = root.listFiles();
			for (File dir : dirs) {
				if (containsPython(dir)) {
					paths.add(dir.getAbsolutePath().toLowerCase());
				}
			}
		}
	}

	static boolean containsPython(File dir) {
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			if (files != null) {
				for (File file : files) {
					if (file.isFile() && file.getName().equals("python.exe")) {
						return true;
					}
				}
			}
		}
		return false;
	}

	static void searchRegistry(Set<String> paths) {
		List<String> list = Registry.queryList("hklm\\software\\python\\pythoncore");
		for (String key : list) {
			String data = Registry.queryDefault(key + "\\installpath");
			if (data != null) {
				File file = new File(data);
				if (file.isDirectory()) {
					paths.add(file.getAbsolutePath().toLowerCase());
				}
			}
		}
	}

}
