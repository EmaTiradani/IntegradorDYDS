package integracion;

import model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import presenter.CatalogPresenter;
import presenter.CatalogPresenterImpl;
import view.MainWindowImpl;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationTest {

    CatalogPresenter presenter;
    WikiSearch searcher;
    CatalogWikiSearchModel searchModel;
    CatalogLocalModel localModel;
    ViewStub view;

    @Before
    public void setUp() throws Exception{
        localModel = new CatalogLocalModelImpl();
        searchModel = new CatalogWikiSearchModelImpl();
        searcher = new WikiSearchStub();
        searchModel.setSearchEngine(searcher);
        presenter = new CatalogPresenterImpl(localModel, searchModel);
        view = new ViewStub(presenter);
        presenter.setView(view);
    }

    @Test(timeout = 10000)
    public void testSearchAndShowPreliminaryResults() throws IOException {
        ArrayList<SearchResult> results = new ArrayList<>();
        SearchResult result1 = new SearchResult("First","1", "Snippet");
        SearchResult result2 = new SearchResult("Second","2", "Snippet");
        SearchResult result3 = new SearchResult("Third","3", "Snippet");
        results.add(result1);
        results.add(result2);
        results.add(result3);
        view.setSearchTitle("Pizza");
        presenter.onEventSearch();
        ArrayList<SearchResult> newResults = view.searchOptions;
        for(int i = 0; i<3; i++){
            assertTrue(newResults.get(i).pageID.equals(results.get(i).pageID));
            assertTrue(newResults.get(i).pageID.equals(results.get(i).pageID));
            assertTrue(newResults.get(i).pageID.equals(results.get(i).pageID));
        }

    }

    @Test(timeout = 500)
    public void testShowSavedArticle(){
        when(view.getSavesSelection()).thenReturn("Title");
        when(localModel.getSave("Title")).thenReturn("Saved body");

        presenter.onEventShowSaved();

        verify(localModel).getSave("Title");
        verify(view).setStoredContent("Saved body");

    }



    @Test(timeout = 500)
    public void testDeleteArticle(){
        when(view.getSavesSelection()).thenReturn("Delete this");

       presenter.onEventDeleteArticle();

       verify (localModel).deleteArticle("Delete this");
       verify(view).setStoredContent(any());//TODO no se invoca...
       assert (view.getDisplayedArticle().equals("")); //TODO ¿por que dice que es nulo el retorno, si se supone que el presenter lo deja en "" ??

        //TODO no es necesario verificar los carteles que lanza?
    }

    @Test(timeout = 500)
    public void testSaveArticleChanges(){
        when(view.getSavesSelection()).thenReturn("Save this");
        when(view.getDisplayedArticle()).thenReturn("Modified body");

        presenter.onEventSaveChanges();

        verify(localModel).saveArticleChanges("Save this", "Modified body");//TODO Esto llama al listener y el listener a los 2 metodos de abajo(que nunca son llamados)
        verify(view).setStoredList(any());
        verify(view).displayMessage(any());

    }

    @Test(timeout = 500)
    public void testSaveArticle(){
        when(view.getSearchTitle()).thenReturn("Save this");
        when(view.getSearchedContent()).thenReturn("Body to save");

        presenter.onEventSaveArticle();

        verify(localModel).saveArticle("Save this", "Body to save");
        verify(view).setStoredContent(any());
        verify(view).displayMessage(any());

    }

    @Test(timeout = 500)
    public void loadSelectedArticleBody(){
        SearchResult result = new SearchResult("Pizza","1", "Snippet");
        when(view.getSearchSelection()).thenReturn(result);
        //when() //TODO mockeo la database para obtener un resultado? O primero le guardo uno?

    }

    @Test(timeout = 500)
    public void testChangeFullArticleMode(){
        when(view.getOnlyIntro()).thenReturn(true);


        presenter.onEventChooseOnlyIntro();



        verify(searchModel).setSearchMode(true);
        verify(view).getOnlyIntro();
        verifyNoInteractions(searcher); //TODO no se interactua con este mock, ??
        //verify(searcher).toggleFullArticle(true);//TODO nunca lo llamaron, why?

    }

}
