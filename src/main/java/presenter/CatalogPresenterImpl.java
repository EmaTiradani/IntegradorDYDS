package presenter;

import model.*;
import view.MainWindow;
import view.MainWindowImpl;
import java.util.ArrayList;

public class CatalogPresenterImpl implements CatalogPresenter{

    private CatalogModel model;
    private CatalogLocalModel localModel;
    private CatalogWikiSearchModel searchModel;
    private MainWindow view;

    public CatalogPresenterImpl(){
        this.view = new MainWindowImpl(this);
        this.model = new CatalogModelImpl();
        this.view.setStoredList(model.getStoredTitles());
    }

    @Override
    public void start() {
        initListeners();
        view.showView();
    }

    @Override
    public void onEventSearch() {
        model.searchOnWiki(view.getSearchTitle());
    }

    @Override
    public void onEventShowSaved(){
        String body = model.getSave(view.getSavesSelection());
        view.setStoredContent(body);
    }

    @Override
    public void onEventDeleteArticle() {
        model.deleteArticle(view.getSavesSelection());
    }

    @Override
    public void onEventSaveChanges() {
        model.saveArticleChanges(view.getSavesSelection(),view.getDisplayedArticle());
    }

    @Override
    public void onEventSaveArticle() {
        model.saveArticle(view.getSearchTitle(), view.getSearchedContent());
    }

    @Override
    public void onEventLoadArticle() {
        model.getExtract(view.getSearchSelection());
    }

    @Override
    public void onEventChooseOnlyIntro() {
        model.setSearchMode(view.getOnlyIntro());
    }

    private void initListeners(){
        model.addListener(new CatalogModelListener() {
            @Override
            public void didSearchOnWiki() {
                ArrayList<SearchResult> titles = model.getPreliminaryResults();
                view.displaySearchOptions(titles);
            }

            @Override
            public void didSaveLocally() {
                String[] titles = model.getStoredTitles();
                view.setStoredList(titles);
                view.errorMessage("Saved succesfully!");
            }

            @Override
            public void didSearchExtract() {
                view.setSearchedContent(model.getExtract2());
            }

            @Override
            public void didThrowException() {
                view.errorMessage(model.getErrorMessage());
            }

            @Override
            public void didDeleteSave() {
                view.setStoredContent("");
                view.setStoredList(model.getStoredTitles());
                view.errorMessage("Deleted succesfully!");
            }
        });
    }
}
