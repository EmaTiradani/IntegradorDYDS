package view;

import model.SearchResult;

import javax.swing.*;
import java.util.ArrayList;

public interface SearchView {

    void setSearchedContent(String text);//Va al textPane1

    String getSearchedContent();

    String getSearchTitle();//Titulo de textField1

    void displaySearchOptions(ArrayList<SearchResult> preliminarResults);//Le mandas titulos para que cree el JPopupMenu

    SearchResult getSearchSelection();

    boolean getOnlyIntro();//Retorna true si tiene que traer solo la intro

    void displayMessage(String errorMessage);

    JPanel getPanel();
}
