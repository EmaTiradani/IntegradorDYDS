package integracion;

import model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import presenter.*;
import view.LocalView;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationTest {

    CatalogSearchPresenter searchPresenter;
    CatalogLocalPresenter localPresenter;
    WikiSearch searcher;
    CatalogWikiSearchModel searchModel;
    CatalogLocalModel localModel;
    SearchViewStub searchViewStub;
    LocalViewStub localViewStub;

    @Before
    public void setUp() throws Exception{
        localModel = new CatalogLocalModelImpl();
        searchModel = new CatalogWikiSearchModelImpl();
        searcher = new WikiSearchStub();
        searchModel.setSearchEngine(searcher);
        searchPresenter = new CatalogSearchPresenterImpl(searchModel);
        localPresenter = new CatalogLocalPresenterImpl(localModel);
        //presenter = new CatalogPresenterImpl(localModel, searchModel);
        searchViewStub = new SearchViewStub(searchPresenter);
        //presenter.setView(view);
        localViewStub = new LocalViewStub(localPresenter);

        localPresenter.setView(localViewStub);
    }

    @Test(timeout = 500)
    public void testSearchAndShowPreliminaryResults() throws IOException {
        ArrayList<SearchResult> results = new ArrayList<>();
        SearchResult result1 = new SearchResult("First","1", "Snippet");
        SearchResult result2 = new SearchResult("Second","2", "Snippet");
        SearchResult result3 = new SearchResult("Third","3", "Snippet");
        results.add(result1);
        results.add(result2);
        results.add(result3);
        searchViewStub.setSearchTitle("Pizza");
        searchPresenter.onEventSearch();
        ArrayList<SearchResult> newResults = localViewStub.searchOptions;
        for(int i = 0; i<3; i++){
            assertTrue(newResults.get(i).pageID.equals(results.get(i).pageID));
            assertTrue(newResults.get(i).pageID.equals(results.get(i).pageID));
            assertTrue(newResults.get(i).pageID.equals(results.get(i).pageID));
        }

    }

    @Test(timeout = 500)
    public void testShowSavedArticle(){
        when(localViewStub.getSavesSelection()).thenReturn("Title");
        when(localModel.getSave("Title")).thenReturn("Saved body");

        localPresenter.onEventShowSaved();

        verify(localModel).getSave("Title");
        verify(localViewStub).setStoredContent("Saved body");

    }

    @Test(timeout = 500)
    public void testDeleteArticle(){
        when(localViewStub.getSavesSelection()).thenReturn("Delete this");

       localPresenter.onEventDeleteArticle();

       verify (localModel).deleteArticle("Delete this");
       verify(localViewStub).setStoredContent(any());//TODO no se invoca...
       assert (localViewStub.getDisplayedArticle().equals("")); //TODO ¿por que dice que es nulo el retorno, si se supone que el presenter lo deja en "" ??

        //TODO no es necesario verificar los carteles que lanza?
    }

    @Test(timeout = 500)
    public void testSaveArticleChanges(){
        when(localViewStub.getSavesSelection()).thenReturn("Save this");
        when(localViewStub.getDisplayedArticle()).thenReturn("Modified body");

        localPresenter.onEventSaveChanges();

        verify(localModel).saveArticleChanges("Save this", "Modified body");//TODO Esto llama al listener y el listener a los 2 metodos de abajo(que nunca son llamados)
        //verify(view).setStoredList(any());
        verify(localViewStub).displayMessage(any());

    }

    @Test(timeout = 500)
    public void testSaveArticle(){
        when(searchViewStub.getSearchTitle()).thenReturn("Save this");
        when(searchViewStub.getSearchedContent()).thenReturn("Body to save");

        searchPresenter.onEventSaveArticle();

        verify(localModel).saveArticle("Save this", "Body to save");
        //verify(view).setStoredContent(any());
        verify(searchViewStub).displayMessage(any());

    }

    @Test(timeout = 500)
    public void testChangeFullArticleMode(){
        when(searchViewStub.getOnlyIntro()).thenReturn(true);


        searchPresenter.onEventChooseOnlyIntro();



        verify(searchModel).setSearchMode(true);
        verify(searchViewStub).getOnlyIntro();
        verifyNoInteractions(searcher);

    }

    @Test
    public void testFailSearching(){

    }


}
