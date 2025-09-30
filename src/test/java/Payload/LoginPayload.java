package Payload;

public class LoginPayload {

    public static String loginPayload() {
        return "{\n" +
                "    \"companyCode\":\"MNDACNT220005\",\n" +
                "    \"password\": \"NMiq+MkMjkUjW2xTtzMv2A==\",\n" +
                "    \"userName\": \"sunil.gaur@myndsol.com\"\n" +
                "}";
    }

    public static String redirectionUrlPayload(String accountStamp, String userStamp) {
        return "{\"accountStamp\": \"" + accountStamp + "\",\n" +
                "\"dBKey\": \"1fMHyjzuikLVSV69c8E5jY77Fdgj0pXE9rNEson3NIw=\",\n" +
                "\"productStamp\": \"c8d200cb-d2fb-4db8-9d5e-dcdaf2396a03\",\n" +
                "\"subDomainUrl\": \"https://leasexuat.myndsolution.com/#/layout\",\n" +
                "\"userStamp\": \"" + userStamp + "\"}";
    }


    public static String tokenByUser(String requestID) {
        return "{\n" +
                "    \"stamp\":\"" + requestID + "\"\n" +
                "}";
    }


    public static String menuPayload(String type) {
        return "{\n" +
                "    \"type\":\"" + type + "\"\n" +
                "    }";
    }

    public static String recordListPayload(String tag, int page, int pageSize, String search) {
        return "{\n" +
                "    \"entity_tag\": \"" + tag + "\",\n" +
                "    \"page\":" + page + ",\n" +
                "    \"page_size\":" + pageSize + ",\n" +
                "    \"search\":\"" + search + "\n" +
                "}";
    }

    public static String initiateTransitionPayload(String docType, String processName) {
        return "{\n" +
                "    \"action_id\": 0,\n" +
                "    \"document_type\": \"" + docType + "\",\n" +
                "    \"instance_id\": 0,\n" +
                "    \"meta_data\": {},\n" +
                "    \"process_id\": 0,\n" +
                "    \"process_name\": \"" + processName + "\",\n" +
                "    \"ref_id\": 0,\n" +
                "    \"stage_id\": 0,\n" +
                "    \"workspace_id\": 0\n" +
                "}";
    }

    public static String lessorBasicPayload(String email, String cin, String entityTag, String entityType, String erpCode, String lessorName) {
        return "{\n" +
                "\"cin_number\": \"" + cin + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"entity_stamp\": \"1aa002c83fb9b741a74cdd8dfe18d139\",\n" +
                "\"entity_tag\": \"" + entityTag + "\",\n" +
                "\"entity_type\": \"" + entityType + "\",\n" +
                "\"erp_code\": \"" + erpCode + "\",\n" +
                "\"name\": \"" + lessorName + "\",\n" +
                "\"vendor_code\": null\n" +
                "}";
    }

    public static String statePayload(String countryId, String stamp) {
        return "{\n" +
                "    \"id\": \"" + countryId + "\",\n" +
                "    \"ifscCode\": \"\",\n" +
                "    \"stamp\": \"" + stamp + "\"\n" +
                "}";
    }

    public static String cityPayload(String stateId, String stamp) {
        return "{\n" +
                "    \"id\": \"" + stateId + "\",\n" +
                "    \"ifscCode\": \"\",\n" +
                "    \"stamp\": \"" + stamp + "\"\n" +
                "}";
    }

    public static String addressTypePayload(String adrsType) {
        return "{\"type\":[\"" + adrsType + "\"]}";
    }


    public static String lessorAddressAddPayload(String adrsLine1, String adrsLine2
            , String adrsType, String cityId, String countryId, String entityId, String stateId, String zipCode) {
        return "{\"address_line1\": \"" + adrsLine1 + "\",\n" +
                "\"address_line2\": \"" + adrsLine2 + "\",\n" +
                "\"address_type\":\" " + adrsType + " \",\n" +
                "\"city_id\":\" " + cityId + "\",\n" +
                "\"country_id\":\" " + countryId + "\",\n" +
                "\"entity_id\":\"" + entityId + "\",\n" +
                "\"entity_tag\": \"lessor\",\n" +
                "\"id\": null,\n" +
                "\"state_id\": \"" + stateId + "\",\n" +
                "\"zip_code\": \"" + zipCode + "\"}";
    }

