package integracion;

import model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import presenter.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;
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

        searchViewStub = new SearchViewStub(searchPresenter);
        localViewStub = new LocalViewStub(localPresenter);

        localPresenter.setView(localViewStub);
        searchPresenter.setView(searchViewStub);

        searchPresenter.start();
        localPresenter.start();
    }

    /*@Test(timeout = 500)
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

    }*/

    @Test(timeout = 500)
    public void testShowSavedArticle(){
        //TODO deberia guardar algo en el savesSelection y en la database tambien?
        try {
            DataBase.saveInfo("Pizza", "Pizzabody");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        localViewStub.setSavesSelection("Pizza");

        localPresenter.onEventShowSaved();
        String expectedBody = DataBase.getExtract("Pizza");
        assertEquals("Pizzabody"/*expectedBody*/, localViewStub.getDisplayedArticle());//TODO como? Con el string o con lo de la DB?
    }

    @Test(timeout = 5000)
    public void testDeleteArticle(){
        //TODO me tira un error cuando lo ejecuto a veces, el combobox esta vacio al parecer

        try {
            DataBase.saveInfo("This has to be deleted", "Body");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        localViewStub.setStoredList(localModel.getStoredTitles());
        localViewStub.setSavesSelection("This has to be deleted");

        ArrayList<String> titlesBeforeDeleting = DataBase.getTitles();

        localPresenter.onEventDeleteArticle();

        assertEquals(DataBase.getTitles().size(), titlesBeforeDeleting.size()-1);
        assertNotEquals(DataBase.getTitles(), titlesBeforeDeleting);

        //TODO no es necesario verificar los carteles que lanza? Yo lo deje en un print
    }

    @Test(timeout = 500)
    public void testSaveArticleChanges(){

        try {
            DataBase.saveInfo("Save changes to this article", "Not modified body");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        localViewStub.setStoredList(localModel.getStoredTitles());
        localViewStub.setSavesSelection("Save changes to this");
        localViewStub.setStoredContent();

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
