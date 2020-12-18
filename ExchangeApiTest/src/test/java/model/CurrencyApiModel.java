package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"base",
"rates",
"date"
})
public class CurrencyApiModel {

	@JsonProperty("base")
	private String base;
	@JsonProperty("rates")
	private Map<String, Double> additionalProperties = new HashMap<String, Double>();
	@JsonProperty("date")
	private Date date;
	
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public Map<String, Double> getAdditionalProperties() {
		return additionalProperties;
	}
	public void setAdditionalProperties(Map<String, Double> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
