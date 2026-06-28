package utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String testName) {

        // Create timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());

        // Create screenshots folder
        File screenshotFolder = new File(
                System.getProperty("user.dir"),
                "test-output/screenshots"
        );

        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs();
        }

        // Create screenshot file
        File screenshotFile = new File(
                screenshotFolder,
                testName + "_" + timeStamp + ".png"
        );

        try {

            File sourceFile =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.FILE);

            FileHandler.copy(sourceFile, screenshotFile);

        } catch (IOException e) {

            e.printStackTrace();

        }

        // Return absolute path
        return screenshotFile.getAbsolutePath();
    }
}