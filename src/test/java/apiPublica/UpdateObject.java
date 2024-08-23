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

import java.io.File;

import static io.restassured.RestAssured.given;

public class UpdateObject {
    private ExtentReportGenerate extentReportGenerate;

    @BeforeClass
    public void setUp() {
        extentReportGenerate = new ExtentReportGenerate();
    }

    @Test
    public void updateObject() {
        extentReportGenerate.generateReport("updateObjectTest");
        File putBody = new File("src/main/resources/putObject.json");

        RequestSpecification request = given()
                .baseUri("https://api.restful-api.dev")
                .basePath("/objects")
                .header("Content-Type", "application/json")
                .body(putBody);

        Response response = request
                .when()
                .put("ff80818190910e08019092e8d1220766");

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
            extentReportGenerate.getExtentTestLog(Status.PASS, "Fue exitoso el test");
        }
        extentReportGenerate.closeConnectionExtent();
    }

}
