package com.ntt.acoe.framework.selenium.util;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.ntt.acoe.framework.selenium.report.Reporting;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */

public class AWTScreenshot {
	static int sequence = 1;

	public static void captureScreenshot(String filename) {
		try {
			Robot robot = new Robot();
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Dimension dim = toolkit.getScreenSize();
			BufferedImage bi = robot.createScreenCapture(new Rectangle(dim.width, dim.height));
			try {
				filename = filename.replace(".png", "_" + Reporting.currentTestCaseNo + ".png");
				File f = new File(filename);
				// if(f.exists()){
				// f.deleteOnExit();
				// }
				ImageIO.write(bi, "png", f);
				sequence = sequence + 1;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
		}
	}
}
