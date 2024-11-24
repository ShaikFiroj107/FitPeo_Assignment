Feature: Validating FitPeo Assignment page test cases
#STEPS:1&2 Navigate to the FitPeo Homepage and Navigate to the Revenue Calculator Page from home page
Scenario: Verifying user able to navigate revenue calculator website or not.
	Given User lands on Fit Peo website
  When verify user lands fit peo website successfully or not
  Then navigate to revenue calculator website page from home page
  And verify sucessfully user navigate revenue calculator website from home page or not
  
#STEPS:3&4 Scroll Down to the Slider section and Adjust the Slider
#Use targetValue as 9500 then this slider pixel moving at range of 817 if use 9600 it's moves 823 range
Scenario Outline: Verifying slider adjusting according to input value range or not.
	Given User lands on Fit Peo website
  When verify user lands fit peo website successfully or not
  Then navigate to revenue calculator website page from home page
  And verify sucessfully user navigate revenue calculator website from home page or not
  Then Store slider WebElement value
  Then Scroll until slider section visible
  Then drag and adjust slider value until <targetValue> 
  
  Examples:
  |targetValue|
  |820        |
   
#STEPS:5&6 Update the Text Field and Validate Slider Value
#This test case also failing at validation part because of slider updater value automattically changing 2000
Scenario Outline: Verifying slider adjusting according to input value range or not.
	Given User lands on Fit Peo website
  When verify user lands fit peo website successfully or not
  Then navigate to revenue calculator website page from home page
  And verify sucessfully user navigate revenue calculator website from home page or not
  Then Store slider WebElement value
  Then Scroll until slider section visible
  And Enter <targetValue> in slider box field
  Then Validate updated slider value with <targetValue>
  
  Examples:
  |targetValue|
  |560        |
  
#STEP:7,8,9 Select CPT Codes and Validate Total Recurring Reimbursement
#In this method I have draged slider until 817 value and validate reimbursement according to that slide have some issue in this website
Scenario Outline: Validate Total Recurring Reimbursement.
	Given User lands on Fit Peo website
  When verify user lands fit peo website successfully or not
  Then navigate to revenue calculator website page from home page
  And verify sucessfully user navigate revenue calculator website from home page or not
  Then Scroll until slider section visible
  Then Store slider WebElement value
  Then drag and adjust slider value until <targetValue>
  And Scroll and click all required CTP check boxes
  And store reimbursement value after selected required checkboxes
  Then Validate getted reimbursement value with "<ExpectedReimbursement>"
  
  Examples:
  |targetValue|ExpectedReimbursement|
  |9500       |$110295              |
  
  
  
  