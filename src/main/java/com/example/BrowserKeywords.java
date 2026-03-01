package com.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.javalib.annotation.ArgumentNames;

@RobotKeywords
public class BrowserKeywords {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    // --- Technical Keywords ---

    @RobotKeyword("Open Browser To Page")
    @ArgumentNames({ "url" })
    public void openBrowserToPage(String url) {
        if (playwright == null) {
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        }
        if (context != null) {
            try {
                context.close();
            } catch (Exception e) {
            }
        }
        context = browser.newContext();
        context.setDefaultTimeout(60000);
        page = context.newPage();
        page.navigate(url);
        page.waitForSelector("#user-name");
    }

    @RobotKeyword("I Enter Credentials")
    @ArgumentNames({ "username", "password" })
    public void iEnterCredentials(String username, String password) {
        page.fill("#user-name", username);
        page.fill("#password", password);
    }

    @RobotKeyword("I Click Login")
    public void iClickLogin() {
        page.click("#login-button");
    }

    @RobotKeyword("The Dashboard Should Be Visible")
    public void theDashboardShouldBeVisible() {
        if (!page.isVisible(".inventory_list")) {
            throw new RuntimeException("Dashboard not visible!");
        }
    }

    @RobotKeyword("The Dashboard Should not Be Visible")
    public void theDashboardShouldNotBeVisible() {
        if (page.isVisible(".inventory_list")) {
            throw new RuntimeException("Dashboard visible when it should not be!");
        }
    }

    @RobotKeyword("Close Simple Browser")
    public void closeSimpleBrowser() {
        if (context != null) {
            context.close();
            context = null;
        }
    }

    @RobotKeyword("I Add To Cart for the item")
    @ArgumentNames({ "index" })
    public void iAddToCartForTheItem(int index) {
        // nth(index - 1) because index is 1-based from Robot but 0-based in Playwright
        page.locator(".inventory_item button").nth(index - 1).click();
    }

    @RobotKeyword("I click on the cart icon")
    public void iClickOnTheCartIcon() {
        page.click(".shopping_cart_link");
    }

    @RobotKeyword("I should be able to remove the item from the cart")
    public void iShouldBeAbleToRemoveTheItemFromTheCart() {
        page.getByText("Remove").click();
    }
    // --- BDD Keywords (directly usable in .robot files) ---

    @RobotKeyword("I Navigate To Login Page")
    public void iNavigateToLoginPage() {
        openBrowserToPage("https://www.saucedemo.com/");
    }

    @RobotKeyword("I Login With Valid Credentials")
    public void iLoginWithValidCredentials() {
        iEnterCredentials("standard_user", "secret_sauce");
        iClickLogin();
    }

    @RobotKeyword("The Dashboard Is Displayed")
    public void theDashboardIsDisplayed() {
        theDashboardShouldBeVisible();
    }
}
