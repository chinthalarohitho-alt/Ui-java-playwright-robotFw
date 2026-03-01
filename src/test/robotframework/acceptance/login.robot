*** Settings ***
Resource          ../resources/common_hooks.robot
Test Setup        Setup Browser For SauceDemo
Test Teardown     Teardown Browser

*** Keywords ***
I Add To Cart for the ${index} item
    I Add To Cart for the item    ${index}

*** Test Cases ***
Login To SauceDemo Website
    Given I Login With Valid Credentials
    Then The Dashboard Is Displayed

Add To Cart and remove it successfully
    Given I Login With Valid Credentials
    Then The Dashboard Is Displayed
    When I Add To Cart for the 1 item
    And I click on the cart icon
    Then I should be able to remove the item from the cart
    

