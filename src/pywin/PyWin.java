package pywin;

import java.util.List;
import java.util.Scanner;

public class PyWin {

	final static String REG_KEY_ENV = "hkcu\\environment";
	final static String REG_VAL_PATH = "Path";

	static void load() {
		System.out.println("\nWelcome to pywin!\n");
		List<String> pythons = Python.getPythons();
		if (pythons.isEmpty()) {
			System.out.println("Seems you didn't install a python yet. Please install them first.\n");
		} else {
			System.out.println("These are the pythons on your system:\n");
			String path = Registry.query(REG_KEY_ENV, REG_VAL_PATH);
			for (int i = 0, size = pythons.size(); i < size; ++i) {
				System.out.print(i + " - " + pythons.get(i));
				if (path != null && path.startsWith(pythons.get(i))) {
					System.out.print("  <- currently used");
				}
				System.out.println();
			}
			System.out.print("\nChoose the version you want (please input the number): ");
			Scanner scanner = new Scanner(System.in);
			int choice = getChoice(scanner);
			while (choice < 0 || choice >= pythons.size()) {
				System.out.println("\nSorry, you have to choose one from these candidates:\n");
				for (int i = 0, size = pythons.size(); i < size; ++i) {
					System.out.println(i + " - " + pythons.get(i));
				}
				System.out.print("\nOr type 'exit' to escape, your choice is: ");
				choice = getChoice(scanner);
			}
			scanner.close();
			update(pythons, choice);
			Windows.notifyToChange();
			System.out.println(String.format(
					"\nGreat job! The environment has been switched to '%s'. Before using it, please restart your terminal.\n\nGood luck!\n",
					pythons.get(choice)));
		}
	}

	static int getChoice(Scanner scanner) {
		int ret = -1;
		String line = scanner.nextLine();
		if (line != null) {
			line = line.trim();
			if (line.equals("exit")) {
				System.out.println("\nBye!\n");
				System.exit(0);
			}
			try {
				ret = Integer.parseInt(line);
			} catch (Exception e) {
			}
		}
		return ret;
	}

	static void update(List<String> pythons, int choice) {
		String path = Registry.query(REG_KEY_ENV, REG_VAL_PATH);
		if (path == null) {
			path = "";
		} else {
			for (String python : pythons) {
				path = path.replaceAll(String.format("%s(\\Scripts)?;", python).replaceAll("\\\\", "\\\\\\\\"), "");
			}
		}
		Registry.add(REG_KEY_ENV, REG_VAL_PATH, String.format("%s;%s\\Scripts;%s", pythons.get(choice), pythons.get(choice), path));
	}

	public static void main(String[] args) {
		load();
	}

}
