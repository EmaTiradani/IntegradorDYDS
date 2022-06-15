package integracion;

import model.SearchResult;
import presenter.CatalogSearchPresenter;
import view.SearchViewImpl;

import java.util.ArrayList;

public class SearchViewStub extends SearchViewImpl {
//Combobox con set selected ya se dispara un evento



    public SearchViewStub(CatalogSearchPresenter catalogPresenter) {
        super(catalogPresenter);
    }

    /*public SearchViewStub(CatalogSearchPresenter catalogSearchPresenter) {
        super(catalogSearchPresenter);
    }*/

    //public displaySearchOptions()
    //private String searchTitle;
    public void setSearchTitle(String title) {
        searchTitle.setText(title);
    }




}
