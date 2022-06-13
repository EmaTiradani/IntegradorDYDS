package integracion;

import model.SearchResult;
import presenter.CatalogPresenter;
import view.MainWindowImpl;

import java.util.ArrayList;

public class ViewStub extends MainWindowImpl {
//Combobox con set selected ya se dispara un evento

    public ArrayList<SearchResult> searchOptions;

    public ViewStub(CatalogPresenter catalogPresenter) {
        super(catalogPresenter);
    }

    //public displaySearchOptions()
    //private String searchTitle;
    public void setSearchTitle(String title) {
        searchTitle.setText(title);
    }

    public void displaySearchOptions(ArrayList<SearchResult> preliminarResults) {
        searchOptions = preliminarResults;
    }
}