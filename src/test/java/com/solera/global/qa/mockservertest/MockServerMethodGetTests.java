package com.solera.global.qa.mockservertest;

import static io.restassured.RestAssured.given;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockserver.model.Header;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solera.global.qa.mockservertest.beans.Org;
import com.solera.global.qa.mockservertest.common.MockServerBase;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class MockServerMethodGetTests extends MockServerBase {

  @BeforeEach
  void beforeEach() {
    mockServerUtils.reset();
  }

  @Test
  void testGetReturnStringBody() {
    String body = "Hello World!";

    client.when(request().withMethod("GET").withPath("/hello"))
        .respond(response().withStatusCode(200).withBody(body));

    String actualResult = given().log().all()
        .then().log().all().statusCode(200)
        .when().get(URL + "/hello").asString();

    Assertions.assertThat(actualResult).isEqualTo(body);

    mockServerUtils.verify("/hello", 1);
  }

  @Test
  void testGetWithParamReturnJsonBodyToClass() throws Exception {
    Org org = new Org();
    org.setId("1");
    org.setOrgName("solera");

    String body = new ObjectMapper().writeValueAsString(org);

    client
        .when(request().withMethod("GET").withPath("/org")
            .withQueryStringParameter("orgName", "solera"))
        .respond(response().withStatusCode(200)
            .withHeader(new Header("Content-Type", "application/json")).withBody(body));

    Org actualResult = given().log().all()
        .queryParam("orgName", "solera")
        .then().log().all().statusCode(200)
        .when().get(URL + "/org").as(Org.class);

    Assertions.assertThat(actualResult.getId()).isEqualTo(org.getId());
    Assertions.assertThat(actualResult.getOrgName()).isEqualTo(org.getOrgName());

    mockServerUtils.verify("/org", 1);
  }

}