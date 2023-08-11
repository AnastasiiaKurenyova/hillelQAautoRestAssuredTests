package ua.ithillel.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.ithillel.dto.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ReqresTest {

    private static int userId = 1;

    @BeforeEach
    public void init() {
        BaseApiTest.setBaseRestAssuredSpec();
    }

    @Test
    public void getListResourceTest() {
        List<String> actualNamesList = Arrays.asList("cerulean", "fuchsia rose", "true red", "aqua sky", "tigerlily", "blue turquoise");
        List<ResourceObject> resourceObjectList = given()
                .when()
                .get("api/unknown")
                .then()
                .extract()
                .jsonPath()
                .getList("data", ResourceObject.class);

        boolean isContainsNames = resourceObjectList.stream()
                .map(ResourceObject::getName)
                .anyMatch(actualNamesList::contains);

        Assertions.assertTrue(isContainsNames);
    }

    @Test
    public void postCreateUserTest() {
        CreateUserObject createUserObject = new CreateUserObject("morpheus", "leader");
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        userId = given()
                .when()
                .body(createUserObject)
                .post("api/users")
                .then()
                .spec(BaseApiTest.getResponse201Spec())
                .body("name", Matchers.equalTo("morpheus"))
                .body("job", Matchers.equalTo("leader"))
                .body("id", Matchers.matchesRegex("\\d+"))
                .body("createdAt", Matchers.startsWith(timeStamp))
                .extract()
                .jsonPath()
                .getInt("id");
    }

    @Test
    public void putUpdateUserTest() {
        UpdateUserObject updateUserObject = new UpdateUserObject("morpheus", "zion resident");
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        given()
                .when()
                .body(updateUserObject)
                .put("api/users/" + userId)// userId 727
                .then()
                .spec(BaseApiTest.getResponse200Spec())
                .body("name", Matchers.equalTo("morpheus"))
                .body("job", Matchers.equalTo("zion resident"))
                .body("updatedAt", Matchers.startsWith(timeStamp));
    }

    @Test
    public void deleteUserTest() {
        given()
                .when()
                .delete("api/users/" + userId)
                .then()
                .spec(BaseApiTest.getResponse204Spec());
    }

    @Test
    public void postLoginSuccessfulTest() throws IOException {
        File userJson = new File("src/test/java/ua/ithillel/json/userLogin.json");
        UserLoginObject userLogin = new ObjectMapper().readValue(userJson, UserLoginObject.class);
        given()
                .when()
                .body(userLogin)
                .post("api/login/")
                .then()
                .spec(BaseApiTest.getResponse200Spec())
                .body("token", Matchers.notNullValue());
    }
}
