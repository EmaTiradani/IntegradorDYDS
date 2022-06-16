package unitTests;

import model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import presenter.CatalogLocalPresenter;
import presenter.CatalogLocalPresenterImpl;
import presenter.CatalogSearchPresenter;
import presenter.CatalogSearchPresenterImpl;
import view.LocalViewImpl;
import view.SearchViewImpl;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CatalogPresenterImplTest {

    CatalogLocalPresenter localPresenter;
    CatalogSearchPresenter searchPresenter;

    @Mock
    CatalogWikiSearchModelImpl searchModel;
    @Mock
    CatalogLocalModelImpl localModel;

    @Mock
    SearchViewImpl searchView;
    @Mock
    LocalViewImpl localView;

    @Mock
    WikipediaSearch searcher;

    @Before
    public void setUp() throws Exception{
        localPresenter = new CatalogLocalPresenterImpl(localModel);
        searchPresenter = new CatalogSearchPresenterImpl(searchModel);
        searchModel.setSearchEngine(searcher);
        localPresenter.setView(localView);
        searchPresenter.setView(searchView);
    }

    @Test
    public void onEventSearchTest() {
        searchPresenter.onEventSearch();
        verify(searchModel).searchOnWiki(any());
        verify(searchView).getSearchTitle();
    }

    @Test
    public void onEventShowSavedTest() {
        localPresenter.onEventShowSaved();
        verify(localView).setStoredContent(any());
    }

    @Test
    public void onEventDeleteArticleTest() {
        localPresenter.onEventDeleteArticle();
        verify(localView).getSavesSelection();
        verify(localModel).deleteArticle(any());
    }

    @Test
    public void onEventSaveChanges() {
        localPresenter.onEventSaveChanges();
        verify(localView).getSavesSelection();
        verify(localView).getDisplayedArticle();
        verify(localModel).saveArticleChanges(any(),any());
    }

    @Test
    public void onEventSaveArticle() {

        searchPresenter.onEventSaveArticle();
        verify(searchView).getSearchTitle();
        verify(searchView).getSearchedContent();
        verify(searchModel).saveArticle(searchView.getSearchTitle(),searchView.getSearchedContent());
    }

    @Test
    public void onEventLoadArticle() {
        searchPresenter.onEventLoadArticle();
        verify(searchModel).searchExtract(any());
        verify(searchView).getSearchSelection();
    }

    @Test
    public void onEventChooseOnlyIntro() {
        when(searchView.getOnlyIntro()).thenReturn(true);
        searchPresenter.onEventChooseOnlyIntro();
        verify(searchModel).setSearchMode(true);

        when(searchView.getOnlyIntro()).thenReturn(false);
        searchPresenter.onEventChooseOnlyIntro();
        verify(searchModel).setSearchMode(false);

        verify(searchView, times(2)).getOnlyIntro();
    }
}