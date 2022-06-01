package view;

import com.google.gson.JsonElement;
import dyds.gourmetCatalog.fulllogic.DataBase;
import model.SearchResult;
import presenter.CatalogPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainWindowImpl implements MainWindow{

    private JTextField textField1;
    private JPanel contentPane;
    private JTextPane textPane1;
    private JButton saveLocallyButton;
    private JTabbedPane tabbedPane1;
    private JPanel searchPanel;
    private JPanel storagePanel;
    private JComboBox<Object> comboBox1;
    private JTextPane textPane2;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton saveChangesButton;
    private JCheckBox onlyIntroCheckBox;
    private  JPopupMenu searchOptionsMenu;

    private SearchResult selected;//todo sacar esto cuando arregle el funcionamiento del JPopupmenu

    private CatalogPresenter catalogPresenter;

    public MainWindowImpl(CatalogPresenter catalogPresenter){
        this.catalogPresenter = catalogPresenter;
        initListeners();
        textPane1.setContentType("text/html");
        textPane2.setContentType("text/html");

    }

    @Override
    public void showView() {
        JFrame frame = new JFrame("Gourmet Catalog");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void setSearchedContent(String text) {
        textPane1.setText(text);
        textPane1.setCaretPosition(0);
    }

    @Override
    public String getSearchedContent() {
        return textPane1.getText();
    }

    @Override
    public void setStoredContent(String text) {
        textPane2.setText(text);
    }

    public String getSavesSelection(){
        //comboBox1.addActionListener(actionEvent -> textPane2.setText(textToHtml(DataBase.getExtract(comboBox1.getSelectedItem().toString()))));
        return comboBox1.getSelectedItem().toString();
    }

    @Override
    public String getDisplayedArticle() {
        return textPane2.getText();
    }

    @Override
    public String getSearchTitle() {
        return textField1.getText();
    }

    @Override
    public void displaySearchOptions(ArrayList<SearchResult> preliminarResults) {
        searchOptionsMenu = new JPopupMenu("Search Results");
        for (SearchResult res : preliminarResults) {
            searchOptionsMenu.add(res);
            //TODO mmmmmmmmmmm dijo la muda. A ver, esta medio raro esto PREGUNTAR
            res.addActionListener(actionEvent -> {
                selected = res;
                catalogPresenter.onEventLoadArticle();
            });
        }
        searchOptionsMenu.show(textField1, textField1.getX(), textField1.getY());
    }

    @Override
    public SearchResult getSearchSelection() {
        return selected;//todo feo
    }

    @Override
    public boolean getOnlyIntro() {
        return onlyIntroCheckBox.isSelected();
    }

    @Override
    public void errorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    @Override
    public void setStoredList(String[] storedArticles) {
        comboBox1.setModel(new DefaultComboBoxModel<Object>(storedArticles));
    }

    private void initListeners() {
        comboBox1.addActionListener(actionEvent -> catalogPresenter
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
