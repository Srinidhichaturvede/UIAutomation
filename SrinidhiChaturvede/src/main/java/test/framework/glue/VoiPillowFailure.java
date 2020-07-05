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
 * This class will perform end to end flow of pillow purchase transaction using Invalid credit card
 */

public class VoiPillowFailure extends DriverInitialization {

	private String Productname = "";
	private String Pillowname = "";
	private String PillowPrice = "";
	private String prc = "";
	private String expectedTitle = "Sample Store";

	/**
	 * This method is used to launch the browser
	 */
	
	@Given("^User Launches the Browser$")
	public void launchBrowser() {
		BrowserInitialization();
	}
	
	/**
	 * This method is used to validate the tile of the application
	 */
	
	@When("^User enters the Valid application URL and verifies the title of the page$")
	public void verifyTitle() {
		ApplicationURL();
		String actualTitle = driver.getTitle();
		System.out.println(actualTitle);
		Assert.assertEquals(actualTitle, expectedTitle);
		System.out.println("===========================================================");
	}
	
	/**
	 * This method is used to fetch the details of the product
	 */
	
	@And("^Selects the Midtrans Pillow from the Home page of the application$")
	public void getPillowDetails() {
		Pillowname = driver.findElement(By.xpath(Voi.getProperty("Pillowname"))).getText();
		PillowPrice = driver.findElement(By.xpath(Voi.getProperty("Pillowprice"))).getText();
	}

	/**
	 * This method is used to navigate to shopping cart page
	 */
	
	@And("^Clicks on Buy Now,Shopping cart information should be displayed on the right side of the home page$")
	public void getShoppingCart() {
		driver.findElement(By.xpath(Voi.getProperty("BuyNow"))).click();
		if (driver.findElement(By.xpath(Voi.getProperty("SHOPPINGCART"))).isDisplayed()) {
			System.out.println("Shopping cart page is displayed successfully");
		} else {
			System.out.println("Error displaying shopping page");
		}
		System.out.println("===========================================================");
	}

	/**
	 * This method is used to validate the product and proceed to Checkout
	 */
	
	@And("^Validate the Shopping Cart with the Product selected by the user and click Checkout button$")
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
	
	@And("^Verify order summary page display$")
	public void checkout() {
		driver.switchTo().frame(0);
		if (driver.findElement(By.xpath(Voi.getProperty("Order_Summary"))).isDisplayed()) {
			System.out.println("Order Summary page displayed successfully");
		} else {
			System.out.println("Order summary page not displayed");
		}
		System.out.println("===========================================================");
	}

	/**
	 * This method is used to Validate the Amount before making payment
	 */
	
	@And("^Validate the order summary page with amount selected by the user,click on continue$")
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
			else
			{
				System.out.println("Abort the transaction due to miss match in the price");
			}

		} catch (Exception e) {
			System.out.println("Error in order summary page");
		}
		System.out.println("===========================================================");
	}

	/**
	 * This method is used to select mode of payment
	 */
	
	@And("^Select the payment mode as credit card under select payment page$")
	public void getPaymentMode() {
		try {
			Thread.sleep(1000);
			driver.findElement(By.xpath(Voi.getProperty("PaymentCC"))).click();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Error on payment page");
		}
	}

	/**
	 * This method is used to validate the credit card details
	 */
	
	@And("^Enter the Invalid credit card information details in the credit card page and click on continue$")
	public void getCreditCardDetails() {
		/*
		 * if
		 * (driver.findElement(By.xpath(Voi.getProperty("Promocardcheck"))).isEnabled())
		 * { driver.findElement(By.xpath(Voi.getProperty("Promocardcheck"))).click(); }
		 */
		String Payableamt = driver.findElement(By.xpath(Voi.getProperty("CCAmount"))).getText();
		if (Payableamt.equals(PillowPrice)) {
			boolean isValid = false;
			getCardDetails(isValid);
		}

		System.out.println("===========================================================");
	}

	/**
	 * This method is used to validate the payment transaction via OTP
	 */
	
	@And("^Click on PAY Now and validate the bank confirmation by enter OTP and click Ok button$")
	public void paymentConfirm() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Error in payment trnsaction");
		}
		driver.switchTo().frame(0);
		if (driver.findElement(By.xpath(Voi.getProperty("Payment_detail"))).isDisplayed()) {
			System.out.println("Bank payment page displayed successfully");
			driver.findElement(By.xpath(Voi.getProperty("OTP"))).sendKeys(Voi.getProperty("BankOTP"));
			driver.findElement(By.xpath(Voi.getProperty("PaymentConfirmation"))).click();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Error on payment confirmation page");
			}
			System.out.println("Bank payment got failed for the transaction");
		}
	}

	/**
	 * This method is used to capture screen shot for failed transaction
	 */
	
	@Then("^Capture the Screen shot of failed transaction with details and validate the message and close the browser$")
	public void orderConfirmation() {
		captureScreenShot();
		driver.switchTo().frame(0);
		String paymentfailure = driver.findElement(By.xpath(Voi.getProperty("Transaction_Failed"))).getText();
		Assert.assertTrue(paymentfailure, true);
		System.out.println("Completes the End to End flow of the Failure Credit Card transaction");
		System.out.println("===========================================================");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println("Error on placing order");
		}
		driver.close();
	}

	/**
	 * Method to read the table information in Order summary page
	 */
	
	private void fetchTable() {
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
	
	private void getCardDetails(boolean isValid) {
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
			System.out.println("Error in reading credit card details");
		}
	}
}
