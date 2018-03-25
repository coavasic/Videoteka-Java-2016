package utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

public class WindowUtils {
	public static void centirarnjeProzora(Window prozor) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = prozor.getSize();
		prozor.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

}
