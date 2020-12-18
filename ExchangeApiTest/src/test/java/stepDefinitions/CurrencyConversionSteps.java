package stepDefinitions;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import base.BaseTest;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CurrencyApiModel;
import utility.UtilHelper;

public class CurrencyConversionSteps extends BaseTest{

	/*
	 * Initializes the scenario instance, to help in logging the statements in cucumber report. 
	 */
	@Before
	public void beforeStep(Scenario scn) {
		scenario = scn;
		scn.write("Test execution start!");
	}

	/*
	 * Step to validate whether the endpoint is returning a valid response. 
	 */
	@Given("a valid request is created with uri {string}")
	public void a_valid_request_is_created_with_endpoint(String endpoint) {
		try {
		log.info("Calling the api with endpoint : "+endpoint);
		getApiResponse(endpoint);
		
		//Capture the response from the API and deserialize to API response model
		log.info("Deserializing json to CurrencyApiModel");
		model = response.getBody().as(CurrencyApiModel.class);
		
		} catch (Exception e) {
			//Intentionally left blank, in case of error response from API - do nothing
			//Calling methods to handle the exception.
			log.info("Error response received from the API.");
		}

	}

	/*
	 * This method validates the latest exchange rate api date with the current date. 
	 */
	@Then("the exchange rate date should be current date")
	public void the_exchange_rate_date_should_be_current_date() throws Exception {
		scenario.write("Current date : "+ model.getDate());
		
		assertEquals("The exchange date is not current date.", UtilHelper.getPstDateFormat().format(new Date()), 
				UtilHelper.getSimpleDateFormat().format(model.getDate()));
	}

	/*
	 * This method will verify all the exchange rates that are part of the response based on the jsonfile feed passed. 
	 */
	@Then("the exchange quotes should match {string}")
	public void the_exchange_quotes_should_match(String filename) {
		try {
			referenceRates = UtilHelper.getJsonFileFeed(filename);

			assertTrue("The exchange rates in response do not match the file.", referenceRates.entrySet().containsAll(model.getAdditionalProperties().entrySet()));

		} catch (Exception e) {
			System.out.print("Error handling the exchange quotes file. Please check the file exists and on the correct path.");;
		}
	}

	/*
	 * This method validates the status code of the api. It has been used as a condition and a validation hence 
	 * specified in both When and Then.
	 */
	@When("the api returns a valid response {string}")
	@Then("the api status response should be {string}")
	public void the_api_status_response_should_be(String statusCode) {
		scenario.write("Status code is : " +  response.statusCode());
		assertEquals("The status code is not "+statusCode, response.statusCode(), Integer.parseInt(statusCode));
	}
	
	/*
	 * This method checks the response time based on the user provided value in the feature file. 
	 */
	@Then("response time is less than {string} ms")
	public void response_time_is_less_than_ms(String resTime) {
		scenario.write("Current response time for API is : " + response.getTime());
		assertTrue(response.getTime() < Long.parseLong(resTime));
		log.info("Response time within acceptable limits.");
	}
	
	/*
	 * This method checks the value of a specific node of the json. One of the scenarios here,
	 * it is used to check the error scenario where we have incomplete URL and we are validating the error response.
	 */
	@Then("the {string} response is {string}")
	public void the_response_is(String node, String value) {
		response.then().body(node, is(value));
	}
	
	/*
	 * This method validates the base currency from the response.
	 */
	@When("the base currency is {string}")
	public void the_base_currency_is(String currency) {
		response.then().body("base", is(currency));
	}
	
	/*
	 * This method validates the response date from the response.
	 */
	@Then("the exchange rate date should be {string}")
	public void the_exchange_rate_date_should_be(String date) {
		response.then().body("date", is(date));
	}
}
