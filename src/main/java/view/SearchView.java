package view;

import model.SearchResult;

import javax.swing.*;
import java.util.ArrayList;

public interface SearchView {

    void setSearchedContent(String text);

    String getSearchedContent();

    String getSearchTitle();

    void displaySearchOptions(ArrayList<SearchResult> preliminarResults);

    SearchResult getSearchSelection();

    boolean getOnlyIntro();

    void displayMessage(String message);

    JPanel getPanel();
}
