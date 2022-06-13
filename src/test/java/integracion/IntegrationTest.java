package integracion;

import model.CatalogLocalModelImpl;
import model.CatalogWikiSearchModelImpl;
import model.SearchResult;
import model.WikiSearch;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationTest {

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

    @Test(timeout = 500)
    public void testSearchAndShowPreliminaryResults() throws IOException {
        when(view.getSearchTitle()).thenReturn("Pizza");
        ArrayList<SearchResult> results = new ArrayList<>();
        SearchResult result1 = new SearchResult("First","1", "Snippet");
        SearchResult result2 = new SearchResult("Second","2", "Snippet");
        SearchResult result3 = new SearchResult("Third","3", "Snippet");
        results.add(result1);
        results.add(result2);
        results.add(result3);
        when(searcher.search("Pizza")).thenReturn(results);

        presenter.onEventSearch();

        verify(searchModel).searchOnWiki(any()); //Esto funciona
        //Notifico al listener notifySearchListener() -> didSearchOnWiki() y nunca le avisa al presentador.
        verify(view).displaySearchOptions(results); //TODO Por que no lo llama nunca? no funciona ni con el "any()"
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
