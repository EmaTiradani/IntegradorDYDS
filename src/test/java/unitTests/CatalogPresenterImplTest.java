package unitTests;

import model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import presenter.CatalogPresenter;
import presenter.CatalogPresenterImpl;
import view.MainWindowImpl;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CatalogPresenterImplTest {

    CatalogPresenter presenter;

    @Mock
    CatalogWikiSearchModelImpl searchModel;
    @Mock
    CatalogLocalModelImpl localModel;

    @Mock
    MainWindowImpl view;

    @Mock
    WikiSearch searcher;

    @Before
    public void setUp() throws Exception{
        presenter = new CatalogPresenterImpl(localModel, searchModel);
        searchModel.setSearchEngine(searcher);
        presenter.setView(view);
    }

    @Test
    public void onEventSearchTest() {
        presenter.onEventSearch();
        verify(searchModel).searchOnWiki(any());
        verify(view).getSearchTitle();
    }

    @Test
    public void onEventShowSavedTest() {
        presenter.onEventShowSaved();
        verify(view).setStoredContent(any());
    }

    @Test
    public void onEventDeleteArticleTest() {
        presenter.onEventDeleteArticle();
        verify(view).getSavesSelection();
        verify(localModel).deleteArticle(any());
    }

    @Test
    public void onEventSaveChanges() {
        presenter.onEventSaveChanges();
        verify(view).getSavesSelection();
        verify(view).getDisplayedArticle();
        verify(localModel).saveArticleChanges(any(),any());
    }

    @Test
    public void onEventSaveArticle() {

        presenter.onEventSaveArticle();
        verify(view).getSearchTitle();
        verify(view).getSearchedContent();
        verify(localModel).saveArticle(view.getSearchTitle(),view.getSearchedContent());
    }

    @Test
    public void onEventLoadArticle() {
        presenter.onEventLoadArticle();
        verify(searchModel).searchExtract(any());
        verify(view).getSearchSelection();
    }

    @Test
    public void onEventChooseOnlyIntro() {
        when(view.getOnlyIntro()).thenReturn(true);
        presenter.onEventChooseOnlyIntro();
        verify(searchModel).setSearchMode(true);

        when(view.getOnlyIntro()).thenReturn(false);
        presenter.onEventChooseOnlyIntro();
        verify(searchModel).setSearchMode(false);

        verify(view, times(2)).getOnlyIntro();
    }
}