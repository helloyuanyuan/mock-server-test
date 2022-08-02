package com.solera.global.qa.mockservertest.cases.audaTarget.scheduling.v2;

import static io.restassured.RestAssured.given;

import com.solera.global.qa.mockservertest.MockServerTestBase;
import com.solera.global.qa.mockservertest.common.AuthHeader;
import com.solera.global.qa.mockservertest.common.junitAnnotation.Duration;
import com.solera.global.qa.mockservertest.common.junitExtension.SchedulingApiV2InsurerRepairerAppointmentCreateExtension;
import com.solera.global.qa.mockservertest.common.junitLogger.LifecycleLogger;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openapitools.client.model.SelfViewModel;
import org.openapitools.client.model.ValidationResultModel;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("AudaTarget.Scheduling.v2.InsurerRepairerAppointmentCreateTests")
@Duration
@ExtendWith(SchedulingApiV2InsurerRepairerAppointmentCreateExtension.class)
class InsurerRepairerAppointmentCreateTests extends MockServerTestBase implements LifecycleLogger {

  private final String API_PATH =
      MOCK_SERVER_URL + "/api/v2/insurers/{insurerId}/repairers/{repairerId}/appointments";

  @Test
  void testPostStatusCode201() throws Exception {
    SelfViewModel actualResult =
        given()
            .log()
            .all()
            .pathParam("insurerId", UUID.randomUUID())
            .pathParam("repairerId", UUID.randomUUID())
            .header(AuthHeader.OK_200.header())
            .body(
                SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getCreateAppointmentData())
            .then()
            .log()
            .all()
            .expect()
            .statusCode(200)
            .when()
            .post(API_PATH)
            .as(SelfViewModel.class);
    Assertions.assertThat(actualResult)
        .isEqualTo(SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getSelfViewModel());
  }

  @Test
  void testPostStatusCode400() throws Exception {
    ValidationResultModel actualResult =
        given()
            .log()
            .all()
            .pathParam("insurerId", UUID.randomUUID())
            .pathParam("repairerId", UUID.randomUUID())
            .header(AuthHeader.BAD_REQUEST_400.header())
            .body(
                SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getCreateAppointmentData())
            .then()
            .log()
            .all()
            .expect()
            .statusCode(400)
            .when()
            .post(API_PATH)
            .as(ValidationResultModel.class);
    Assertions.assertThat(actualResult)
        .isEqualTo(
            SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getValidationResultModel());
  }

  @Test
  void testPostStatusCode401() throws Exception {
    ValidationResultModel actualResult =
        given()
            .log()
            .all()
            .pathParam("insurerId", UUID.randomUUID())
            .pathParam("repairerId", UUID.randomUUID())
            .header(AuthHeader.UNAUTHORIZED_401.header())
            .body(
                SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getCreateAppointmentData())
            .then()
            .log()
            .all()
            .expect()
            .statusCode(401)
            .when()
            .post(API_PATH)
            .as(ValidationResultModel.class);
    Assertions.assertThat(actualResult)
        .isEqualTo(
            SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getValidationResultModel());
  }

  @Test
  void testPostStatusCode404() throws Exception {
    given()
        .log()
        .all()
        .pathParam("insurerId", UUID.randomUUID())
        .pathParam("repairerId", UUID.randomUUID())
        .header(AuthHeader.NOT_FOUND_404.header())
        .body(SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getCreateAppointmentData())
        .then()
        .log()
        .all()
        .expect()
        .statusCode(404)
        .when()
        .post(API_PATH);
  }

  @Test
  void testPostStatusCode409() throws Exception {
    ValidationResultModel actualResult =
        given()
            .log()
            .all()
            .pathParam("insurerId", UUID.randomUUID())
            .pathParam("repairerId", UUID.randomUUID())
            .header(AuthHeader.CONFLICT_409.header())
            .body(
                SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getCreateAppointmentData())
            .then()
            .log()
            .all()
            .expect()
            .statusCode(409)
            .when()
            .post(API_PATH)
            .as(ValidationResultModel.class);
    Assertions.assertThat(actualResult)
        .isEqualTo(
            SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getValidationResultModel());
  }

  @Test
  void testPostStatusCode422() throws Exception {
    ValidationResultModel actualResult =
        given()
            .log()
            .all()
            .pathParam("insurerId", UUID.randomUUID())
            .pathParam("repairerId", UUID.randomUUID())
            .header(AuthHeader.UNPROCESSABLE_ENTITY_422.header())
            .body(
                SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getCreateAppointmentData())
            .then()
            .log()
            .all()
            .expect()
            .statusCode(422)
            .when()
            .post(API_PATH)
            .as(ValidationResultModel.class);
    Assertions.assertThat(actualResult)
        .isEqualTo(
            SchedulingApiV2InsurerRepairerAppointmentCreateExtension.getValidationResultModel());
  }
}
