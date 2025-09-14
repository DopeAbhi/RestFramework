package Utils;

import io.restassured.path.json.JsonPath;

import java.util.Base64;

public class CommonUtility {
    public static String decodeBase64Url(String input) {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        byte[] decodedBytes = decoder.decode(input);
        return new String(decodedBytes);
    }

    public static JsonPath rawtoJson(String response)
    {
return  new JsonPath(response);

    }
}
