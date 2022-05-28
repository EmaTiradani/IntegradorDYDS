package presenter;

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

    }

    @Override
    public void start() {

        String[] test= {"Hola","Pepe","Salame","See"};
        view.setStoredList(test);
        view.showView();
    }

    @Override
    public void onEventSearch() {

    }

    @Override
    public void onEventShowSaved(){

    }

    @Override
    public void onEventDeleteArticle() {

    }

    @Override
    public void onEventSaveChanges() {
        //DataBase.saveInfo(comboBox1.getSelectedItem().toString().replace("'", "`"), textPane2.getText()); todo acordarse de lo de las comillas mandarlo a la DB
        model.saveArticleChanges(view.getSavesSelection(),view.getDisplayedArticle());

    }

    @Override
    public void onEventSaveArticle() {
        model.saveArticle(view.getSearchedContent());
    }


    private void initListeners(){
        model.addListener(new CatalogModelListener() {
            @Override
            public void didSearchOnWiki() {
                view.setSearchedContent(model.getSearchResult());
            }

            @Override
            public void didSelectSearchOption() {
                view.setStoredContent(model.getSave(view.getSavesSelection()));
            }

            @Override
            public void didSaveLocally() {

            }

            @Override
            public void didSelectSavedSearch() {

            }

            @Override
            public void didDeleteSave() {

            }

        });

    }
}
