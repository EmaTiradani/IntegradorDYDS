package view;

import presenter.CatalogLocalPresenter;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LocalViewImpl implements LocalView {
    public JPanel storagePanel;
    private JComboBox<Object> storedArticlesTitles;
    private JTextPane storedArticleBody;
    private JButton saveChangesButton;
    private JButton deleteButton;

    private CatalogLocalPresenter localPresenter;

    public LocalViewImpl(CatalogLocalPresenter localPresenter){
        this.localPresenter = localPresenter;
    }
    @Override
    public void start() {
        initListeners();
        storedArticleBody.setContentType("text/html");
    }

    @Override
    public JPanel getPanel(){
        return this.storagePanel;
    }

    @Override
    public String getSavesSelection(){
        return storedArticlesTitles.getSelectedItem().toString();
    }

    @Override
    public void setStoredContent(String text) {
        storedArticleBody.setText(text);
    }

    @Override
    public String getDisplayedArticle() {
        return storedArticleBody.getText();
    }

    @Override
    public void setStoredList(String[] storedArticles) {
        storedArticlesTitles.setModel(new DefaultComboBoxModel<Object>(storedArticles));
    }

    @Override
    public void displayMessage(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage);
    }

    public void initListeners(){
        storedArticlesTitles.addActionListener(actionEvent -> localPresenter
                .onEventShowSaved());
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                localPresenter.onEventDeleteArticle();
            }
        });
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                localPresenter.onEventSaveChanges();
            }
        });
    }

}
