package integracion;

import model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import presenter.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class IntegrationTest {

    CatalogSearchPresenter searchPresenter;
    CatalogLocalPresenter localPresenter;
    WikipediaSearch searcher;
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
        searchModel.setLocalModel(localModel);

        searchPresenter = new CatalogSearchPresenterImpl(searchModel);
        localPresenter = new CatalogLocalPresenterImpl(localModel);

        searchViewStub = new SearchViewStub(searchPresenter);
        localViewStub = new LocalViewStub(localPresenter);

        ArrayList<String> titles = DataBase.getTitles();
        for(String title : titles){
            DataBase.deleteEntry(title);
        }

        localPresenter.setView(localViewStub);
        searchPresenter.setView(searchViewStub);

        searchPresenter.start();
        localPresenter.start();
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
        ArrayList<SearchResult> newResults = searchViewStub.preliminaryResults;
        for(int i = 0; i<3; i++){
            assertTrue(newResults.get(i).pageID.equals(results.get(i).pageID));
            assertTrue(newResults.get(i).pageID.equals(results.get(i).pageID));
            assertTrue(newResults.get(i).pageID.equals(results.get(i).pageID));
        }
    }

    @Test(timeout = 500)
    public void testShowSavedArticle(){
        try {
            DataBase.saveInfo("Pizza", "Pizzabody");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        localViewStub.setStoredList(new String[]{"Pizza"});
        localViewStub.setSavesSelection("Pizza");
        localPresenter.onEventShowSaved();

        assertEquals("Pizzabody", localViewStub.getDisplayedArticle());
    }

    @Test(timeout = 5000)
    public void testDeleteArticle(){

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
        localViewStub.setSavesSelection("Save changes to this article");
        localViewStub.setStoredContent("Changed body");

        localPresenter.onEventSaveChanges();

        assertEquals("Changed body", DataBase.getExtract("Save changes to this article"));
    }

    @Test(timeout = 500)
    public void testSaveArticle(){

        searchViewStub.setSearchTitle("Save this");
        searchViewStub.setSearchedContent("Body to save");

        searchPresenter.onEventSaveArticle();

        assert(DataBase.getExtract("Save this").contains("Body to save"));

    }

    @Test(timeout = 500)
    public void testChangeFullArticleMode(){

        searchViewStub.setSearchMode(false);
        boolean initialValue = searchViewStub.getOnlyIntro();
        searchViewStub.setSearchTitle("Pizza");
        searchPresenter.onEventChooseOnlyIntro();
        searchPresenter.onEventLoadArticle();
        String notFullArticleBody = searchViewStub.getSearchedContent();

        searchViewStub.setSearchMode(true);
        boolean afterChangeValue = searchViewStub.getOnlyIntro();
        assertNotEquals(initialValue, afterChangeValue);
        searchViewStub.setSearchTitle("Test full article");
        searchPresenter.onEventChooseOnlyIntro();
        searchPresenter.onEventLoadArticle();
        String fullArticleBody = searchViewStub.getSearchedContent();

        assertNotEquals(notFullArticleBody, fullArticleBody);
        assert(notFullArticleBody.contains("Only intro"));
        assert(fullArticleBody.contains("Full article"));
    }

    @Test
    public void testFailSearching(){

    }


}
