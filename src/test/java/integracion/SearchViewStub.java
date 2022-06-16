package integracion;

import model.SearchResult;
import presenter.CatalogSearchPresenter;
import view.SearchViewImpl;

import java.util.ArrayList;

public class SearchViewStub extends SearchViewImpl {

    public ArrayList<SearchResult> preliminaryResults;

    public SearchViewStub(CatalogSearchPresenter catalogPresenter) {
        super(catalogPresenter);
    }

    public void setSearchTitle(String title) {
        searchTitle.setText(title);
    }

    public void displaySearchOptions(ArrayList<SearchResult> preliminaryResults){
        this.preliminaryResults = preliminaryResults;
    }

    public void setSearchMode(boolean fullArticle){
        onlyIntroCheckBox.setSelected(fullArticle);
    }



}
