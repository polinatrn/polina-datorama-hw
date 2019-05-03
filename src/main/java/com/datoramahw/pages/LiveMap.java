package com.datoramahw.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LiveMap {
    private final String baseUrl = "https://www.waze.com/livemap";
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final String lengthClassName = "wm-route-item__length";
    private final String timeClassName = "wm-route-item__time";

    public LiveMap(WebDriver driver) {
        this.driver = driver;
        driver.get(baseUrl);
        wait = new WebDriverWait(driver, 10);
    }

    private WebElement getDestinationTextBox() {
        
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Share routes'])[1]/following::input[1]")));
    }

    private WebElement getDropDownEntry(int i) {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Share routes'])[1]/following::li[" + i + "]")));
    }

    private WebElement getDirectionsButton() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id("gtm-poi-card-get-directions")));
    }

    private WebElement getFromTextBox() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Directions'])[1]/following::input[1]")));
    }

    private Select getIsNowOrAtSelect() {
        WebElement parentDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("is-now-or-at")));
        return new Select(parentDiv.findElement(By.tagName("select")));
    }

    public void getDirections(String from, String to) throws InterruptedException {
        WebElement destinationTextBox = getDestinationTextBox();
        destinationTextBox.click();
        destinationTextBox.clear();
        destinationTextBox.sendKeys(to);
        WebElement firstSuggestionInTheDropDown = getDropDownEntry(1);
        firstSuggestionInTheDropDown.click();
        WebElement getDirectionsButton = getDirectionsButton();
        getDirectionsButton.click();
        WebElement fromTextBox = getFromTextBox();
        fromTextBox.clear();
        fromTextBox.sendKeys(from);
        getFirstDropDownElementInFromList().click();
    }

    private WebElement getFirstDropDownElementInFromList() {
        return wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Directions'])[1]/following::li[1]")));
    }

    public void setLeaveTime(String timeToSelect) {
        Select leaveNowOrAt = getIsNowOrAtSelect();
        leaveNowOrAt.selectByVisibleText("Leave at");
        Select time = new Select(getIsAtDropDownDiv().findElement(By.tagName("select")));
        time.selectByValue(timeToSelect);
    }

    private WebElement getIsAtDropDownDiv() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.className("is-at")));
    }

    public List<NavigationRoute> getNavigationRoutes(String leaveAt) throws InterruptedException {
        List<NavigationRoute> result = new ArrayList<>();
        String titleClassName = "wm-route-item__title";
        List<WebElement> routeElements = driver.findElements(By.className(titleClassName));
        int i = 0;
        try {
            for (WebElement routeElement : routeElements) {
                addRoutes(leaveAt, result, routeElement);
                i++;
            }
        } catch (StaleElementReferenceException staleException) {
            try {
                routeElements = driver.findElements(By.className(titleClassName));
                for (; i < routeElements.size(); i++) {
                    addRoutes(leaveAt, result, routeElements.get(i));
                }
            } catch (StaleElementReferenceException staleException2) {
                routeElements = driver.findElements(By.className(titleClassName));
                for (; i < routeElements.size(); i++) {
                    addRoutes(leaveAt, result, routeElements.get(i));
                }
            }
        }
        return result;
    }

    private void addRoutes(String leaveAt, List<NavigationRoute> result, WebElement routeElement) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(timeClassName)));
        String time = routeElement.findElement(By.className(timeClassName)).getAttribute("innerText");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className(lengthClassName)));
        String length = routeElement.findElement(By.className(lengthClassName)).getAttribute("innerText");
        NavigationRoute navigationRoute = new NavigationRoute(time, length, leaveAt);
        result.add(navigationRoute);
    }

    public List<String> getAllRoundLeaveTimes() {
        List<WebElement> options = getIsAtDropDownDiv().findElements(By.tagName("option"));
        return options.stream().map(WebElement::getText)
                .filter(text -> text.endsWith("00"))
                .collect(Collectors.toList());

    }
}
