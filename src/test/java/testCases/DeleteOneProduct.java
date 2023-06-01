package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DeleteOneProduct {

	String baseURI;
	SoftAssert softAssert;
	String firstProductId;
    HashMap<String, String> deletepayload;
    
	public DeleteOneProduct() {
		baseURI = "https://techfios.com/api-prod/api/product";
		softAssert = new SoftAssert();
		deletepayload = new HashMap <String, String>();
	}
    
	public Map<String, String> deletePayloadMap(){
		deletepayload.put("id", "8273");	
			return deletepayload;
		}
	
	@Test (priority=1)
	public void deleteteOneProduct() {

		Response response =

				given().baseUri(baseURI).header("Content-Type", "application/json")
						.header("Authorization", "Bearer hagsgysdbjd")
						.body(deletePayloadMap()).

						when()
						.delete("/delete.php").

						then()
						.extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("StatusCode : " + statusCode);
//       Assert.assertEquals(statusCode, 200);
		softAssert.assertEquals(statusCode, 200);

		long responseTime = response.getTime();
		System.out.println("ResponseTime : " + responseTime);
		if (responseTime <= 2000) {
			System.out.println("ResponseTime is within range");
		} else {
			System.out.println("ResponseTime is out of range");
		}

		String responseHeader = response.getHeader("Content-Type");
		System.out.println("responseHeaderType : " + responseHeader);
//      Assert.assertEquals(responseHeader, "application/json; charset=UTF-8");
		softAssert.assertEquals(responseHeader, "application/json; charset=UTF-8");

		String responseBody = response.getBody().asString();
		System.out.println(responseBody);

		JsonPath jp = new JsonPath(responseBody);
		String productMessage = jp.get("message");
		System.out.println("Product message : " + productMessage);
//      Assert.assertEquals(productMessage, "Product was deleted.", "Product was deleted message not found");
		softAssert.assertEquals(productMessage, "Product was deleted.", "Product was deleted message not found");

		softAssert.assertAll();
	}
	
	@Test (priority=2)
	public void readOneProduct() {

		String deletedProductId = deletePayloadMap().get("id");
		
		Response response =

				        given()
				        .baseUri(baseURI).header("Content-Type", "application/json")
						.header("Authorization", "Bearer hagsgysdbjd").queryParam("id", "8269")
						.queryParam("id", deletedProductId).

						when()
						.get("/read_one.php").

						then()
						.extract().response();
		
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		
		int statusCode = response.getStatusCode();
		System.out.println("StatusCode : " + statusCode);
//       Assert.assertEquals(statusCode, 404);
		softAssert.assertEquals(statusCode, 404);
		
		JsonPath jp = new JsonPath(responseBody);
		String productMessage = jp.get("message");
		System.out.println("Product message : " + productMessage);
//        Assert.assertEquals(actualproductName, updatedproductName, "Product name not found");
		softAssert.assertEquals(productMessage, "Product does not exist.", "Product Messages are not matching");
		
		softAssert.assertAll();
	}

}
