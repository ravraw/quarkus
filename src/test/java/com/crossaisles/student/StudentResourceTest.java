package com.crossaisles.student;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.hamcrest.CoreMatchers;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class StudentResourceTest {
    @Test
    void getStudents() {
        RestAssured
                .given()
                .when()
                .get("/students")
                .then()
                .body("size()", equalTo(5))
                .body("branch", hasItems("CS","EE"));
    }

}