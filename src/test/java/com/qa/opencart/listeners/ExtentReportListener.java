package com.qa.opencart.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;

import static com.qa.opencart.factory.PlaywrightFactoryThreadLocal.takeScreenshot;

public class ExtentReportListener implements ITestListener {

    private static final String OUTPUT_FOLDER = "./build/";
    private static final String FILE_NAME = "TestExecutionReport.html";

    private static final ExtentReports extent = init();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private static ExtentReports init() {
        Path path = Paths.get(OUTPUT_FOLDER);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ExtentReports extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        reporter.config().setReportName("KOEL Automation Test Results");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System", "MAC");
        extentReports.setSystemInfo("Author", "Gans AutomationLabs");
        extentReports.setSystemInfo("Build#", "1.1");
        extentReports.setSystemInfo("Team", "OMS");
        extentReports.setSystemInfo("Customer Name", "GMS");
        extentReports.setSystemInfo("ENV Name", "STAGE");

        return extentReports;
    }

    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite Started!!!");
    }

    public synchronized void onFinish(ITestContext context) {
        System.out.println("Test Suite is Ending!!!");
        extent.flush();
        test.remove();
    }

    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        System.out.println(methodName + " Started!!!");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());
        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " Passed!!!");
        test.get().pass("Test Passed!!!");
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " Failed!!!");
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " Skipped!!!");
        test.get().fail(result.getThrowable(), MediaEntityBuilder.createScreenCaptureFromPath(takeScreenshot()).build());
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailedButWithSuccessPercentage(ITestResult result) {
        System.out.println("onTestFailedButWithSuccessPercentage for " + result.getMethod().getMethodName());
    }

    private Date getTime(long mills){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.getTime();
    }

}