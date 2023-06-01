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

public class UpdateOneProductUsingHashMap {

	String baseURI;
	SoftAssert softAssert;
	String firstProductId;
    HashMap<String, String> updatepayload;
    
	public UpdateOneProductUsingHashMap() {
		baseURI = "https://techfios.com/api-prod/api/product";
		softAssert = new SoftAssert();
		updatepayload = new HashMap <String, String>();
	}
    
	public Map<String, String> updatePayloadMap(){
    updatepayload.put("id", "8281");	
	updatepayload.put("name", "Virat's Amazing Amazon 3.14");	
	updatepayload.put("price", "9");
	updatepayload.put("description", "The best for amazing Qa SDET .");
	updatepayload.put("category_id", "2");
	updatepayload.put("category_name", "Electronics");
		return updatepayload;
	}
	
	@Test (priority=1)
	public void updateOneProduct() {

		Response response =

				given().baseUri(baseURI).header("Content-Type", "application/json")
						.header("Authorization", "Bearer hagsgysdbjd")
						.body(updatePayloadMap()).

						when()
						.put("/update.php").

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
//      Assert.assertEquals(productMessage, "Product was updated.", "Product was updated message not found");
		softAssert.assertEquals(productMessage, "Product was updated.", "Product was updated message not found");

		softAssert.assertAll();
	}
	
	@Test (priority=2)
	public void readOneProduct() {

		Response response =

				        given()
				        .baseUri(baseURI).header("Content-Type", "application/json")
						.header("Authorization", "Bearer hagsgysdbjd").queryParam("id", "8269")
						.queryParam("id",updatePayloadMap().get("id") ).

						when()
						.get("/read_one.php").

						then()
						.extract().response();

	    
		String updatedproductName = updatePayloadMap().get("name");
		String updatedproductPrice = updatePayloadMap().get("price");
		String updatedproductDescription = updatePayloadMap().get("description");
		String updatedproductCategoryId = updatePayloadMap().get("category_id");
		String updatedproductCategoryName = updatePayloadMap().get("category_name");
		
		
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);

		JsonPath jp = new JsonPath(responseBody);
		String actualproductName = jp.get("name");
		System.out.println("Actual Product name : " + actualproductName);
//        Assert.assertEquals(actualproductName, updatedproductName, "Product name not found");
		softAssert.assertEquals(actualproductName, updatedproductName, "Product name not found");
		
		String actualproductPrice = jp.get("price");
		System.out.println("Actual Product Price : " + actualproductPrice);
//       Assert.assertEquals(actualproductPrice, updatedproductPrice, "Product price not found");
		softAssert.assertEquals(actualproductPrice, updatedproductPrice, "Product price not found");

		String actualproductDescription = jp.get("description");
		System.out.println("Actual Product description : " + actualproductDescription);
//       Assert.assertEquals(actualproductDescription, updatedproductDescription,"Product description not found");
		softAssert.assertEquals(actualproductDescription, updatedproductDescription,"Product description not found");

		String actualproductCategoryId = jp.get("category_id");
		System.out.println("Actual Product Category Id : " + actualproductCategoryId);
//       Assert.assertEquals(actualproductCategoryId, updatedproductCategoryId, "Product Category id not found");
		softAssert.assertEquals(actualproductCategoryId, updatedproductCategoryId, "Product Category id not found");

		String actualproductCategoryName = jp.get("category_name");
		System.out.println("Actual Product Category Name : " + actualproductCategoryName);
//        Assert.assertEquals(actualproductCategoryName, updatedproductCategoryName, "Product Category name not found");
		softAssert.assertEquals(actualproductCategoryName, updatedproductCategoryName, "Product Category name not found");

		softAssert.assertAll();
	}

}
