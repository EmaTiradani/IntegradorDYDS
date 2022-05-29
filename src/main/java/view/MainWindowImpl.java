package view;

import dyds.gourmetCatalog.fulllogic.DataBase;
import presenter.CatalogPresenter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private CatalogPresenter catalogPresenter;

    public MainWindowImpl(CatalogPresenter catalogPresenter){
        this.catalogPresenter = catalogPresenter;
        initListeners();


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
    public void getSearchBarText() {

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
            /*saveLocallyButton.addActionListener(actionEvent -> {
                if(text != ""){
                    // save to DB  <o/
                    DataBase.saveInfo(selectedResultTitle.replace("'", "`"), text);  //Dont forget the ' sql problem
                    comboBox1.setModel(new DefaultComboBoxModel<Object>(DataBase.getTitles().stream().sorted().toArray()));
                }
            });*/
        });
    }
}
