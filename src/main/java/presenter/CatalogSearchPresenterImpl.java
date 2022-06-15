package presenter;

import model.CatalogWikiSearchModel;
import model.SearchResult;
import model.listeners.CatalogWikiSearchModelListener;
import view.SearchView;

import java.util.ArrayList;

public class CatalogSearchPresenterImpl implements CatalogSearchPresenter{

    private CatalogWikiSearchModel searchModel;
    private SearchView view;

    public CatalogSearchPresenterImpl(CatalogWikiSearchModel searchModel){
        this.searchModel = searchModel;
        initListeners();
    }

    public void start() {
        //this.view.setStoredList(localModel.getStoredTitles());
        //view.showView();
    }

    public void setView(SearchView view){
        this.view = view;
    }

    @Override
    public void onEventSearch() {
        searchModel.searchOnWiki(view.getSearchTitle());
    }

    @Override
    public void onEventChooseOnlyIntro() {
        searchModel.setSearchMode(view.getOnlyIntro());
    }

    @Override
    public void onEventSaveArticle() {
        searchModel.saveArticle(view.getSearchTitle(), view.getSearchedContent());
    }

    @Override
    public void onEventLoadArticle() {
        searchModel.searchExtract(view.getSearchSelection());
    }

    private void initListeners(){
        searchModel.addListener(new CatalogWikiSearchModelListener() {
            @Override
            public void didSearchOnWiki() {
                ArrayList<SearchResult> titles = searchModel.getPreliminaryResults();
                view.displaySearchOptions(titles);
            }

            @Override
            public void didSearchExtract() {
                view.setSearchedContent(searchModel.getExtract());
            }

            @Override
            public void didThrowException() {
                view.displayMessage(searchModel.getErrorMessage());
            }

        });
    }
}
