package com.crossaisles.student;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.vertx.http.runtime.devmode.Json;
import io.restassured.RestAssured;
import org.h2.util.json.JSONObject;
import org.junit.jupiter.api.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentResourceTest {
    @Test
    @Order(1)
    void getStudents() {
        RestAssured
                .given()
                .when()
                .get("/students")
                .then()
                .body("size()", equalTo(5))
                .body("branch", hasItems("CS","EE"));
    }

//    @Test
//    @Order(2)
//    void saveStudent() {
//        Student student = new Student();
//        student.setId(10L);
//        student.setName("Student No.1");
//        student.setBranch("CS");
//
//        RestAssured.given()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(student)
//                .when()
//                .post("/students")
//                .then()
//                .statusCode(Response.Status.CREATED.getStatusCode());
//    }

}