package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadAllProducts {
	
	@Test
	public void readAllProducts() {
		
		/*
		given: All input details (base URI, Headers, Authorization, Payload/Body, QuaryParameters)
		when: submit api requests (HTTP method, Endpoint/Resource)
		then: Validate Response (Status Code, Headers, responseTime, Payload/Body)
	    */
		
      Response response =
		
      given()
             .baseUri("https://techfios.com/api-prod/api/product")
	         .header("Content-Type","application/json; charset=UTF-8")
	         .auth().preemptive().basic("demo@techfios.com", "abc123").
	       //  log().all().
	 
	   when()
	     //   .log().all()
	        .get("/read.php").
	        
	   then()
	       //  .statusCode(200)
	      //    .header("Content-Type","application/json; charset=UTF-8")
          //  .log().all();
	     .extract().response();
      
       int statusCode   = response.getStatusCode();
       System.out.println("StatusCode : " + statusCode);
       Assert.assertEquals(statusCode, 200);
       
      long responseTime = response.getTime();
      System.out.println("ResponseTime : " + responseTime);
      if(responseTime <= 2000) {
    	  System.out.println("ResponseTime is within range");
      }else {
    	  System.out.println("ResponseTime is out of range");
      }
      
      String responseHeader = response.getHeader("Content-Type");
      System.out.println("responseHeaderType : " + responseHeader);
      Assert.assertEquals(responseHeader, "application/json; charset=UTF-8");
      
      String responseBody = response.getBody().asString();
      System.out.println(responseBody);
      
      JsonPath jp = new JsonPath(responseBody);
      String firstProductId = jp.get("records[0].id");
      System.out.println("first Product Id : " + firstProductId );
      
      if (firstProductId != null) {
    	  System.out.println("first product id is not null"); 
      }else {
    	  System.out.println("first product id is null");
      }
    	  
	}
	

}
