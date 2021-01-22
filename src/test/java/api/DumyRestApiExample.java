package api;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.assertEquals;

public class DumyRestApiExample {

    @BeforeClass
    public void startUp(){
        RestAssured.baseURI = "http://dummy.restapiexample.com";
    }

    @Test
    public void getAllEmployeeTest(){
        given()
                .log().all()
                .when()
                .get("/api/v1/employees")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("status",equalTo("success"))
                .body("data.id", hasItems("1","2","3"));
    }

    @Test
    public void getAllEmployeeNegativeTest(){
        given()
                .log().all()
                .when()
                .get("/api/v1/employees1")
                .then()
                .log().all()
                .statusCode(404)
                .assertThat()
                .body("message",equalTo("Error Occured! Page Not found, contact rstapi2example@gmail.com"));

    }

    @Test
    public void getEmployeeTest(){
        given()
                .log().all()
                .when()
                .get("/api/v1/employee/1")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("status",equalTo("success"))
                .body("data.id", equalTo(1));
    }

    @Test
    public void getEmployeeNegativeTest(){
        given()
                .log().all()
                .when()
                .get("/api/v1/employee/25")
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("status",equalTo("success"))
                .body("data", Matchers.nullValue());
    }

    @Test
    public  void postEmployeeTest (){
        PostRequestModel postRequest = new PostRequestModel("Mike", "4900", "30");

        EmployeeData employeeData = new EmployeeData("Mike" , "4900", "30");
        PostResponseModel expectedResponseBody = new PostResponseModel("success", employeeData,"Successfully! Record has been added.");

        PostResponseModel responseBodyObject = given()
                .contentType("application/json")
                .with()
                .log().all()
                .body(postRequest.toString())
                .when()
                .request("POST", "/api/v1/create")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(PostResponseModel.class);
        assertEquals(expectedResponseBody, responseBodyObject);
    }


    @Test
    public  void postEmployeeNegativeTest (){
        PostRequestModel postRequest = new PostRequestModel("Mike", "4900", "30");

        EmployeeData employeeData = new EmployeeData("Mike" , "4900", "30");
        PostResponseModel expectedResponseBody = new PostResponseModel("success", employeeData,"Successfully! Record has been added.");

        given()
                .contentType("application/json")
                .with()
                .log().all()
                .body(postRequest.toString())
                .when()
                .request("POST", "/api/v1/create")
                .then()
                .log().all()
                .statusCode(not(201))
                .body(not(empty()));
//        assertEquals(responseBodyObject, expectedResponseBody);
    }

    @Test
    public void putEmployeeTest(){
        PostRequestModel postRequest = new PostRequestModel("Mike", "4900", "30");

        EmployeeData employeeData = new EmployeeData("Mike" , "4900", "30");
        PostResponseModel expectedResponseBody = new PostResponseModel("success", employeeData,"Successfully! Record has been updated.");

        PostResponseModel responseBodyObject = given()
                .contentType("application/json")
                .with()
                .log().all()
                .body(postRequest.toString())
                .when()
                .request("PUT", "/api/v1/update/21")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(PostResponseModel.class);
        assertEquals(expectedResponseBody, responseBodyObject);
    }

    @Test
    public  void putEmployeeNegativeTest (){
        PostRequestModel postRequest = new PostRequestModel("Mike", "4900", "30");

        EmployeeData employeeData = new EmployeeData("Mike" , "4900", "30");
        PostResponseModel expectedResponseBody = new PostResponseModel("success", employeeData,"Successfully! Record has been added.");

        given()
                .contentType("application/json")
                .with()
                .log().all()
                .body(postRequest.toString())
                .when()
                .request("PUT", "/api/v1/update/21")
                .then()
                .log().all()
                .statusCode(not(201))
                .body(not(empty()));
//        assertEquals(responseBodyObject, expectedResponseBody);
    }

    @Test
    public void deleteEmployeeTest(){
        when()
                .delete("/api/v1/delete/2")
                .then()
                .statusCode(200)
                .assertThat()
                .body("status", equalTo("success"))
                .body("message",equalTo("Successfully! Record has been deleted"))
                .body("data",equalTo("2"));

    }

    @Test
    public void deleteEmployeeNegativeTest(){
        Pattern p = Pattern.compile("^\\{.*\\}$");

        when()
                .post("/api/v1/delete/2")
                .then()
                .statusCode(405)
                .assertThat()
                .body(not(Matchers.matchesPattern(p)));

    }

}
