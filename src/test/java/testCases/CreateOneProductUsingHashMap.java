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

public class CreateOneProductUsingHashMap {

	String baseURI;
	SoftAssert softAssert;
	String firstProductId;
    HashMap<String, String> createpayload;
    
	public CreateOneProductUsingHashMap() {
		baseURI = "https://techfios.com/api-prod/api/product";
		softAssert = new SoftAssert();
		createpayload = new HashMap <String, String>();
	}
    
	public Map<String, String> createPayloadMap(){
	createpayload.put("name", "Virat's Amazing Pillow 2.5");	
	createpayload.put("price", "99");
	createpayload.put("description", "The best for amazing Qa SDET programmers.");
	createpayload.put("category_id", "2");
	createpayload.put("category_name", "Electronics");
		return createpayload;
	}
	
	@Test (priority=1)
	public void createOneProduct() {

		Response response =

				given().baseUri(baseURI).header("Content-Type", "application/json")
						.header("Authorization", "Bearer hagsgysdbjd")
						.body(createPayloadMap()).

						when()
						.post("/create.php").

						then()
						.extract().response();

		int statusCode = response.getStatusCode();
		System.out.println("StatusCode : " + statusCode);
//       Assert.assertEquals(statusCode, 201);
		softAssert.assertEquals(statusCode, 201);

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
//      Assert.assertEquals(productMessage, "Product was created.", "Product created message not found");
		softAssert.assertEquals(productMessage, "Product was created.", "Product created message not found");

		softAssert.assertAll();
	}
    @Test (priority=2)
	public void readAllProducts() {

		Response response =

				given().baseUri("https://techfios.com/api-prod/api/product")
						.header("Content-Type", "application/json; charset=UTF-8").auth().preemptive()
						.basic("demo@techfios.com", "abc123").

						when().get("/read.php").

						then().extract().response();

		String responseBody = response.getBody().asString();
		System.out.println(responseBody);

		JsonPath jp = new JsonPath(responseBody);
		firstProductId = jp.get("records[0].id");
		System.out.println("first Product Id : " + firstProductId);
	}

	@Test (priority=3)
	public void readOneProduct() {

		Response response =

				        given()
				        .baseUri(baseURI).header("Content-Type", "application/json")
						.header("Authorization", "Bearer hagsgysdbjd").queryParam("id", "8269")
						.queryParam("id", firstProductId).

						when()
						.get("/read_one.php").

						then()
						.extract().response();

	
		String expectedproductName = createPayloadMap().get("name");
		String expectedproductPrice = createPayloadMap().get("price");
		String expectedproductDescription = createPayloadMap().get("description");
		String expectedproductCategoryId = createPayloadMap().get("category_id");
		String expectedproductCategoryName = createPayloadMap().get("category_name");
		
		
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);

		JsonPath jp = new JsonPath(responseBody);
		String actualproductName = jp.get("name");
		System.out.println("Actual Product name : " + actualproductName);
//        Assert.assertEquals(actualproductName, expectedproductName, "Product name not found");
		softAssert.assertEquals(actualproductName, expectedproductName, "Product name not found");
		
		String actualproductPrice = jp.get("price");
		System.out.println("Actual Product Price : " + actualproductPrice);
//       Assert.assertEquals(actualproductPrice, expectedproductPrice, "Product price not found");
		softAssert.assertEquals(actualproductPrice, expectedproductPrice, "Product price not found");

		String actualproductDescription = jp.get("description");
		System.out.println("Actual Product description : " + actualproductDescription);
//       Assert.assertEquals(actualproductDescription, expectedproductDescription,"Product description not found");
		softAssert.assertEquals(actualproductDescription, expectedproductDescription,"Product description not found");

		String actualproductCategoryId = jp.get("category_id");
		System.out.println("Actual Product Category Id : " + actualproductCategoryId);
//     Assert.assertEquals(actualproductCategoryId, expectedproductCategoryId, "Product Category id not found");
		softAssert.assertEquals(actualproductCategoryId, expectedproductCategoryId, "Product Category id not found");

		String actualproductCategoryName = jp.get("category_name");
		System.out.println("Actual Product Category Name : " + actualproductCategoryName);
//        Assert.assertEquals(actualproductCategoryName, expectedproductCategoryName, "Product Category name not found");
		softAssert.assertEquals(actualproductCategoryName, expectedproductCategoryName, "Product Category name not found");

		softAssert.assertAll();
	}

}
