package base;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import io.cucumber.core.api.Scenario;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.CurrencyApiModel;
import utility.Logging;
import utility.UtilHelper;

public class BaseTest {
	// Member variables
	protected RequestSpecification httpRequest;
	protected Response response;
	protected Scenario scenario=null;
	public static Logger log;

	protected CurrencyApiModel model = new CurrencyApiModel();
	protected Map<String, Double> referenceRates = new HashMap<String, Double>();

	/*
	 * Initializes the log4j logger instance.
	 */
	public BaseTest() {
		log = Logging.getLogger(Logging.class);
	}

	// Get response
	public Response getResponse() {
		return response;
	}

	// Get JsonPath for response
	public JsonPath getJsonPath() {
		return response.jsonPath();
	}
	
	protected void getApiResponse(String endpoint) {
		try {
			RestAssured.baseURI = UtilHelper.getProperty("baseUri");
			httpRequest = RestAssured.given();
			log.info("Calling the endpoint.");
			response = httpRequest.get(endpoint);
			if(response == null) {
				log.error("Api response not received. Please check the configurations.");
				throw new RuntimeException("Error getting response from API.");
			}
		} catch(Exception ex) {
			log.error("Error calling the endpoint. Check whether the correct information has been passed. " + ex.toString());
		}
		log.info(response.body().toString());
	}
}
