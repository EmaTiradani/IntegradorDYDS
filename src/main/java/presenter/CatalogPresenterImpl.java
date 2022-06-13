package presenter;

import model.*;
import model.listeners.CatalogLocalModelListener;
import model.listeners.CatalogWikiSearchModelListener;
import view.MainWindow;
import java.util.ArrayList;

public class CatalogPresenterImpl implements CatalogPresenter{

    private CatalogLocalModel localModel;
    private CatalogWikiSearchModel searchModel;
    private MainWindow view;

    public CatalogPresenterImpl(CatalogLocalModel localModel, CatalogWikiSearchModel searchModel){
        this.localModel = localModel;
        this.searchModel = searchModel;
        initListeners();
    }

    @Override
    public void start() {
        this.view.setStoredList(localModel.getStoredTitles());
        view.showView();
    }

    public void setView(MainWindow view){
        this.view = view;
    }

    @Override
    public void onEventSearch() {
        searchModel.searchOnWiki(view.getSearchTitle());
    }

    @Override
    public void onEventShowSaved(){
        String body = localModel.getSave(view.getSavesSelection());
        view.setStoredContent(body);
    }

    @Override
    public void onEventDeleteArticle() {
        localModel.deleteArticle(view.getSavesSelection());
    }

    @Override
    public void onEventSaveChanges() {
        localModel.saveArticleChanges(view.getSavesSelection(),view.getDisplayedArticle());
    }

    @Override
    public void onEventSaveArticle() {
        localModel.saveArticle(view.getSearchTitle(), view.getSearchedContent());
    }

    @Override
    public void onEventLoadArticle() {
        searchModel.searchExtract(view.getSearchSelection());
    }

    @Override
    public void onEventChooseOnlyIntro() {
        searchModel.setSearchMode(view.getOnlyIntro());
    }

    private void initListeners(){
        /*model.addListener(new CatalogModelListener() {
            @Override
            public void didSearchOnWiki() {
                ArrayList<SearchResult> titles = searchModel.getPreliminaryResults();
                view.displaySearchOptions(titles);
            }

            @Override
            public void didSaveLocally() {
                String[] titles = model.getStoredTitles();
                view.setStoredList(titles);
                view.displayMessage("Saved succesfully!");
            }

            @Override
            public void didSearchExtract() {
                view.setSearchedContent(model.getExtract2());
            }

            @Override
            public void didThrowException() {
                view.displayMessage(model.getErrorMessage());
            }

            @Override
            public void didDeleteSave() {
                view.setStoredContent("");
                view.setStoredList(model.getStoredTitles());
                view.displayMessage("Deleted succesfully!");
            }
        });*/
        localModel.addListener(new CatalogLocalModelListener() {

            @Override
            public void didSaveLocally() {
                String[] titles = localModel.getStoredTitles();
                view.setStoredList(titles);
                view.displayMessage("Saved succesfully!");
            }

            @Override
            public void didThrowException() {
                view.displayMessage(localModel.getErrorMessage());
            }

            @Override
            public void didDeleteSave() {
                view.setStoredContent("");
                view.setStoredList(localModel.getStoredTitles());
                view.displayMessage("Deleted succesfully!");
            }
        });
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
