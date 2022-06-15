package view;

import model.SearchResult;
import presenter.CatalogSearchPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchViewImpl implements SearchView{
    protected JPanel SearchPanel;
    protected JTextField searchTitle;
    protected JTextPane searchedArticleBody;
    protected JButton searchButton;
    protected JCheckBox onlyIntroCheckBox;
    protected JButton saveLocallyButton;
    protected JPopupMenu searchOptionsMenu;

    private SearchResult selected;
    private CatalogSearchPresenter catalogPresenter;

    public SearchViewImpl(CatalogSearchPresenter catalogPresenter){
        this.catalogPresenter = catalogPresenter;
        initListeners();
        searchedArticleBody.setContentType("text/html");
    }

    @Override
    public void setSearchedContent(String text) {
        searchedArticleBody.setText(text);
        searchedArticleBody.setCaretPosition(0);
    }

    @Override
    public String getSearchedContent() {
        return searchedArticleBody.getText();
    }

    @Override
    public String getSearchTitle() {
        return searchTitle.getText();
    }

    @Override
    public void displaySearchOptions(ArrayList<SearchResult> preliminarResults) {
        searchOptionsMenu = new JPopupMenu("Search Results");
        for (SearchResult res : preliminarResults) {
            searchOptionsMenu.add(res);
            res.addActionListener(actionEvent -> {
                selected = res;
                catalogPresenter.onEventLoadArticle();
            });
        }
        searchOptionsMenu.show(searchTitle, searchTitle.getX(), searchTitle.getY());
    }

    @Override
    public SearchResult getSearchSelection() {
        return selected;
    }

    @Override
    public boolean getOnlyIntro() {
        return onlyIntroCheckBox.isSelected();
    }

    @Override
    public void displayMessage(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    @Override
    public JPanel getPanel() {
        return SearchPanel;
    }

    private void initListeners() {

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.onEventSearch();
            }
        });
        saveLocallyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.onEventSaveArticle();
            }
        });
        onlyIntroCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.onEventChooseOnlyIntro();
            }
        });
    }
}
