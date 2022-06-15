package view;

import javax.swing.*;

public interface LocalView {

    void start();

    JPanel getPanel();

    String getSavesSelection();

    void setStoredContent(String text);

    String getDisplayedArticle();

    void setStoredList(String[] storedArticles);//va al comboBox

    void displayMessage(String errorMessage);

}
