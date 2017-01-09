package com.horstmann.violet.application.gui;

import static org.mockito.Mockito.mock;

import com.horstmann.violet.framework.dialog.DialogFactory;
import com.horstmann.violet.framework.dialog.DialogFactoryMode;
import com.horstmann.violet.framework.file.chooser.IFileChooserService;
import com.horstmann.violet.framework.file.chooser.JFileChooserService;
import com.horstmann.violet.framework.file.persistence.IFilePersistenceService;
import com.horstmann.violet.framework.file.persistence.StandardJavaFilePersistenceService;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanFactory;
import com.horstmann.violet.framework.injection.bean.ManiocFramework.BeanInjector;
import com.horstmann.violet.framework.theme.ClassicMetalTheme;
import com.horstmann.violet.framework.theme.ITheme;
import com.horstmann.violet.framework.theme.ThemeManager;
import com.horstmann.violet.framework.userpreferences.DefaultUserPreferencesDao;
import com.horstmann.violet.framework.userpreferences.IUserPreferencesDao;
import com.horstmann.violet.framework.userpreferences.UserPreferencesService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class MainFrameTest {

    private MainFrame mainFrame;

    public MainFrameTest() {
        BeanInjector.getInjector().inject(this);
        final DialogFactory dialogFactory = new DialogFactory(DialogFactoryMode.INTERNAL);
        BeanFactory.getFactory().register(DialogFactory.class, dialogFactory);

        final IUserPreferencesDao userPreferencesDao = new DefaultUserPreferencesDao();
        BeanFactory.getFactory().register(IUserPreferencesDao.class, userPreferencesDao);

        final UserPreferencesService preferencesService = new UserPreferencesService();
        BeanFactory.getFactory().register(UserPreferencesService.class, preferencesService);

        final ThemeManager themeManager = new ThemeManager();
        final ITheme theme = new ClassicMetalTheme();
        final List<ITheme> themes = new ArrayList<ITheme>();
        themes.add(theme);
        themeManager.setInstalledThemes(themes);
        BeanFactory.getFactory().register(ThemeManager.class, themeManager);
        themeManager.applyPreferedTheme();

        final IFileChooserService fileChooserService = new JFileChooserService();
        BeanFactory.getFactory().register(IFileChooserService.class, fileChooserService);

        final IFilePersistenceService filePersistenceService = new StandardJavaFilePersistenceService();
        BeanFactory.getFactory().register(IFilePersistenceService.class, filePersistenceService);

        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setupMock() {
        mainFrame = mock(MainFrame.class);
    }

}
