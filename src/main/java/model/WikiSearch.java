package model;

import java.io.IOException;
import java.util.ArrayList;

public interface WikiSearch {

    void toggleFullArticle(boolean fullArticle);

    boolean getArticleMode();

    ArrayList<SearchResult> search(String title) throws IOException;

    String getExtract(SearchResult searchResult);


}
