package microscan.Pages.reports;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {

    public static String capture(WebDriver driver) {
        try {
            File folder = new File("screenshots");
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String fileName = "FAIL_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".png";
            String path = "screenshots/" + fileName;
            File des = new File(path);
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(source.toPath(), des.toPath());
            return path;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}