    public static String lessorContactPersonPayload(String email,
                                                    String entityId, String mobNum, String name) {
        return "{\n" +
                "    \"department_id\": null,\n" +
                "    \"email_id\": \"" + email + "\",\n" +
                "    \"entityTag\": \"lessor\",\n" +
                "    \"entity_id\": \"" + entityId + "\",\n" +
                "    \"is_primary\": false,\n" +
                "    \"mobile_number1\": \"" + mobNum + "\",\n" +
                "    \"name\": \"" + name + "\"\n" +
                "}";
    }

    public static String compliancePayload(String entityId, String msmeExpreDte
            , String msmeRegNo, String panNo) {
        return "{\n" +
                "    \"aadhaar_copy\": null,\n" +
                "    \"aadhaar_no\": null,\n" +
                "    \"declaration_letter\": {\n" +
                "        \"docId\": 1716,\n" +
                "        \"fileName\": \"http_status_methods_1744028189.pdf\"\n" +
                "    },\n" +
                "    \"entity_id\": " + entityId + ",\n" +
                "    \"entity_stamp\": null,\n" +
                "    \"entity_tag\": \"lessor\",\n" +
                "    \"id\": null,\n" +
                "    \"is_invoice_applied\": false,\n" +
                "    \"msme_expiry_date\": \"" + msmeExpreDte + "\",\n" +
                "    \"msme_register\": \"4\",\n" +
                "    \"msme_register_no\": \"" + msmeRegNo + "\",\n" +
                "    \"msme_registration_certificate\": null,\n" +
                "    \"pan_copy\": {\n" +
                "        \"docId\": 1715,\n" +
                "        \"fileName\": \"ISTQB_CTFL_Syllabus-v4.0.pdf\"\n" +
                "    },\n" +
                "    \"pan_no\": \"" + panNo + "\"\n" +
                "}";
    }

    public static String gstPayload(String entityId, String stateId, String gstNumber
    ) {
        return "{\n" +
                "\"entity_id\": " + entityId + ",\n" +
                "\"entity_stamp\": \"6c935e7f090f076c990b06a26ba1c11d\",\n" +
                "\"entity_tag\": \"lessor\",\n" +
                "\"gst_certificate\": null,\n" +
                "\"gst_number\": \"" + gstNumber + "\",\n" +
                "\"gst_status\": \"34\",\n" +
                "\"state_id\": \"" + stateId + "\"\n" +
                "}";
    }

    public static String accountPayoutType() {
        return "{\n" +
                "    \"type\": [\n" +
                "        \"bank_account_type\",\n" +
                "        \"bank_payout_type\"\n" +
                "    ]\n" +
                "}";
    }

    public static String lessorBankPayload(
            String accHolderName, String accNo,
            String accTypeId, String bankName, String payoutId,
            String branchName, String entityId, String ifscCode
    ) {
        return "{\n" +
                "\"account_holder_name\":\""+accHolderName+"\",\n" +
                "\"account_no\": \""+accNo+"\",\n" +
                "\"account_type_id\": \""+accTypeId+"\",\n" +
                "\"bank_name\": \""+bankName+"\",\n" +
                "\"bank_payout_type_id\": \""+payoutId+"\",\n" +
                "\"branch_name\": \""+branchName+"\",\n" +
                "\"cancelled_cheque\": null,\n" +
                "\"entity_id\": "+entityId+",\n" +
                "\"entity_tag\": \"lessor\",\n" +
                "\"ifsc_code\": \""+ifscCode+"\"\n" +
                "}";
    }


    public static String tdsPayload(String compId, String entityId, String taxCodeRate)
    {
        return "{\n" +
                "    \"component_id\": "+compId+",\n" +
                "    \"entity_id\": "+entityId+",\n" +
                "    \"tax_code_rate\": \""+taxCodeRate+"\"\n" +
                "}";
    }

    public static String subCodePayload(String addressId, String bankId,
                                        String contactId, String entityId,
                                        String gstId, String paymentTermID, String siteCode, String siteName)
    {
        return "{\n" +
                "\"address_id\": "+addressId+",\n" +
                "\"bank_ids\": ["+bankId+"],\n" +
                "\"company_id\": 1214,\n" +
                "\"contact_person\": ["+contactId+"],\n" +
                "\"entityId\": "+entityId+",\n" +
                "\"entity_id\": "+entityId+",\n" +
                "\"entity_stamp\": \"2688998cdf2511161229c2a65630a9c5\",\n" +
                "\"entity_tag\": \"lessor\",\n" +
                "\"erp_code\": null,\n" +
                "\"gst_id\": "+gstId+",\n" +
                "\"invoice_payment_terms_id\": "+paymentTermID+",\n" +
                "\"site_code\": \""+siteCode+"\",\n" +
                "\"site_name\": \""+siteName+"\"\n" +
                "}";
    }

}
