*** Settings ***
Resource          ../resources/common_hooks.robot
Test Setup        Setup Browser For SauceDemo
Test Teardown     Teardown Browser

*** Test Cases ***
Standard Valid Login
    [Template]    Login With Credentials
    standard_user             secret_sauce

Invalid Login Scenarios
    [Template]    Login With invalid Credentials
    locked_out_user           secret_sauce
    standard_user             wrong_password

*** Keywords ***
Login With Credentials
    [Arguments]    ${username}    ${password}
    I Enter Credentials    ${username}    ${password}
    I Click Login
    The Dashboard Should Be Visible

Login With invalid Credentials
    [Arguments]    ${username}    ${password}
    I Enter Credentials    ${username}    ${password}
    I Click Login
    The Dashboard Should not Be Visible