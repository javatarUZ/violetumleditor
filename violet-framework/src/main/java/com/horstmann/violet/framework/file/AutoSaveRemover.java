package com.horstmann.violet.framework.file;

import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 * Created on 2017-02-15.
 */
public class AutoSaveRemover {
    private final String autoSaveDirectoryPath = System.getProperty("user.home") + File.separator + "VioletUML";

    private String maxSizeOfDirectory = "100000000";

    public void removeBackup() {
        final File autoSaveDirectory = new File(autoSaveDirectoryPath);
        final List<File> autoSaveFiles = listFiles(autoSaveDirectory);
        final List<Long> fileTimeCreation = filesCreationTime(autoSaveFiles);
        deleteOldestFiles(autoSaveDirectory, autoSaveFiles, fileTimeCreation);
    }

    /**
     * Lists files in directory where they are saved automatically
     *
     * @param autoSaveDirectory directory where files are saved
     * @return list of all files from directory
     */
    private List<File> listFiles(final File autoSaveDirectory) {
        final File[] listOfFiles = autoSaveDirectory.listFiles();
        final List<File> listOfFilesFinal = new ArrayList<File>();

        for (int index = 0; index < listOfFiles.length; index++) {
            final File autoSaveFile = listOfFiles[index];
            if (autoSaveFile.isFile()) {
                listOfFilesFinal.add(autoSaveFile);
            }
        }
        return listOfFilesFinal;
    }

    /**
     * Makes list of creation time of each file from directory where they are saved automatically, and then sort it
     *
     * @param autoSaveFiles files from directory where they were saved
     * @return sorted list of creation time for each file
     */
    private List<Long> filesCreationTime(final List<File> autoSaveFiles) {
        final List<Long> creationTime = new ArrayList<Long>();
        for (final File savedFile : autoSaveFiles) {
            final long lastModification = savedFile.lastModified();
            if (!creationTime.contains(lastModification)) {
                creationTime.add(lastModification);
            }
        }
        Collections.sort(creationTime);
        return creationTime;
    }

    /**
     * Deletes the oldest files from directory where they are saved automatically until the directory will be use
     * more space than a pre-determined value
     *
     * @param autoSaveDirectory directory where files are saved
     * @param autoSaveFiles files from directory where they were saved
     * @param fileTimeCreation sorted list of creation time for each file
     */
    private void deleteOldestFiles(final File autoSaveDirectory, final List<File> autoSaveFiles,
                                   final List<Long> fileTimeCreation) {
        final Long directorySize = FileUtils.sizeOfDirectory(autoSaveDirectory);
        while (Long.valueOf(maxSizeOfDirectory) < directorySize && !fileTimeCreation.isEmpty()) {
            for (final File saveFile : autoSaveFiles) {
                if (saveFile.lastModified() == fileTimeCreation.get(0)) {
                    saveFile.delete();
                }
            }
            fileTimeCreation.remove(0);
        }
    }
}
