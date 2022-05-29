package presenter;

import dyds.gourmetCatalog.fulllogic.DataBase;
import model.CatalogModel;
import model.CatalogModelImpl;
import model.CatalogModelListener;
import view.MainWindow;
import view.MainWindowImpl;

import javax.swing.*;
import java.util.Arrays;

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
        initListeners();
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
        model.saveArticle(view.getSearchTitle(), view.getSearchedContent());
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
                //Al momento de guardar cosas locales solo le agrego el nuevo titulo al combobox
                Object[] titles = DataBase.getTitles().stream().sorted().toArray();//TODO -> mal, le tengo que pedir cosas al modelo, no a la database
                String[] titlesAsStringArray = Arrays.copyOf(titles, titles.length, String[].class);
                view.setStoredList(titlesAsStringArray);
            }

            @Override
            public void didSelectSavedSearch() {
                //view.set
            }

            @Override
            public void didDeleteSave() {

            }

        });

    }
}
