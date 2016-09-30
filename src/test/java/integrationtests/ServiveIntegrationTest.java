/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationtests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Test;

public class ServiveIntegrationTest {

    public ServiveIntegrationTest() {
    }

    @BeforeClass
    public static void setUpBeforeAll() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/Test1";
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void serverIsRunning() {
        given().
                when().get().
                then().
                statusCode(200);
    }

    @Test
    public void addOperation() {
        given().pathParam("n1", 2).pathParam("n2", 2).
                when().get("/api/calculator/add/{n1}/{n2}").
                then().
                statusCode(200).
                body("result", equalTo(4), "operation", equalTo("2 + 2"));
    }

    @Test
    public void addOperationv2() {
        given().
                when().get("/api/calculator/add/2/2").
                then().
                statusCode(200).
                body("result", equalTo(4), "operation", equalTo("2 + 2"));
    }

    @Test
    public void subtractOperationv2() {
        given().
                when().get("/api/calculator/sub/10/5").
                then().
                statusCode(200).
                body("result", equalTo(5), "operation", equalTo("10 - 5"));
    }

    @Test
    public void divisionOperationv2() {
        given().
                when().get("/api/calculator/div/100/5").
                then().
                statusCode(200).
                body("result", equalTo(20), "operation", equalTo("100 / 5"));
    }

    @Test
    public void multiplicationOperationv2() {
        given().
                when().get("/api/calculator/mul/10/4").
                then().
                statusCode(200).
                body("result", equalTo(40), "operation", equalTo("10 * 4"));
    }

    @Test
    public void addOperationWrongArguments() {
        given().pathParam("n1", 2).pathParam("n2", 2.2).
                when().get("/api/calculator/add/{n1}/{n2}").
                then().
                statusCode(400).
                body("code", equalTo(400));
    }

    @Test
    public void divisionByZeroError() {
        given().pathParam("n1", 10).pathParam("n2", 0).
                when().get("/api/calculator/div/{n1}/{n2}").
                then().
                statusCode(400).
                body("code", equalTo(400));
    }

    @Test
    public void pathNotFound() {
        given().pathParam("n1", 2).pathParam("n2", 2.2).
                when().get("/api/calculator/adddddd/{n1}/{n2}").
                then().
                statusCode(404).
                body("code", equalTo(404));
    }
}
