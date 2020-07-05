/**
 * @author Srinidhi Chaturvede
 *
 */
package test.framework.glue;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import test.utility.DriverInitialization;

/**
 * This class will perform end to end flow of pillow purchase transaction using
 * valid credit card
 */

public class VoiPillowSuccess extends DriverInitialization {

	private String Productname = "";
	private String Pillowname = "";
	private String PillowPrice = "";
	private String prc = "";
	private String expectedTitle = "Sample Store";

	/**
	 * This method is used to launch the browser
	 */
	@Given("^User Launch the Browser$")
	public void launchBrowser() {
		BrowserInitialization();
	}

	/**
	 * This method is used to validate the tile of the application
	 */
	@When("^User enters the Valid application URL and verify the title of the page$")
	public void verifyTitle() {
		ApplicationURL();
		String actualTitle = driver.getTitle();
		System.out.println(actualTitle);
		Assert.assertEquals(actualTitle, expectedTitle);
		System.out.println("===========================================");
	}

	/**
	 * This method is used to fetch the details of the product
	 */
	@And("^Selects the Midtrans Pillow from the Landing page of the application$")
	public void getPillowDetails() {
		Pillowname = driver.findElement(By.xpath(Voi.getProperty("Pillowname"))).getText();
		PillowPrice = driver.findElement(By.xpath(Voi.getProperty("Pillowprice"))).getText();
	}

	/**
	 * This method is used to navigate to shopping cart page
	 */
	@And("^Clicks on Buy Now button, Shopping cart information should be displayed on the right side of the home page$")
	public void getShoppingCart() {
		driver.findElement(By.xpath(Voi.getProperty("BuyNow"))).click();
		if (driver.findElement(By.xpath(Voi.getProperty("SHOPPINGCART"))).isDisplayed()) {
			System.out.println("Shopping cart page is displayed successfully");
		} else {
			System.out.println("Error in display of shopping page");
		}
		System.out.println("===========================================");
	}

