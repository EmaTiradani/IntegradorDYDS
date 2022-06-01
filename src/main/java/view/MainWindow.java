package view;

import model.SearchResult;

import java.util.ArrayList;

public interface MainWindow {
    void showView();

    String getSearchBarText();//Va al textField1 cambiar void

    void setSearchedContent(String text);//Va al textPane1

    String getSearchedContent();

    void setStoredContent(String text);//va al textPane2

    void setStoredList(String[] storedArticles);//va al comboBox

    String getSavesSelection();//retorna lo seleccionado por el combobox pasado a string

    String getDisplayedArticle();

    String getSearchTitle();//Titulo de textField1

    void displaySearchOptions(ArrayList<SearchResult> preliminarResults);//Le mandas titulos para que cree el JPopupMenu

    SearchResult getSearchSelection();

    boolean getOnlyIntro();//Retorna true si tiene que traer solo la intro
}
