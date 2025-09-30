package TestFlow.LessorFlow;

import Payload.LoginPayload;
import Utils.CommonUtility;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestLogSpecification;
import io.restassured.specification.RequestSpecification;
import net.datafaker.Faker;
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

        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String lessorName = faker.name().fullName();
        String stateName = "Delhi";
        String cityName = "Delhi";
        String addrsLine1 = faker.address().fullAddress();
        String addrsLine2 = faker.address().fullAddress();
        String zipCode = faker.address().zipCode();
        String panNumber = "BYVPV091N";
        String msmeNumber = "123456789012";
        String gstNumber = "09AAACH7409R1ZZ";
        String taxCodeTitle = "IN- Sec 194I-Invoice HUF-Rent Property 10%";
        String tdsComponent="Minimum Guarantee";
        String paymentTerm="7 Days";
//==============================================Login API =========================================================

        RequestSpecification saasRequest = new RequestSpecBuilder().setBaseUri("https://myndsaasuat.myndsolution.com/")
                .setContentType(ContentType.JSON).build();

        RequestSpecification workflowRequest = new RequestSpecBuilder().setBaseUri("https://uatworkflow.myndsolution.com/").setContentType(ContentType.JSON).build();
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
                .when().post("api/menu").then()
                .log().all();

//==============================================Record List========================================================
        given().spec(vendorRequest).header("Authorization", "Bearer " + authToken).body(LoginPayload.recordListPayload("lessor", 1, 10, ""))
                .when().post("vendor/list").then()
                .log().all();

//==============================================Initiate Transition========================================================
        given().spec(workflowRequest).header("Authorization", "Bearer " + authToken).body(LoginPayload.initiateTransitionPayload("lessor", "lessor_registration"))
                .when().post("api/internal/wf/initiate_transition").then()
                .log().all();

//==============================================Lessor Basic Add===========================================================
        String basicRespone = given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.lessorBasicPayload(email, "Test", "lessor", "1", "Test", lessorName))
                .when().post("register/add").then()
                .log().all().extract().response().asString();
        js = CommonUtility.rawtoJson(basicRespone);

        String entityId = js.getString("data.id");

//==============================================Get Countries API===========================================================
        String countryResp = given().spec(saasRequest).header("Authorization", "Bearer " + authToken)
                .body(" ")
                .when().post("mst/api/Country/GetCountries")
                .then().log().all().extract().response().asString();

        js = CommonUtility.rawtoJson(countryResp);
        String countryId = js.getString("data[0].countryId");

//==============================================Get State by Country===========================================================

        String stateResp = given().spec(saasRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.statePayload(countryId, requestId))
                .when().post("mst/api/State/GetStatesByCountryId").then()
                .log().all().extract().response().asString();
        String stateId = null;
        js = CommonUtility.rawtoJson(stateResp);
        for (int i = 0; i < js.getInt("data.size()"); i++) {
            if (js.getString("data[" + i + "].name").equalsIgnoreCase(stateName)) {
                stateId = js.getString("data[" + i + "].stateId");
                break;
            }
        }
//==============================================Get City by State===========================================================

        String cityResp = given().spec(saasRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.cityPayload(stateId, requestId))
                .when().post("mst/api/City/GetCityByStateId")
                .then().log().all().extract().response().asString();

        js = CommonUtility.rawtoJson(cityResp);
        String cityId = null;
        for (int i = 0; i < js.getInt("data.size()"); i++) {
            if (js.getString("data[" + i + "].name").equalsIgnoreCase(cityName)) {
                cityId = js.getString("data[" + i + "].cityId");
                break;
            }
        }
//==============================================Get AddressType===========================================================
        String addressId = null;
        String addrssTypeResp = given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.addressTypePayload("address_type"))
                .when().post("master/lookup")
                .then().log().all().extract().response().asString();
        js = CommonUtility.rawtoJson(addrssTypeResp);

        for (int i = 0; i < js.getInt("data.address_type"); i++) {
            if (js.getString("data.address_type[" + i + "].name").equalsIgnoreCase("Corporate")) {
                addressId = js.getString("data.address_type[" + i + "].id");
                break;
            }
        }
