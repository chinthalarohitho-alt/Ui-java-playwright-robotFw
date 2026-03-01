*** Settings ***
Library    com.example.BrowserKeywords

*** Keywords ***
Setup Browser For SauceDemo    Open Browser To Page    https://www.saucedemo.com/
Teardown Browser               Close Simple Browser

   