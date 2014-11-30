package pywin;

import com.sun.jna.Native;

public class Windows {

	static final int HWND_BROADCAST = 0xffff;
	static final int WM_SETTINGCHANGE = 0x001A;

	interface User32 extends com.sun.jna.platform.win32.User32 {

		User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

		int SendMessageA(int hWnd, int msg, int wParam, String lParam);

	}

	static void notifyToChange() {
		User32.INSTANCE.SendMessageA(HWND_BROADCAST, WM_SETTINGCHANGE, 0, "Environment");
	}

}
