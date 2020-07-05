@smokeTesting
Feature: Business use case: End to end flow Pillow Purchase using valid Credit Card. 


Scenario: Transaction using valid Credit Card

Given User Launch the Browser
When User enters the Valid application URL and verify the title of the page
And Selects the Midtrans Pillow from the Landing page of the application
And Clicks on Buy Now button, Shopping cart information should be displayed on the right side of the home page
And Validate the Shopping Cart with the Product selected by the user,click on Checkout button
And Verify order summary confirmation page displayed
And Validate the order summary page with the product name and the amount selected by the user,click on continue
And Select the payment mode as credit card in the select payment page
And Enter the valid credit card information details in the credit card page and click on continue
And Click on PAY Now and wait until the Issuing bank information details is displayed,Enter OTP and Click Ok Button
Then Capture the Screen shot of the success transaction with details and validate the acknowledgement