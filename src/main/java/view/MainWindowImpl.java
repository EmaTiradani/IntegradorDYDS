package view;

import javax.swing.*;

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



    @Override
    public void showView() {
        JFrame frame = new JFrame("Gourmet Catalog");
        frame.setContentPane(new view.MainWindowImpl().contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void getSearchBarText() {

    }

    @Override
    public void setSearchedContent() {

    }

    @Override
    public void setStoredContent() {

    }

    @Override
    public void setStoredList() {

    }
}
