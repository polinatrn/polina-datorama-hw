package com.datoramahw.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LiveMap {
    private final String baseUrl = "https://www.waze.com/livemap";
    private final WebDriver driver;

    public LiveMap(WebDriver driver) {
        this.driver = driver;
        driver.get(baseUrl);
    }

    public WebElement getDestinationTextBox() {
        return driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Share routes'])[1]/following::input[1]"));
    }

    public WebElement getDropDownEntry(int i) {
        return driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Share routes'])[1]/following::li[" + i + "]"));
    }

    public WebElement getDirectionsButton() {
        return driver.findElement(By.id("gtm-poi-card-get-directions"));
    }

    public WebElement getFromTextBox() {
        return driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Directions'])[1]/following::input[1]"));
    }

    public void getDirections(String from, String to) throws InterruptedException {
        WebElement destinationTextBox = getDestinationTextBox();
        destinationTextBox.click();
        destinationTextBox.clear();
        destinationTextBox.sendKeys(to);
        Thread.sleep(1000); //todo change to better wait
        WebElement firstSuggestionInTheDropDown = getDropDownEntry(1);
        firstSuggestionInTheDropDown.click();
        WebElement getDirectionsButton = getDirectionsButton();
        getDirectionsButton.click();
        WebElement fromTextBox = getFromTextBox();
        fromTextBox.clear();
        fromTextBox.sendKeys(from);
        Thread.sleep(1000);
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Directions'])[1]/following::li[1]")).click();
        Thread.sleep(1000);
    }
}
