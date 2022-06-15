package view;

import javax.swing.*;
import java.awt.*;

public class MainFrameImpl implements MainFrame {
    private JTabbedPane tabs;
    private JPanel contentPane;

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
    public void setLocalPanel(JPanel localPanel) {
        tabs.add(localPanel, "Saved articles", 0);

    }

    @Override
    public void setSearchPanel(JPanel searchPanel) {
        tabs.add(searchPanel, "Search on wikipedia!", 1);
    }

}
