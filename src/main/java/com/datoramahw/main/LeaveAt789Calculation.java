package com.datoramahw.main;
import java.util.concurrent.TimeUnit;

import com.datoramahw.pages.LiveMap;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LeaveAt789Calculation {
    private WebDriver driver;

    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private final String datoramaAddress = "Yigal Alon St 94, Tel Aviv-Yafo, Israel";
    private final String myAddress = "Shahal 4 hadera";

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "D://geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        LiveMap liveMap = new LiveMap(driver);
        liveMap.getDirections(myAddress, datoramaAddress);
        Thread.sleep(1000);
        Select leaveNowOrAt = new Select(driver.findElement(By.className("wm-select is-now-or-at")).findElement(By.tagName("select")));
        leaveNowOrAt.selectByValue("Leave at");
        Select time = new Select(driver.findElement(By.className("wm-select is-at")).findElement(By.tagName("select")));
        time.selectByValue("07:00");
//        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Yigal Alon St 94'])[1]/following::select[2]")).click();
//        new Select(driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Yigal Alon St 94'])[1]/following::select[2]"))).selectByVisibleText("07:00");
        Thread.sleep(1000);

    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
