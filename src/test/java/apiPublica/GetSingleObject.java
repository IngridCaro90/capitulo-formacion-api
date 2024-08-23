package apiPublica;

import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ExtentReportGenerate;

import static io.restassured.RestAssured.given;

public class GetSingleObject {
    private ExtentReportGenerate extentReportGenerate;

    @BeforeClass
    public void setUp() {
        extentReportGenerate = new ExtentReportGenerate();
    }

    @Test
    public void getSingleObject() {
        extentReportGenerate.generateReport("getSingleObjectTest");
        RequestSpecification request = given()
                .baseUri("https://api.restful-api.dev")
                .basePath("/objects");

        Response response  = request
                .when()
                .get("/4");

        extentReportGenerate.getExtentTestLog(Status.INFO, "Se realizó el consumo del api.");
        response.prettyPrint();
        int statusCode = response.getStatusCode();
        extentReportGenerate.getExtentTestLog(Status.INFO, String.format("El código recibido es %d.", statusCode));
        Assert.assertEquals(statusCode, 200);
        extentReportGenerate.getExtentTestLog(Status.INFO, "El resultado de la prueba fue exitoso.");

        JSONObject jsonResponse = new JSONObject(response.asString());
        String name = jsonResponse.getString("name");
        System.out.println(name);

        JSONObject jsonData = jsonResponse.getJSONObject("data");
        String cpuModel = jsonData.getString("color");
        System.out.println(cpuModel);
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
