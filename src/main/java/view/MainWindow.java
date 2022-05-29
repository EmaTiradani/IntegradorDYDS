package view;

public interface MainWindow {
    void showView();

    void getSearchBarText();//Va al textField1 cambiar void

    void setSearchedContent(String text);//Va al textPane1

    String getSearchedContent();

    void setStoredContent(String text);//va al textPane2

    void setStoredList(String[] storedArticles);//va al comboBox

    String getSavesSelection();//retorna lo seleccionado por el combobox pasado a string

    String getDisplayedArticle();

    String getSearchTitle();//Titulo de textField1

}
