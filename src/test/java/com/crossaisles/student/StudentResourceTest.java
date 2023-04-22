package com.crossaisles.student;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class StudentResourceTest {
    @Test
    void getStudents() {
        RestAssured
                .given()
                .when()
                .get("/students")
                .then()
                .body("size()", equalTo(6))
                .body("branch", hasItems("CS","EE"));
    }

    @Test
    void saveStudent() {
        JsonObject student = Json.createObjectBuilder()
                .add("name", "Radhe Shayam")
                .add("branch", "CS")
                .build();
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(student.toString())
                .when()
                .post("/students")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

}