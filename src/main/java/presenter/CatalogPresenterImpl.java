package presenter;

import model.SearchResult;
import model.CatalogModel;
import model.CatalogModelImpl;
import model.CatalogModelListener;
import view.MainWindow;
import view.MainWindowImpl;

public class CatalogPresenterImpl implements CatalogPresenter{

    private CatalogModel model;
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
        //DataBase.saveInfo(comboBox1.getSelectedItem().toString().replace("'", "`"), textPane2.getText()); todo acordarse de lo de las comillas mandarlo a la DB
        model.saveArticleChanges(view.getSavesSelection(),view.getDisplayedArticle());
    }

    @Override
    public void onEventSaveArticle() {
        model.saveArticle(view.getSearchTitle(), view.getSearchedContent());
    }


    private void initListeners(){
        model.addListener(new CatalogModelListener() {
            @Override
            public void didSearchOnWiki() {
                //view.setSearchedContent(model.getSearchResult());
                SearchResult[] titles = model.getPreliminaryResults();
                view.displaySearchOptions(titles);
            }

            @Override
            public void didSelectSearchOption() {
                view.setStoredContent(model.getSave(view.getSavesSelection()));
            }

            @Override
            public void didSaveLocally() {
                //Al momento de guardar cosas locales solo le agrego el nuevo titulo al combobox
                String[] titles = model.getStoredTitles();
                view.setStoredList(titles);
            }

            @Override
            public void didSelectSavedSearch() {
                //view.set
            }

            @Override
            public void didDeleteSave() {
                view.setStoredContent("");
                view.setStoredList(model.getStoredTitles());
            }

        });

    }
}
