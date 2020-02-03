package gui;

import data.GlobalVariables;
import enums.Runes;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {
    private JPanel mainPanel;
    private JPanel startCancelPanel;
    private JButton cancelButton;
    private JButton startButton;
    private JButton muleSettings;
    private JPanel infoSelectorPanel;
    private JLabel selectRuneLabel;
    private JComboBox<Runes> runeCB;
    private JDialog mainDialog;

    private boolean closed = false;

    private Color defaultForeground = new Color(-10306630);
    private Color defaultBackground = new Color(-13553359);

    public GUI() {
        initializeMainDialog();
        initializeMainPanel();
        initializeStartCancelPanel();
        initializeInfoSelectorPanel();
        initializeButtons();
        initializeRuneCBLabel();
        initializeRuneCB();
    }

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.show();
    }

    public void show() {
        mainDialog.setVisible(true);
    }

    public void close() {
        mainDialog.setVisible(false);
        mainDialog.dispose();
    }

    private void openMuleSettingsGUI() {
        MuleSettingsGUI muleSettingsGUI = new MuleSettingsGUI();
        muleSettingsGUI.show();
    }

    public boolean exited() {
        return !mainDialog.isVisible() && !closed;
    }

    private void start() {
        GlobalVariables.rune = (Runes) runeCB.getSelectedItem();
        if (GlobalVariables.rune != null) {
            GlobalVariables.talisman = GlobalVariables.rune.getTalisman();
            GlobalVariables.tiara = GlobalVariables.rune.getTiara();
        }
        mainDialog.setVisible(false);
        mainDialog.dispose();
        closed = true;
    }

    private void initializeMainDialog() {
        mainDialog = new JDialog();
        mainDialog.setModal(true);
        mainDialog.setLocationRelativeTo(null);
        mainDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        mainDialog.setMinimumSize(new Dimension(500, 300));
    }

    private void initializeMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBackground(defaultBackground);
        mainPanel.setForeground(defaultForeground);
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Elemental Binder", TitledBorder.CENTER, TitledBorder.BELOW_TOP, setFont(null, Font.BOLD, 20, mainPanel.getFont()), new Color(-10306630)));
        mainDialog.add(mainPanel);
    }

    private void initializeStartCancelPanel() {
        startCancelPanel = new JPanel();
        startCancelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        startCancelPanel.setBackground(defaultBackground);
        startCancelPanel.setForeground(defaultForeground);
        mainPanel.add(startCancelPanel, BorderLayout.SOUTH);
        startCancelPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, setFont(null, -1, -1, startCancelPanel.getFont())));
    }

    private void initializeInfoSelectorPanel() {
        infoSelectorPanel = new JPanel();
        infoSelectorPanel.setLayout(new GridBagLayout());
        infoSelectorPanel.setBackground(defaultBackground);
        infoSelectorPanel.setForeground(defaultForeground);
        mainPanel.add(infoSelectorPanel, BorderLayout.CENTER);
        infoSelectorPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
    }

    private void initializeRuneCB() {
        runeCB = new JComboBox<>(Runes.values());
        runeCB.setBackground(defaultBackground);
        runeCB.setForeground(defaultForeground);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        infoSelectorPanel.add(runeCB, gbc);
    }

    private void initializeRuneCBLabel() {
        selectRuneLabel = new JLabel();
        selectRuneLabel.setBackground(defaultBackground);
        selectRuneLabel.setForeground(defaultForeground);
        selectRuneLabel.setText("Select a type of rune:");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        infoSelectorPanel.add(selectRuneLabel, gbc);
    }

    private void initializeButtons() {
        muleSettings = new JButton();
        muleSettings.setBackground(defaultBackground);
        muleSettings.setForeground(defaultForeground);
        muleSettings.setHorizontalAlignment(0);
        muleSettings.setHorizontalTextPosition(0);
        muleSettings.setText("Mule Settings");
        muleSettings.setVerticalAlignment(0);
        muleSettings.setVerticalTextPosition(0);
        startCancelPanel.add(muleSettings);
        cancelButton = new JButton();
        cancelButton.setBackground(defaultBackground);
        cancelButton.setForeground(defaultForeground);
        cancelButton.setHorizontalAlignment(0);
        cancelButton.setHorizontalTextPosition(0);
        cancelButton.setText("Cancel");
        cancelButton.setVerticalAlignment(0);
        cancelButton.setVerticalTextPosition(0);
        startCancelPanel.add(cancelButton);
        startButton = new JButton();
        startButton.setBackground(defaultBackground);
        startButton.setForeground(defaultForeground);
        startButton.setHorizontalAlignment(0);
        startButton.setHorizontalTextPosition(0);
        startButton.setText("Start");
        startButton.setVerticalAlignment(0);
        startButton.setVerticalTextPosition(0);
        startCancelPanel.add(startButton);

        cancelButton.addActionListener(e -> close());
        startButton.addActionListener(e -> start());
        muleSettings.addActionListener(e -> openMuleSettingsGUI());

        muleSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                muleSettings.setForeground(Color.ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                muleSettings.setForeground(defaultForeground);
            }
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setForeground(Color.green);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setForeground(defaultForeground);
            }
        });

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setForeground(defaultForeground);
            }
        });
    }

    private Font setFont(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }
}
