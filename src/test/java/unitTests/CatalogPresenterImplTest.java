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
        /*
        when(searchModel.getExtract(new SearchResult("Fideos", "1234", "Holisxd"))).thenReturn("Holis");
        presenter.onEventLoadArticle();*/
        //when(searchModel.searchOnWiki("SearchThis")).thenReturn;
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
        //when(view.getSavesSelection()).thenReturn("Pizza");
        presenter.onEventSaveChanges();
        verify(view).getSavesSelection();
        verify(view).getDisplayedArticle();
        verify(localModel).saveArticleChanges(any(),any());
    }

    @Test
    public void onEventSaveArticle() {
        //when(view.getSearchTitle()).thenReturn("Pizza");
        //when(view.getSearchedContent()).thenReturn("Pizza body");

        presenter.onEventSaveArticle();
        verify(view).getSearchTitle();
        verify(view).getSearchedContent();
        verify(localModel).saveArticle(view.getSearchTitle(),view.getSearchedContent());
    }

    @Test
    public void onEventLoadArticle() {
        /*SearchResult searchResultDummy = new SearchResult("Fideos", "1234", "Holis");

        when(searcher.getExtract(any())).thenReturn("This");
        when(view.getSearchSelection()).thenReturn(searchResultDummy);
        when(searchModel.searchExtract(any())).thenReturn("This");
        presenter.onEventLoadArticle();
        assertEquals(searchModel.getExtract(),"This");//TODO no me retorna nada el primer parametro*/
        presenter.onEventLoadArticle();
        verify(searchModel).searchExtract(any());
        verify(view).getSearchSelection();
    }

    @Test
    public void onEventChooseOnlyIntro() {
        /*boolean actual = searcher.getArticleMode();
        when(view.getOnlyIntro()).thenReturn(true);
        presenter.onEventChooseOnlyIntro();
        boolean afterChange = searcher.getArticleMode();
        assertNotEquals(actual, afterChange);*/
        presenter.onEventChooseOnlyIntro();
        verify(searchModel).setSearchMode(anyBoolean());
        verify(view).getOnlyIntro();
    }
}