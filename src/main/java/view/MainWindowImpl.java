package view;

import model.SearchResult;
import presenter.CatalogPresenter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindowImpl implements MainWindow{

    protected JTextField searchTitle;
    protected JPanel contentPane;
    protected JTextPane searchedArticleBody;
    protected JButton saveLocallyButton;
    protected JTabbedPane tabbedPane1;
    protected JPanel searchPanel;
    protected JPanel storagePanel;
    protected JComboBox<Object> storedArticlesTitles;
    protected JTextPane storedArticleBody;
    protected JButton deleteButton;
    protected JButton searchButton;
    protected JButton saveChangesButton;
    protected JCheckBox onlyIntroCheckBox;
    protected JPopupMenu searchOptionsMenu;

    private SearchResult selected;
    private CatalogPresenter catalogPresenter;

    public MainWindowImpl(CatalogPresenter catalogPresenter){
        this.catalogPresenter = catalogPresenter;
        initListeners();
        searchedArticleBody.setContentType("text/html");
        storedArticleBody.setContentType("text/html");
    }

    @Override
    public void showView() {
        JFrame frame = new JFrame("Gourmet Catalog");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/pizza (1).png")));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
    public void setStoredContent(String text) {
        storedArticleBody.setText(text);
    }

    public String getSavesSelection(){
        return storedArticlesTitles.getSelectedItem().toString();
    }

    @Override
    public String getDisplayedArticle() {
        return storedArticleBody.getText();
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
    public void setStoredList(String[] storedArticles) {
        storedArticlesTitles.setModel(new DefaultComboBoxModel<Object>(storedArticles));
    }

    private void initListeners() {
        storedArticlesTitles.addActionListener(actionEvent -> catalogPresenter
                .onEventShowSaved());
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.onEventSearch();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.onEventDeleteArticle();
            }
        });
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.onEventSaveChanges();
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
