package com.datoramahw.main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.datoramahw.email.EmailSender;
import com.datoramahw.pages.LiveMap;
import com.datoramahw.pages.NavigationRoute;
import com.google.common.base.Strings;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.fail;


public class Main {
    private static WebDriver driver;

    private static final String DATORAMA_ADDRESS = "Yigal Alon St 94, Tel Aviv-Yafo, Israel";
    private static final String MY_ADDRESS = "Shahal 4, Hadera";
    private static LiveMap liveMap;

    public static void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "D://geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        liveMap = new LiveMap(driver);
    }

    public static void main(String[] args) throws Exception {
        StringBuilder message = new StringBuilder("Results for navigation from " + MY_ADDRESS + " to " + DATORAMA_ADDRESS + "\n\n");
        setUp();
        liveMap.getDirections(MY_ADDRESS, DATORAMA_ADDRESS);

        List<String> leaveTimes = Arrays.asList("07:00", "08:00", "09:00");
        List<NavigationRoute> navigationRoutes = getNavigationRoutesForLeaveTimes(leaveTimes);

        navigationRoutes.sort(null);
        message.append("Resulting routes for hours ").append(String.join(",", leaveTimes)).append(":\n");
        navigationRoutes.forEach(route -> message.append(route.toString()));


        List<String> allRoundLeaveTimes = liveMap.getAllRoundLeaveTimes();
        List<NavigationRoute> routesForAllRoundLeaveTimes = getNavigationRoutesForLeaveTimes(allRoundLeaveTimes);
        message.append("\n--------------------\n");
        message.append("All Routes\n");
        routesForAllRoundLeaveTimes.forEach(route -> message.append(route.toString()));
        routesForAllRoundLeaveTimes.sort(null);
        message.append("\nShortest:\n");
        message.append(routesForAllRoundLeaveTimes.get(0));
        message.append("Longest:\n");
        message.append(routesForAllRoundLeaveTimes.get(routesForAllRoundLeaveTimes.size() - 1));

        new EmailSender().send(args[0], message.toString());

        tearDown();
    }

    private static List<NavigationRoute> getNavigationRoutesForLeaveTimes(List<String> leaveTimes) throws InterruptedException {
        List<NavigationRoute> navigationRoutes = new ArrayList<>();
        for (String leaveTime : leaveTimes) {
            liveMap.setLeaveTime(leaveTime);
            navigationRoutes.addAll(liveMap.getNavigationRoutes(leaveTime));
        }
        return navigationRoutes;
    }

    public static void tearDown() throws Exception {
        driver.quit();
    }

}
