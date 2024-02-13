package com.talxan.ywitter.yuser;

import com.talxan.ywitter.RestAssured.RestAssuredConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RestTest {
    private String load = "{\n" +
            "    \"firstName\": \"Edgar\",\n" +
            "    \"lastName\": \"Testirovshik\",\n" +
            "    \"email\": \"admin6@email.com\",\n" +
            "    \"password\": \"0000\",\n" +
            "    \"role\": \"ADMIN\"\n" +
            " }";

    @Test
    public void testRegister() throws IOException {
        Response response = RestAssuredConfig.registerUser(load);

        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(),200);
    }
}