//==============================================Lessor Address Add API===========================================================


        String addrsResp = given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.lessorAddressAddPayload(addrsLine1, addrsLine2, addressId
                        , cityId, countryId, entityId, stateId, zipCode))
                .when().post("api/address/add")
                .then().log().all().extract().response().asString();
        js = CommonUtility.rawtoJson(addrsResp);
        int addrsId = js.getInt("data.id");
//===============================Lessor Contact Person Add API =================================================
        String contactResp = given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.lessorContactPersonPayload(email, entityId, faker.phoneNumber().cellPhone()
                        , lessorName)).when().post()
                .then().log().all().extract().response().asString();
        js = CommonUtility.rawtoJson(contactResp);
        int contactId = js.getInt("data.id");
//===============================Lessor Compliance Details =================================================

        given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.compliancePayload(entityId, "2090-11-09", msmeNumber,
                        panNumber)).when().post("compliance/pan_add")
                .then().log().all().extract().response().asString();
//===============================Lessor GST Add API =================================================

        String gstResp = given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.gstPayload(entityId, stateId, gstNumber)
                ).when().post("compliance/gst_add")
                .then().log().all().extract().response().asString();
        js = CommonUtility.rawtoJson(gstResp);
        int gstId = js.getInt("data.id");
//===============================Account Payout Type API =================================================

        String accPayResp = given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.accountPayoutType())
                .when().post("/master/lookup")
                .then().log().all().extract().response().asString();
        js = CommonUtility.rawtoJson(accPayResp);
        String payoutTypeId = js.getString("data.bank_account_type[0].id");
        String accType = js.getString("data.bank_account_type[0].name");
//===============================Bank Details Add API =================================================
        String bankResp = given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.lessorBankPayload("Test Holder"
                        , "1231231233", accType, "Test Bank", payoutTypeId, "Test Branch", entityId,
                        "HDFC0001234"))
                .when().post("bank/add").then()
                .log().all().extract().response().asString();
        js = CommonUtility.rawtoJson(bankResp);
        int bankId = js.getInt("data.id");
//===============================Get Tax Code API =================================================
        String taxCodeRate = null;
        String taxCodeResp = given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body("")
                .when().post("vendor/tax_code_rate").then()
                .log().all().extract().response().asString();
        js = CommonUtility.rawtoJson(taxCodeResp);
        for (int i = 0; i < js.getInt("data.size()"); i++) {
            if (js.getString("data[" + i + "].display_title").equalsIgnoreCase(taxCodeTitle)) {
                taxCodeRate = js.getString("data[" + i + "].tax_code_rate");
                break;
            }
        }
//===============================Get TDS Component API =================================================
      String taxComponentId = null;
        String tdsCompResp=given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body("")
                .when().post("vendor/component_list").then()
                .log().all().extract().response().asString();
        for (int i = 0; i < js.getInt("data.size()"); i++) {
            if (js.getString("data[" + i + "].name").equalsIgnoreCase(tdsComponent)) {
                taxComponentId = js.getString("data[" + i + "].id");
                break;
            }
        }

        //===============================Add TDS API =================================================
        given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.tdsPayload(taxComponentId, entityId, taxCodeRate))
                .when().post("tds/add")
                .then().log().all().extract().response().asString();
        //===============================Get Invoice Payment Terms =================================================

       String payTermsResp= given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body("{\n" +
                        "    \"type\":[\n" +
                        "        \"invoice_payment_terms\"\n" +
                        "    ]\n" +
                        "}")
                .when().post("master/lookup")
                .then().log().all().extract().response().asString();
            js=CommonUtility.rawtoJson(payTermsResp);
        for (int i = 0; i <js.getInt("data.invoice_payment_terms.size()") ; i++) {
            if(js.getString("data.invoice_payment_terms["+i+"].name").equalsIgnoreCase("Net 30")){
                String paymentTermID=js.getString("data.invoice_payment_terms["+i+"].id");
                break;
            }
        //==============================Lessor SubCode Add=================================================

        given().spec(vendorRequest).header("Authorization", "Bearer " + authToken)
                .body(LoginPayload.subCodePayload())
                .when().post("subcode/add")
                .then().log().all().extract().response().asString();

    }
}