	/**
	 * This method is used to validate the product and proceed to Checkout
	 */
	@And("^Validate the Shopping Cart with the Product selected by the user,click on Checkout button$")
	public void getProductDesc() {
		fetchTable();
		if (Pillowname.equals(Productname) && PillowPrice.equals(prc)) {
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(Voi.getProperty("CHECKOUT")))));
			driver.findElement(By.xpath(Voi.getProperty("CHECKOUT"))).click();
		} else {
			System.out.println("Error in product selection and the price");
		}
	}

	/**
	 * This method is used to verify Order summary page
	 */
	@And("^Verify order summary confirmation page displayed$")
	public void checkout() {
		driver.switchTo().frame(0);
		if (driver.findElement(By.xpath(Voi.getProperty("Order_Summary"))).isDisplayed()) {
			System.out.println("Order Summary page displayed successfully");
		} else {
			System.out.println("Order summary page not displayed");
		}
		System.out.println("===========================================");
	}

	/**
	 * This method is used to Validate the Amount before making payment
	 */
	@And("^Validate the order summary page with the product name and the amount selected by the user,click on continue$")
	public void placeOrder() {
		try {
			WebElement baseTable = driver.findElement(By.tagName("table"));
			WebElement tableRow = baseTable.findElement(By.xpath(Voi.getProperty("OTable")));
			String ProductnameOC = tableRow.getText();
			System.out.println("Product name in the OC page : " + ProductnameOC);
			Thread.sleep(2000);
			String Totalamount = driver.findElement(By.xpath(Voi.getProperty("OC_Amount"))).getText();
			if (Totalamount.equals(PillowPrice)) {
				System.out.println("No Miss match in the product price in Order summary page");
				Thread.sleep(1000);
				driver.findElement(By.xpath(Voi.getProperty("OC_Continue"))).click();
				WebDriverWait wait = new WebDriverWait(driver, 20);
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(Voi.getProperty("PaymentCC")))));
			}

		} catch (Exception e) {
			System.out.println("Error in order summary page");
		}
		System.out.println("===========================================");
	}

	/**
	 * This method is used to payment mode for transaction
	 */
	@And("^Select the payment mode as credit card in the select payment page$")
	public void getPaymentMode() {
		driver.findElement(By.xpath(Voi.getProperty("PaymentCC"))).click();
		System.out.println("Sucess - Select the payment mode as credit card in the select payment page");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Error on payment page");
		}
	}

	/**
	 * This method is used to validate the credit card details
	 */

	@And("^Enter the valid credit card information details in the credit card page and click on continue$")
	public void getCreditCardDetails() {
		/*
		 * if
		 * (driver.findElement(By.xpath(Voi.getProperty("Promocardcheck"))).isEnabled())
		 * { driver.findElement(By.xpath(Voi.getProperty("Promocardcheck"))).click(); }
		 */
		String Payableamt = driver.findElement(By.xpath(Voi.getProperty("CCAmount"))).getText();
		System.out.println("Payable amount is : "+Payableamt);
		if (Payableamt.equals(PillowPrice)) {
			boolean isValid = true;
			getCardDetails(isValid);
		} else
			System.out.println("Abort the transaction due to miss match in price");
	}

	/**
	 * This method is used to validate the payment transaction via OTP
	 * @throws InterruptedException 
	 */

	@And("^Click on PAY Now and wait until the Issuing bank information details is displayed,Enter OTP and Click Ok Button$")
	public void paymentConfirm() throws InterruptedException {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Error in payment trnsaction");
		}
		driver.switchTo().frame(0);
		if (driver.findElement(By.xpath(Voi.getProperty("Payment_detail"))).isDisplayed()) {
			System.out.println("Bank payment page displayed successfully");
			Thread.sleep(1000);
			driver.findElement(By.xpath(Voi.getProperty("OTP"))).sendKeys(Voi.getProperty("BankOTP"));
			driver.findElement(By.xpath(Voi.getProperty("PaymentConfirmation"))).click();
				Thread.sleep(1000);
				System.out.println("Success entering the OTP on Bank issuing payment page");
		} else {
			System.out.println("Failed to open Bank issuing payment page");
			captureScreenShot();
			Assert.assertFalse(false);
			driver.close();
		}
	}

	/**
	 * This method is used to capture screen shot for Success transaction
	 */

	@Then("^Capture the Screen shot of the success transaction with details and validate the acknowledgement$")
	public void orderConfirmation() {
		captureScreenShot();
		driver.switchTo().defaultContent();
		/*
		 * WebDriverWait wait = new WebDriverWait(driver, 40);
		 * wait.until(ExpectedConditions
		 * .visibilityOf(driver.findElement(By.xpath(Voi.getProperty(
		 * "Transaction_confirmation")))));
		 */
		String paymentack = driver.findElement(By.xpath(Voi.getProperty("Transaction_confirmation"))).getText();
		Assert.assertTrue(paymentack, true);
		System.out.println("Completes the End to End flow of the Success transaction");
		System.out.println("===========================================");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			System.out.println("Error on placing order");
		}
		driver.close();
	}

	/**
	 * Method to read the table information in Order summary page
	 */

	public void fetchTable() {
		WebElement baseTable = driver.findElement(By.tagName("table"));

		// To find first row data of table
		WebElement tableRow = baseTable.findElement(By.xpath(Voi.getProperty("Table_row1")));
		Productname = tableRow.getText();
		System.out.println("Product Name in Shopping Cart : " + Productname);

		// to get first row's 2nd column data
		WebElement quantity = baseTable.findElement(By.xpath(Voi.getProperty("Table_row2")));
		String qty = quantity.getText();
		System.out.println("Number of quantity in Shopping Cart : " + qty);

		// to get second row's 3rd column data
		WebElement price = baseTable.findElement(By.xpath(Voi.getProperty("Table_row3")));
		prc = price.getText();
		System.out.println("Total price in the shopping cart : " + prc);
	}

	/**
	 * Method to fetch credit card details
	 */

	public void getCardDetails(boolean isValid) {
		String cardNumber = null;
		try {
			if (isValid) {
				cardNumber = Voi.getProperty("Validcardnumber");
			} else {
				cardNumber = Voi.getProperty("Invalidcardnumber");
			}
			driver.findElement(By.xpath(Voi.getProperty("CC_Holder"))).sendKeys(cardNumber);
			driver.findElement(By.xpath(Voi.getProperty("Date_Holder"))).sendKeys(Voi.getProperty("ExpiryDate"));
			driver.findElement(By.xpath(Voi.getProperty("CVV_Holder"))).sendKeys(Voi.getProperty("CVV"));
			driver.findElement(By.xpath(Voi.getProperty("PayNow"))).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
