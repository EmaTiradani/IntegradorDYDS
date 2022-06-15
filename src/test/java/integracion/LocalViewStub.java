package integracion;

import model.SearchResult;
import presenter.CatalogLocalPresenter;
import view.LocalViewImpl;

import java.util.ArrayList;

public class LocalViewStub extends LocalViewImpl {

    public ArrayList<SearchResult> searchOptions;

    public LocalViewStub(CatalogLocalPresenter localPresenter) {
        super(localPresenter);
    }

    public void displaySearchOptions(ArrayList<SearchResult> preliminarResults) {
        searchOptions = preliminarResults;
    }


}
