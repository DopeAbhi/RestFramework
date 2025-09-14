package TestFlow.LessorFlow;

import Payload.LoginPayload;
import Utils.CommonUtility;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.IOException;

import static Utils.CommonUtility.decodeBase64Url;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;

public class LessorRegistrationFlow {



    @Test
    public void lessorRegistrationFlow() throws IOException {
        String requestId;
        String authToken = null;

//==============================================Login API =========================================================

        RequestSpecification saasRequest = new RequestSpecBuilder().setBaseUri("https://myndsaasuat.myndsolution.com/")
                .setContentType(ContentType.JSON).build();

        RequestSpecification workflowRequest = new RequestSpecBuilder().setBaseUri("https://uatworkflow.myndsolution.com").setContentType(ContentType.JSON).build();
        RequestSpecification vendorRequest = new RequestSpecBuilder().setBaseUri("https://uatvendor.myndsolution.com/api/").setContentType(ContentType.JSON).build();


        String response = given().spec(saasRequest).
                body(LoginPayload.loginPayload())
                .when().post("gw/api/Authentication2/ValidateUser")
                .then().assertThat().body(matchesJsonSchemaInClasspath("products-schema.json")).extract().response().asString();

        JsonPath js = CommonUtility.rawtoJson(response);
        String jwt = js.getString("data.tokenDetails.token");
        String payloadJson = decodeBase64Url(jwt.split("\\.")[1]);
        JsonPath tokenJson = CommonUtility.rawtoJson(payloadJson);
        requestId = tokenJson.getString("RequestId");
        String userStamp = tokenJson.getString("Id");
        String accountStamp = tokenJson.getString("AccountStamp");


//==============================================Redirection URL =========================================================

        given().spec(saasRequest).header("Authorization", "Bearer " + jwt).body(LoginPayload.redirectionUrlPayload(accountStamp, userStamp))
                .log().all().when().post("gw/api/Authentication2/GetRedirectionUrl")
                .then().log().all();


//==============================================Get Token By User ========================================================

        String tokenresp = given().spec(saasRequest).header("Authorization", "Bearer " + jwt).body(LoginPayload.tokenByUser(requestId))
                .when().post("gw/api/Authentication2/GetTokenByUser").then()
                .log().all().extract().response().asString();

        js = CommonUtility.rawtoJson(tokenresp);
        authToken = js.getString("data.token");
        System.out.println(authToken);

//==============================================Get All Accounts========================================================

        given().spec(saasRequest).header("Authorization", "Bearer " + authToken).body("{}")
                .when().post("sub/api/Account/getAllAccounts").then()
                .log().all();

//==============================================Menu========================================================

        given().spec(workflowRequest).header("Authorization", "Bearer " + authToken).body(LoginPayload.menuPayload("left"))
                .when().post("/api/menu").then()
                .log().all();

//==============================================Record List========================================================
        given().spec(vendorRequest).header("Authorization","Bearer "+authToken).body(LoginPayload.recordListPayload("lessor",1,10,""))
                .when().post("vendor/list").then()
                .log().all();

//==============================================Initiate Transition========================================================
        given().spec(workflowRequest).header("Authorization", "Bearer "+authToken).body(LoginPayload.initiateTransitionPayload("lessor","lessor_registration"))
                .when().post("/api/internal/wf/initiate_transition").then()
                .log().all();

//==============================================Lessor Basic Add===========================================================
     String basicRespone=   given().spec(vendorRequest).header("Authorization","Bearer "+authToken)
                .body(LoginPayload.lessorBasicPayload("AutomateAPI@gmail.com","Test","lessor","1","Test","AutomateAPI"))
                .when().post("register/add").then()
                .log().all().extract().response().asString();
          js=  CommonUtility.rawtoJson(basicRespone);

        String lessorId=  js.getString("data.id");

//==============================================Get Countries API===========================================================
      String countryResp=  given().spec(saasRequest).header("Authorization", "Bearer "+authToken)
                .body(" ")
                .when().post("mst/api/Country/GetCountries")
                .then().log().all().extract().response().asString();

                js=CommonUtility.rawtoJson(countryResp);
                String countryId=js.getString("data.countryId");

//==============================================Get State by Country===========================================================

       String stateResp= given().spec(saasRequest).header("Authorization","Bearer "+authToken)
                .body(LoginPayload.statePayload(countryId,requestId))
                .when().post("mst/api/State/GetStatesByCountryId").then()
                .log().all().extract().response().asString();

       js=CommonUtility.rawtoJson(stateResp);
       String stateId=js.getString("data.stateId");
//==============================================Get City by State===========================================================

      String cityResp=  given().spec(saasRequest).header("Authorization","Bearer "+authToken)
                .body(LoginPayload.cityPayload(stateId,requestId))
                .when().post("mst/api/City/GetCityByStateId")
                .then().log().all().extract().response().asString();

      js=CommonUtility.rawtoJson(cityResp);
     String cityId= js.getString("data.cityId");
//==============================================Get AddressType===========================================================
        String addressId=null;
       String addrssTypeResp= given().spec(vendorRequest).header("Authorization" ,"Bearer "+authToken)
                .body(LoginPayload.addressTypePayload("address_type"))
                .when().post("master/lookup")
                .then().log().all().extract().response().asString();
       js=CommonUtility.rawtoJson(addrssTypeResp);
      int count=  js.getInt("data.address_type");
        for (int i = 0; i <count ; i++) {
            if(js.getString("data.address_type["+i+"].name").equalsIgnoreCase("Corporate"))
            {
                addressId=js.getString("data.address_type["+i+"].id");
            }
        }
//==============================================Lessor Address Add API===========================================================

    }
}
