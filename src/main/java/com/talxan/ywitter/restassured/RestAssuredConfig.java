package com.talxan.ywitter.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import static io.restassured.http.ContentType.JSON;

public class RestAssuredConfig {

    //test
    public static Response registerUser(String s) throws IOException {

        return RestAssured.given()
                .baseUri("http://localhost:8080/api/v1/auth/register")
                .contentType(JSON)
                .accept(JSON)
                .body(s)
                .post();

    }

//    /**
//     * Gets a board given the ID
//     * @param boards
//     * @return Response Type
//     */
//    public static Response getBoard(){
//
//        Response response = RestAssured.given()
//                .get();
//        return response;
//
//    }
//
//    /**
//     * Updates the board given the board and the new parameter
//     * @param boards
//     * @param value new parameter
//     * @param key key to be changed
//     * @return Response Type
//     */
//    public static Response updateBoard() {
//
//        Response response = RestAssured.given()
//                .queryParam()
//                .put();
//        return response;
//
//    }
//
//
//    public static Response deleteBoard(){
//
//        Response response = RestAssured.given()
//                .delete();
//        return response;
//
//    }


    //Helper method to generate the URL for the needed operations
    private static RequestSpecification generateURL() {

        return RestAssured.given()
                .baseUri(Utility.BASE_URI)
                .basePath(Utility.BASE_PATH);

    }
}
