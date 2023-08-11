package ua.ithillel.tests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseApiTest {

    private static final String baseURL = "https://reqres.in/";
    public static RequestSpecification getRequestBaseSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(baseURL)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    public static ResponseSpecification getResponse200Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification getResponse201Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }

    public static ResponseSpecification getResponse204Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();
    }

    public static void setBaseRestAssuredSpec() {
        RestAssured.requestSpecification = getRequestBaseSpec();
    }
}
