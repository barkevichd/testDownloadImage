package com.automation;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecuteJar {

    public String executeJarWithParams(String pathToImageFolder, String pathToDB, String dbName, String jarName) {
        String command = String.format("java -Dimage.folder=%s -Ddatabase.folder=%s -Ddatabase.name=%s -jar %s",
                pathToImageFolder, pathToDB, dbName, Constants.JAR_FOLDER + jarName);
        return execCmdAndGetResultString(command);
    }

    public String executeJarWithoutParams(String jarName) {
        String command = String.format("java -jar %s", Constants.JAR_FOLDER + jarName);
        return execCmdAndGetResultString(command);
    }

    private String execCmdAndGetResultString(String command) {
        Process p = null;
        StringBuffer result = new StringBuffer();
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
