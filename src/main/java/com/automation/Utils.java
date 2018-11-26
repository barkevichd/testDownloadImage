package com.automation;

import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class Utils {

    public boolean checkFileOrFolderExists(String pathToFile){
        File f = new File(pathToFile);
        if(!f.isFile() && !f.isDirectory()) {
           return false;
        }
        return true;
    }

    public void createFolder(String path) {
        try {
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
        } catch (SecurityException e) {
            throw new TestAutomationException("Folder cannot be created: permission denied or provided path is not correct", e.getCause());
        }
    }

    public void deleteDirectory(String pathToFolder) {
        try {
            File directory = new File(pathToFolder);
            if (directory.isDirectory()) {
                org.apache.commons.io.FileUtils.deleteDirectory(directory);
            }
        } catch (IOException e) {
            throw new TestAutomationException("Could not delete folder by provided path " + pathToFolder, e);
        }
    }

    public void getListOfAllFilesFromDirectory(String directoryPath, ArrayList<File> files) {
        File dir = new File(directoryPath);
        File[] filesArray = dir.listFiles();

        if (files != null) {
            for (File file : filesArray) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    getListOfAllFilesFromDirectory(file.getAbsolutePath(), files);
                }
            }
        }
    }

    public String getCurrentDate(){
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Date());
    }

    public Date dateConverter(String date){
        String pattern = "yyyy.MM.dd.HH.mm.ss.SSS";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new TestAutomationException("Could not parse date", e.getCause());
        }
    }


    public void checkDateBetweenTwoDates(Date dateBefore, Date dateAfter, Date dateToCheck){
       Assert.assertTrue(dateToCheck.after(dateBefore) && dateToCheck.before(dateAfter),"Date is not between two dates");

    }

    public void checkLinkIsCorrect(String link){
        Pattern p = Pattern.compile("http:.*images.*jpg");
        Assert.assertTrue(p.matcher(link).find(), "Link format doesn't match expected");
    }
}
