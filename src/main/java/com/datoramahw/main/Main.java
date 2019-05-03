package com.datoramahw.main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.datoramahw.pages.LiveMap;
import com.datoramahw.pages.NavigationRoute;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.fail;


public class Main {
    private static WebDriver driver;

    private static final String DATORAMA_ADDRESS = "Yigal Alon St 94, Tel Aviv-Yafo, Israel";
    private static final String MY_ADDRESS = "Shahal 4 hadera";
    private static LiveMap liveMap;

    public static void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "D://geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        liveMap = new LiveMap(driver);
    }

    public static void main(String[] args) throws Exception {
        setUp();
        liveMap.getDirections(MY_ADDRESS, DATORAMA_ADDRESS);

        List<String> leaveTimes = Arrays.asList("07:00", "08:00", "09:00");
        List<NavigationRoute> navigationRoutes = getNavigationRoutesForLeaveTimes(leaveTimes);

        navigationRoutes.sort(null);
        System.out.println("Resulting routes:");
        navigationRoutes.forEach(System.out::println);


        List<String> allRoundLeaveTimes = liveMap.getAllRoundLeaveTimes();
        List<NavigationRoute> routesForAllRoundLeaveTimes = getNavigationRoutesForLeaveTimes(allRoundLeaveTimes);
        System.out.println("--------------------");
        System.out.println("All Routes");
        routesForAllRoundLeaveTimes.forEach(System.out::println);
        routesForAllRoundLeaveTimes.sort(null);
        System.out.println("Shortest:");
        System.out.println(routesForAllRoundLeaveTimes.get(0));
        System.out.println("Longest:");
        System.out.println(routesForAllRoundLeaveTimes.get(routesForAllRoundLeaveTimes.size() - 1));


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
