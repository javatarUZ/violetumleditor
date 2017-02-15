package com.horstmann.violet.framework.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created on 2017-02-14.
 */
@RunWith(PowerMockRunner.class)
public class AutoSaveRemoverTest {

    @Test
    public void shouldDeleteAutoSavedFiles() throws Exception {
        //given
        final String autoSaveDirectoryPath = System.getProperty("user.home") + File.separator + "VioletUML";
        final AutoSaveRemover autoSaveRemover = new AutoSaveRemover();
        final File autoSaveDirectory = new File(autoSaveDirectoryPath);
        Whitebox.setInternalState(autoSaveRemover, "maxSizeOfDirectory", "0");

        //when
        createTemporaryFiles(autoSaveDirectoryPath);
        autoSaveRemover.removeBackup();

        //then
        assertThat(FileUtils.sizeOfDirectory(autoSaveDirectory), is(Long.valueOf(0)));
    }

    private void createTemporaryFiles(final String autoSaveDirectoryPath) {
        int index = 0;
        final String exampleText = "Example Test Text";
        while (index < 5) {
            index++;
            final File tmpFile = new File(autoSaveDirectoryPath + File.separator + "file" + index);
            try {
                final PrintWriter writer = new PrintWriter(tmpFile, "UTF-8");
                writer.println(exampleText);
                writer.close();
            } catch (FileNotFoundException e) {
                System.err.println("File wasn't found " + e);
            } catch (UnsupportedEncodingException e) {
                System.err.println("Encoding is unsupported " + e);
            }
        }
    }
}
