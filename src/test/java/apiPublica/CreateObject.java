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

public class CreateObject {
    private ExtentReportGenerate extentReportGenerate;

    @BeforeClass
    public void setUp() {
        extentReportGenerate = new ExtentReportGenerate();
    }

    @Test
    public void createObject() {
        extentReportGenerate.generateReport("createObjectTest");
        File postBody = new File("src/main/resources/createObject.json");
        extentReportGenerate.getExtentTestLog(Status.INFO, "Pasó la ruta del archivo del body que es " + postBody);

        RequestSpecification request = given()
                .baseUri("https://api.restful-api.dev")
                .basePath("/objects")
                .header("Content-Type", "application/json")
                .body(postBody);

        Response response = request
                .when()
                .post();

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
