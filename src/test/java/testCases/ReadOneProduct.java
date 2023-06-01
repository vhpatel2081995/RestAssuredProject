package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ReadOneProduct {
	
	String baseURI;
	SoftAssert softAssert;
	
	public ReadOneProduct() {
		baseURI="https://techfios.com/api-prod/api/product";
		softAssert = new SoftAssert ();
	}
	
	
	@Test
	public void readOneProduct() {
		
		/*
		given: All input details (base URI, Headers, Authorization, Payload/Body, QuaryParameters)
		when: submit api requests (HTTP method, Endpoint/Resource)
		then: Validate Response (Status Code, Headers, responseTime, Payload/Body)
	    */
		
      Response response =
		
      given()
             .baseUri(baseURI)
	         .header("Content-Type","application/json")
	         .header("Authorization", "Bearer hagsgysdbjd")
	         .queryParam("id", "8269").
	       //  log().all().
	 
	   when()
	     //   .log().all()
	        .get("/read_one.php").
	        
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
//      Assert.assertEquals(responseHeader, "application/json");
      softAssert.assertEquals(responseHeader, "application/json");
      
      String responseBody = response.getBody().asString();
      System.out.println(responseBody);
      
      JsonPath jp = new JsonPath(responseBody);
      String productName = jp.get("name");
      System.out.println("Product name : " + productName );
//      Assert.assertEquals(productName, "HashMap Cowboys vs Giants", "Product name not found");
      softAssert.assertEquals(productName, "HashMap Cowboys vs Giants", "Product name not found");
      
      String productDescription = jp.get("description");
      System.out.println("Product description : " + productDescription );
//      Assert.assertEquals(productDescription, "HashMap Dallas Cowboys vs New York Giants", "Product description not found");
      softAssert.assertEquals(productDescription, "HashMap Dallas Cowboys vs New York Giants", "Product description not found");
      
      String productPrice = jp.get("price");
      System.out.println("Product Price : " + productPrice );
//      Assert.assertEquals(productPrice, "59", "Product price not found");
      softAssert.assertEquals(productPrice, "59", "Product price not found");
      
      String productCategoryId = jp.get("category_id");
      System.out.println("Product Category Id : " + productCategoryId );
//      Assert.assertEquals(productCategoryId, "13", "Product Category id not found");
      softAssert.assertEquals(productCategoryId, "13", "Product Category id not found");
      
      String productCategoryName = jp.get("category_name");
      System.out.println("Product Category Name : " + productCategoryName );
//      Assert.assertEquals(productCategoryName, "Sports", "Product Category name not found");
      softAssert.assertEquals(productCategoryName, "Sports", "Product Category name not found");
      
      softAssert.assertAll();
	}
	

}
