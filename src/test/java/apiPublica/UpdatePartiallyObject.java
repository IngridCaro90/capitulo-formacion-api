package apiPublica;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ExtentReportGenerate;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UpdatePartiallyObject {
    private ExtentReportGenerate extentReportGenerate;

    @BeforeClass
    public void setUp() {
        RestAssured.useRelaxedHTTPSValidation("SSL");
        RestAssured.proxy("piscis01.bancodebogota.net", 8003);
        extentReportGenerate = new ExtentReportGenerate();
    }

    @Test
    public void updatePartiallyObject() {
        extentReportGenerate.generateReport("updatePartiallyObjectTest");
        File patchBody = new File("src/main/resources/patchObject.json");

        RequestSpecification request = given()
                .baseUri("https://api.restful-api.dev")
                .basePath("/objects")
                .header("Content-Type", "application/json")
                .body(patchBody);

        Response response = request
                .when()
                .patch("/ff80818190910e08019092e8d1220766");

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
