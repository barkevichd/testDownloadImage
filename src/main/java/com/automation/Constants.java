package com.automation;

import java.io.File;

public class Constants {
    public static final String RESOURCES = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    public static final String JAR_FOLDER = RESOURCES + "jarFolder" + File.separator;
    public static final String IMAGE_FOLDER_CUSTOM = System.getProperty("custom.image.folder");
    public static final String DATABASE_FOLDER_CUSTOM = System.getProperty("custom.database.folder");
    public static final String DATABASE_NAME_CUSTOM = System.getProperty("custom.database.name");

    public static final String IMAGE_FOLDER_DEFAULT = System.getProperty("default.image.folder");
    public static final String DATABASE_FOLDER_DEFAULT = System.getProperty("default.database.folder");
    public static final String DATABASE_NAME_DEFAULT = System.getProperty("default.database.name");

    public static final String JAR_NAME = System.getProperty("jar.name");
}
