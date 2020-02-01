package helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MessageBox extends JDialog {
    private JTextArea textArea;
    private JPanel mainPanel;
    private JPanel southPanel;
    private JPanel buttonsPanel;
    private JButton okButton;


    public MessageBox(String message) {
        setUpDialog();
        setUpMainPanel();
        setUpTextArea(message);
        setUpSouthPanel();
        setUpButtonsPanel();
        setUpOkButton();

        add(mainPanel);

        addComponentToMainPanel(textArea, BorderLayout.CENTER);
        addComponentToMainPanel(southPanel, BorderLayout.SOUTH);

        addComponentToSouthPanel(buttonsPanel, BorderLayout.EAST);

        addComponentsToButtonsPanel(okButton);
    }

    private void setUpDialog() {
        setBackground(Color.darkGray);
        setForeground(Color.cyan);
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
    }

    private void setUpMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.setForeground(Color.cyan);
        mainPanel.setLayout(new BorderLayout());
    }

    private void setUpTextArea(String message) {
        textArea = new JTextArea(message);
        textArea.setBackground(Color.darkGray);
        textArea.setForeground(Color.cyan);
        textArea.setFont(new Font("Sans Serif", Font.BOLD, 18));
    }

    private void setUpSouthPanel() {
        southPanel = new JPanel();
        southPanel.setBackground(Color.darkGray);
        southPanel.setForeground(Color.cyan);
        southPanel.setLayout(new BorderLayout());
    }

    private void setUpButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setBackground(Color.darkGray);
        buttonsPanel.setForeground(Color.cyan);
    }

    private void setUpOkButton() {
        okButton = new JButton("Ok");
        okButton.setBackground(Color.darkGray);
        okButton.setForeground(Color.cyan);
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                close();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                okButton.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                okButton.setForeground(Color.cyan);
            }
        });
    }

    private void addComponentToSouthPanel(Component component, Object position) {
        southPanel.add(component, position);
    }

    private void addComponentsToButtonsPanel(Component... components) {
        for (Component component : components) {
            buttonsPanel.add(component);
        }
    }

    private void addComponentToMainPanel(Component component, Object positon) {
        mainPanel.add(component, positon);
    }

    private void close() {
        setVisible(false);
        dispose();
    }

    public void showMessage() {
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
