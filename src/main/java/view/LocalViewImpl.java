package view;

import presenter.CatalogLocalPresenter;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LocalViewImpl implements LocalView {
    protected JPanel storagePanel;
    protected JComboBox<Object> storedArticlesTitles;
    protected JTextPane storedArticleBody;
    protected JButton saveChangesButton;
    protected JButton deleteButton;

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
    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
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
