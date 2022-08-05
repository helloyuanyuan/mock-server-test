package com.audatarget.scheduling.expecations.v2;

import static org.mockserver.model.HttpResponse.notFoundResponse;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.OpenAPIDefinition.openAPI;

import com.audatarget.scheduling.MockServerTestBase;
import com.audatarget.scheduling.v2.model.DayResViewModel;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockserver.model.HttpStatusCode;

public class DayGetAll extends MockServerTestBase implements BeforeTestExecutionCallback {

  public static final DayResViewModel getDayResViewModel() {
    DayResViewModel dayResViewModel = new DayResViewModel();
    dayResViewModel.setId(200);
    dayResViewModel.setName("TEST NAME");
    return dayResViewModel;
  }

  @Override
  public void beforeTestExecution(ExtensionContext context) throws Exception {
    resetMockServer();
    CLIENT
        .when(openAPI(V2_OPEN_API_URL))
        .respond(
            httpRequest -> {
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.OK_200.toString())) {
                return response()
                    .withStatusCode(200)
                    .withHeaders(header())
                    .withBody(body(getDayResViewModel()));
              }
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.UNAUTHORIZED_401.toString())) {
                return response().withStatusCode(401).withHeaders(header());
              }
              if (httpRequest
                  .getHeader(AUTH_HEADER)
                  .get(0)
                  .contains(HttpStatusCode.INTERNAL_SERVER_ERROR_500.toString())) {
                return response().withStatusCode(500).withHeaders(header());
              } else {
                return notFoundResponse();
              }
            });
  }
}