package com.automation;

import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class TestBase {
    public Properties properties;
    public Utils utils;
    public ExecuteJar executeJar;
    public DatabaseConnection databaseConnection;
    public String dateBefore = null;
    public String dateAfter = null;
    public ArrayList<File> filesDownloaded;
    public ArrayList<File> databasesList;
    public HashMap<String, String> resultsMap;
    public ArrayList<File> filesBeforeDownload;

    @BeforeMethod(alwaysRun = true)
    public void initAll() {
        properties = new Properties();
        properties.init();
        utils = new Utils();
        executeJar = new ExecuteJar();
        databaseConnection = new DatabaseConnection();
        filesDownloaded = new ArrayList<>();
        databasesList = new ArrayList<>();
        resultsMap = new HashMap<>();
        filesBeforeDownload = new ArrayList<>();

    }
}
