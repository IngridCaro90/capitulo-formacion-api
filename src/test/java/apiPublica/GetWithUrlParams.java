package apiPublica;

import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ExtentReportGenerate;

import static io.restassured.RestAssured.given;

public class GetWithUrlParams {
    private ExtentReportGenerate extentReportGenerate;

    @BeforeClass
    public void setUp() {
        extentReportGenerate = new ExtentReportGenerate();
    }

    //@Test
    public void getWitUrlParams() {
        extentReportGenerate.generateReport("getWitUrlParamsTest");
        RequestSpecification request = given()
                .baseUri("https://api.restful-api.dev")
                .basePath("/objects")
                .formParam("id", "1")
                .formParam("id", "2");

        Response response  = request
                .when()
                .get();

        extentReportGenerate.getExtentTestLog(Status.INFO, "Se realizó el consumo del api.");
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        extentReportGenerate.getExtentTestLog(Status.INFO, String.format("El código recibido es %d.", statusCode));
        Assert.assertEquals(statusCode, 200);
        extentReportGenerate.getExtentTestLog(Status.INFO, "El resultado de la prueba fue exitoso.");
    }

    @Test
    public void getWitUrlParams2() {
        extentReportGenerate.generateReport("getWitUrlParams2Test");
        RequestSpecification request = given()
                .baseUri("https://api.restful-api.dev")
                .basePath("/objects")
                .formParam("id", 1, 2);

        Response response  = request
                .when()
                .get();

        extentReportGenerate.getExtentTestLog(Status.INFO, "Se realizó el consumo del api.");
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        extentReportGenerate.getExtentTestLog(Status.INFO, String.format("El código recibido es %d.", statusCode));
        Assert.assertEquals(statusCode, 200);
        extentReportGenerate.getExtentTestLog(Status.INFO, "El resultado de la prueba fue exitoso.");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentReportGenerate.getExtentTestLog(Status.FAIL, "Fue fallido el test: ");
        } else {
            extentReportGenerate.getExtentTestLog(Status.PASS, "Fue exitoso el test: ");
        }
        extentReportGenerate.closeConnectionExtent();
    }

}
