package com.petstore.steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

public class StoreSteps {

    private Response response;
    private String baseUrl = "https://petstore.swagger.io/v2";
    private Map<String, Object> orderData;

    @Given("que el sistema está conectado a la base de datos")
    public void setupDatabaseConnection() {
        // No se necesita simulación de BD, suposiciones de conectividad aquí.
    }

    @Given("que el API de Store está disponible")
    public void setupApiAvailability() {
        RestAssured.baseURI = baseUrl;
    }

    @When("envío una solicitud POST a \"/store/order\" con los siguientes datos:")
    public void envioUnaSolicitudPOST(Map<String, String> data) {
        orderData = new HashMap<>();
        orderData.put("id", Integer.parseInt(data.get("id")));
        orderData.put("petId", Integer.parseInt(data.get("petId")));
        orderData.put("quantity", Integer.parseInt(data.get("quantity")));
        orderData.put("shipDate", data.get("shipDate"));
        orderData.put("status", data.get("status"));
        orderData.put("complete", Boolean.parseBoolean(data.get("complete")));

        response = given()
                    .contentType("application/json")
                    .body(orderData)
                .when()
                    .post("/store/order")
                .then()
                    .extract().response();
    }

    @Then("el código de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(int statusCode) {
        assertEquals(statusCode, response.statusCode());
    }

    @Then("el cuerpo del response debe contener el ID de la orden {int}")
    public void elCuerpoDelResponseDebeContenerElIDDeLaOrden(int id) {
        assertEquals(id, response.jsonPath().getInt("id"));
    }

    @When("envío una solicitud GET a \"/store\\/order\\/{int}\"")
    public void envioUnaSolicitudGET(int orderId) {
        response = given()
                .pathParam("orderId", orderId)
                .when()
                    .get("/store/order/{orderId}")
                .then()
                    .extract().response();
    }

    @Then("el cuerpo del response debe contener los datos de la orden:")
    public void elCuerpoDelResponseDebeContenerLosDatosDeLaOrden(Map<String, String> data) {
        assertEquals(Integer.parseInt(data.get("id")), response.jsonPath().getInt("id"));
        assertEquals(Integer.parseInt(data.get("petId")), response.jsonPath().getInt("petId"));
        assertEquals(Integer.parseInt(data.get("quantity")), response.jsonPath().getInt("quantity"));
        assertEquals(data.get("status"), response.jsonPath().getString("status"));
        assertEquals(Boolean.parseBoolean(data.get("complete")), response.jsonPath().getBoolean("complete"));
    }
}
