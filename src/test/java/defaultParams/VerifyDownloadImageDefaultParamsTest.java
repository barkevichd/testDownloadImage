package defaultParams;

import com.automation.*;
import org.apache.commons.collections4.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerifyDownloadImageDefaultParamsTest extends TestBase {
    Logger LOG = Logger.getLogger(this.getClass().getName());

    @Test(groups = {"all", "default"}, description = "verify that all files and data created properly during first execution of program with default params")
    public void executeProgramFirstTimeDefaultParams() {
        LOG.log(Level.INFO, "Check if folders are exists, if yes - delete them");
        if (utils.checkFileOrFolderExists(Constants.IMAGE_FOLDER_DEFAULT)) {
            utils.deleteDirectory(Constants.IMAGE_FOLDER_DEFAULT);
        }
        if (utils.checkFileOrFolderExists(Constants.DATABASE_FOLDER_DEFAULT)) {
            utils.deleteDirectory(Constants.DATABASE_FOLDER_DEFAULT);
        }

        LOG.log(Level.INFO, "Take current date before downloading to compare with stored in DB");
        dateBefore = utils.getCurrentDate();

        LOG.log(Level.INFO, "Execute jar with default params");
        String result = executeJar.executeJarWithoutParams(Constants.JAR_NAME);

        LOG.log(Level.INFO, "Verify there are no errors or exceptions in console output");
        Assert.assertFalse(result.contains("Error"), "Console output contains Error message " + result);
        Assert.assertFalse(result.contains("Exception"), "Console output contains Exception message " + result);
        Assert.assertTrue(result.contains("Program executed successfully"), "Console output does not contains result message " + result);

        LOG.log(Level.INFO, "Verify that only one file downloaded");
        utils.getListOfAllFilesFromDirectory(Constants.IMAGE_FOLDER_DEFAULT, filesDownloaded);
        Assert.assertEquals(1, filesDownloaded.size(), "Downloaded files count is not match expected");

        LOG.log(Level.INFO, "Verify that only one DB created");
        utils.getListOfAllFilesFromDirectory(Constants.DATABASE_FOLDER_DEFAULT, databasesList);
        Assert.assertEquals(1, databasesList.size(), "Data bases files count is not match expected");

        LOG.log(Level.INFO, "Take current date after downloading to compare with stored in DB");
        dateAfter = utils.getCurrentDate();

        LOG.log(Level.INFO, "Verify values stored in DB are match expected");
        Connection conn = databaseConnection.createConnection(Constants.DATABASE_FOLDER_DEFAULT, Constants.DATABASE_NAME_DEFAULT);
        resultsMap = databaseConnection.getResultAsMap(conn);
        databaseConnection.closeConnection(conn);
        utils.checkLinkIsCorrect(resultsMap.get("address"));
        utils.checkDateBetweenTwoDates(utils.dateConverter(dateBefore), utils.dateConverter(dateAfter), utils.dateConverter(resultsMap.get("timeOfDownload")));
        Assert.assertEquals(0, Long.valueOf(resultsMap.get("size")).compareTo(filesDownloaded.get(0).length()));

        LOG.log(Level.INFO, "Test executeProgramFirstTimeDefaultParams finished successfully");
    }

    @Test(groups = {"all", "default"}, description = "verify that all files and data are added properly during second and further execution of program with default params")
    public void executeProgramToPopulateDataDefaultParams() {
        LOG.log(Level.INFO, "Check if folders are exists, if no - execute test program once to create default data");
        if (!utils.checkFileOrFolderExists(Constants.IMAGE_FOLDER_DEFAULT) |
                !utils.checkFileOrFolderExists(Constants.DATABASE_FOLDER_DEFAULT
                        + File.separator + Constants.DATABASE_NAME_DEFAULT)) {
            String tmpRes = executeJar.executeJarWithoutParams(Constants.JAR_NAME);
            Assert.assertFalse(tmpRes.contains("Error"), "Console output contains Error message " + tmpRes);
            Assert.assertFalse(tmpRes.contains("Exception"), "Console output contains Exception message " + tmpRes);
            Assert.assertTrue(tmpRes.contains("Program executed successfully"), "Console output does not contains result message " + tmpRes);
        }

        LOG.log(Level.INFO, "Get list of all images in directory");
        utils.getListOfAllFilesFromDirectory(Constants.IMAGE_FOLDER_DEFAULT, filesBeforeDownload);

        LOG.log(Level.INFO, "Take current date before downloading to compare with stored in DB");
        dateBefore = utils.getCurrentDate();

        LOG.log(Level.INFO, "Execute jar with default params");
        String result = executeJar.executeJarWithoutParams(Constants.JAR_NAME);

        LOG.log(Level.INFO, "Verify there are no errors or exceptions in console output");
        Assert.assertFalse(result.contains("Error"), "Console output contains Error message " + result);
        Assert.assertFalse(result.contains("Exception"), "Console output contains Exception message " + result);
        Assert.assertTrue(result.contains("Program executed successfully"), "Console output does not contains result message " + result);

        LOG.log(Level.INFO, "Verify that only one file downloaded");
        utils.getListOfAllFilesFromDirectory(Constants.IMAGE_FOLDER_DEFAULT, filesDownloaded);
        ArrayList<File> downloadedFile = (ArrayList<File>) CollectionUtils.disjunction(filesDownloaded, filesBeforeDownload);
        Assert.assertEquals(1, filesDownloaded.size() - filesBeforeDownload.size(), "Downloaded files count is not match expected");
        utils.getListOfAllFilesFromDirectory(Constants.DATABASE_FOLDER_DEFAULT, databasesList);

        LOG.log(Level.INFO, "Verify that still one DB exist");
        Assert.assertEquals(1, databasesList.size(), "Data bases files count is not match expected");

        LOG.log(Level.INFO, "Take current date after downloading to compare with stored in DB");
        dateAfter = utils.getCurrentDate();

        LOG.log(Level.INFO, "Verify values stored in DB are match expected");
        Connection conn = databaseConnection.createConnection(Constants.DATABASE_FOLDER_DEFAULT, Constants.DATABASE_NAME_DEFAULT);
        resultsMap = databaseConnection.getResultAsMap(conn);
        databaseConnection.closeConnection(conn);
        utils.checkLinkIsCorrect(resultsMap.get("address"));
        utils.checkDateBetweenTwoDates(utils.dateConverter(dateBefore), utils.dateConverter(dateAfter), utils.dateConverter(resultsMap.get("timeOfDownload")));
        Assert.assertEquals(0, Long.valueOf(resultsMap.get("size")).compareTo(downloadedFile.get(0).length()));

        LOG.log(Level.INFO, "Test executeProgramToPopulateDataDefaultParams finished successfully");
    }
}
