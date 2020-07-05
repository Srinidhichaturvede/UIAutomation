@smokeTesting
Feature: Business use case: End to end checkout flow Pillow Purchase using Invalid Credit Card


Scenario: Transaction with Invalid Credit Card.

Given User Launches the Browser
When User enters the Valid application URL and verifies the title of the page
And Selects the Midtrans Pillow from the Home page of the application
And Clicks on Buy Now,Shopping cart information should be displayed on the right side of the home page
And Validate the Shopping Cart with the Product selected by the user and click Checkout button
And Verify order summary page display
And Validate the order summary page with amount selected by the user,click on continue
And Select the payment mode as credit card under select payment page
And Enter the Invalid credit card information details in the credit card page and click on continue
And Click on PAY Now and validate the bank confirmation by enter OTP and click Ok button
Then Capture the Screen shot of failed transaction with details and validate the message and close the browser