package Payload;

public class LoginPayload {

    public static String loginPayload()
    {
        return "{\n" +
                "    \"companyCode\":\"MNDACNT220005\",\n" +
                "    \"password\": \"NMiq+MkMjkUjW2xTtzMv2A==\",\n" +
                "    \"userName\": \"sunil.gaur@myndsol.com\"\n" +
                "}";
    }

    public static String redirectionUrlPayload(String accountStamp,String userStamp)
    {
        return "{\"accountStamp\": \""+accountStamp+"\",\n" +
                "\"dBKey\": \"1fMHyjzuikLVSV69c8E5jY77Fdgj0pXE9rNEson3NIw=\",\n" +
                "\"productStamp\": \"c8d200cb-d2fb-4db8-9d5e-dcdaf2396a03\",\n" +
                "\"subDomainUrl\": \"https://leasexuat.myndsolution.com/#/layout\",\n" +
                "\"userStamp\": \""+userStamp+"\"}";
    }


    public static String tokenByUser(String requestID)
    {
        return "{\n" +
                "    \"stamp\":\""+requestID+"\"\n" +
                "}";
    }


    public static String menuPayload(String type)
    {
        return "{\n" +
                "    \"type\":\""+type+"\"\n" +
                "    }";
    }

    public static String recordListPayload(String tag,int page, int pageSize,String search)
    {
        return "{\n" +
                "    \"entity_tag\": \""+tag+"\",\n" +
                "    \"page\":"+page+",\n" +
                "    \"page_size\":"+pageSize+",\n" +
                "    \"search\":\""+search+"\n" +
                "}";
    }

    public static String initiateTransitionPayload(String docType, String processName)
    {
        return "{\n" +
                "    \"action_id\": 0,\n" +
                "    \"document_type\": \""+docType+"\",\n" +
                "    \"instance_id\": 0,\n" +
                "    \"meta_data\": {},\n" +
                "    \"process_id\": 0,\n" +
                "    \"process_name\": \""+processName+"\",\n" +
                "    \"ref_id\": 0,\n" +
                "    \"stage_id\": 0,\n" +
                "    \"workspace_id\": 0\n" +
                "}";
    }

    public static String lessorBasicPayload(String email,String cin,String entityTag,String entityType,String erpCode,String lessorName)
    {
        return "{\n" +
                "\"cin_number\": \""+cin+"\",\n" +
                "\"email\": \""+email+"\",\n" +
                "\"entity_stamp\": \"1aa002c83fb9b741a74cdd8dfe18d139\",\n" +
                "\"entity_tag\": \""+entityTag+"\",\n" +
                "\"entity_type\": \""+entityType+"\",\n" +
                "\"erp_code\": \""+erpCode+"\",\n" +
                "\"name\": \""+lessorName+"\",\n" +
                "\"vendor_code\": null\n" +
                "}";
    }

    public static String statePayload(String countryId,String stamp)
    {
        return "{\n" +
                "    \"id\": \""+countryId+"\",\n" +
                "    \"ifscCode\": \"\",\n" +
                "    \"stamp\": \""+stamp+"\"\n" +
                "}";
    }

    public static String cityPayload(String stateId,String stamp)
    {
        return "{\n" +
                "    \"id\": \""+stateId+"\",\n" +
                "    \"ifscCode\": \"\",\n" +
                "    \"stamp\": \""+stamp+"\"\n" +
                "}";
    }

    public static String addressTypePayload(String adrsType)
    {
        return "{\"type\":[\""+adrsType+"\"]}";
    }

}
