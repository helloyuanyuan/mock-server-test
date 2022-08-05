package com.audatarget.scheduling.expecations.v2;

import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.OpenAPIDefinition.openAPI;

import com.audatarget.scheduling.MockServerTestBase;
import com.audatarget.scheduling.v2.model.CreateAppointmentData;
import com.audatarget.scheduling.v2.model.SelfViewModel;
import com.audatarget.scheduling.v2.model.ValidationResultModel;
import java.util.UUID;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockserver.model.HttpStatusCode;

public class InsurerRepairerAppointmentCreate extends MockServerTestBase
    implements BeforeTestExecutionCallback {

  public static final CreateAppointmentData getCreateAppointmentData() {
    CreateAppointmentData createAppointmentData = new CreateAppointmentData();
    createAppointmentData.setReservationId(UUID.randomUUID());
    createAppointmentData.setClaimId(UUID.randomUUID());
    createAppointmentData.emailAddress("TEST@EMAIL.COM");
    return createAppointmentData;
  }

  public static final SelfViewModel getSelfViewModel() {
    SelfViewModel selfViewModel = new SelfViewModel();
    selfViewModel.setId("200");
    selfViewModel.setUrl("TEST URL");
    return selfViewModel;
  }

  public static final ValidationResultModel getValidationResultModel() {
    return new ValidationResultModel();
  }

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {
    resetMockServer();
    CLIENT
        .when(openAPI(V2_OPEN_API_URL, "Insurer.Repairer.Appointment.Create"))
        .respond(
            httpRequest -> {
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.OK_200.toString())) {
                return response()
                    .withStatusCode(200)
                    .withHeaders(header())
                    .withBody(body(getSelfViewModel()));
              }
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.BAD_REQUEST_400.toString())) {
                return response()
                    .withStatusCode(400)
                    .withHeaders(header())
                    .withBody(body(getValidationResultModel()));
              }
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.UNAUTHORIZED_401.toString())) {
                return response()
                    .withStatusCode(401)
                    .withHeaders(header())
                    .withBody(body(getValidationResultModel()));
              }
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.CONFLICT_409.toString())) {
                return response()
                    .withStatusCode(409)
                    .withHeaders(header())
                    .withBody(body(getValidationResultModel()));
              }
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.UNPROCESSABLE_ENTITY_422.toString())) {
                return response()
                    .withStatusCode(422)
                    .withHeaders(header())
                    .withBody(body(getValidationResultModel()));
              } else {
                return notFoundResponse();
              }
            });
  }
}