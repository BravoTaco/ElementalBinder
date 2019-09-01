package gui;

import helpers.FileHandler;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static data.GlobalVariables.*;

public class MuleSettingsGUI {
    private JDialog mainDialog;
    private JPanel mainPanel;
    private JButton okButton;
    private JButton saveButton;
    private JButton loadButton;
    private JPanel buttonPanel;
    private JButton cancelButton;
    private JPanel settingsPanel;
    private JPanel enableMulingPanel;
    private JCheckBox enableMulingCB;
    private JPanel mulingSettingsPanel;
    private JCheckBox isMuleCB;
    private JPanel muleNamesPanel;
    private JPanel runecrafterNamePanel;
    private JLabel inputMuleNamesLabel;
    private JTextField muleNamesTextField;
    private JLabel inputRcNameLabel;
    private JTextField runecraftersNameTextField;

    public MuleSettingsGUI() {
        initMainDialog();
        initMainPanel();
        initButtonPanel();
        initLoadButton();
        initSaveButton();
        initCancelButton();
        initOkButton();
        initSettingsPanel();
        initEnableMulingPanel();
        initEnableMulingCB();
        initIsMuleCB();
        initMuleSettingsPanel();
        initMuleNamesPanel();
        initInputMuleNamesLabel();
        initMuleNamesTextField();
        initRunecrafterNamePanel();
        initInputRcNameLabel();
        initRcNameTextField();

    }

    public static void main(String args[]) {
        MuleSettingsGUI muleSettingsGUI = new MuleSettingsGUI();
        muleSettingsGUI.show();
        muleNames.clear();
    }

    public void show() {
        mainDialog.pack();
        mainDialog.setLocationRelativeTo(null);
        mainDialog.setVisible(true);
    }

    private void ok() {
        String tempName;
        StringBuilder sb = new StringBuilder();

        if (enableMulingCB.isSelected()) {
            mulingEnabled = true;
            if (isMuleCB.isSelected()) {
                isMule = true;
                if (runecraftersNameTextField.getText() != null) {
                    runecrafterName = runecraftersNameTextField.getText();
                }
            } else {
                if (muleNamesTextField.getText() != null) {
                    String tempString = muleNamesTextField.getText();

                    for (char c : tempString.toCharArray()) {
                        if (c != ',' && c != ' ') {
                            sb.append(c);
                        } else if (c == ' ') {
                            tempName = sb.toString();
                            muleNames.add(tempName);
                            sb = new StringBuilder();
                        }
                    }
                    tempName = sb.toString();
                    muleNames.add(tempName);
                }
            }
        }
        mainDialog.setVisible(false);
        mainDialog.dispose();
    }

    private void cancel() {
        mainDialog.setVisible(false);
        mainDialog.dispose();
    }

    private void save() {
        if (muleNamesTextField.getText() != null && runecraftersNameTextField.getText() != null) {
            FileHandler.saveInfo(muleNamesTextField.getText(), runecraftersNameTextField.getText());
        }
    }

    private void load() {
        muleNamesTextField.setText(FileHandler.loadMuleInfo());
        runecraftersNameTextField.setText(FileHandler.loadRcerInfo());
        mainPanel.update(mainPanel.getGraphics());
    }

    private void initMainDialog() {
        mainDialog = new JDialog();
        mainDialog.setModal(true);
        mainDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    }

    private void initMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(-13553359));
        mainPanel.setForeground(new Color(-10306630));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Mule Settings", TitledBorder.CENTER, TitledBorder.BELOW_TOP, this.getFont(null, -1, 20, mainPanel.getFont()), new Color(-10306630)));
        mainDialog.add(mainPanel);
    }

    private void initButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.setBackground(new Color(-13553359));
        buttonPanel.setForeground(new Color(-10306630));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
    }

    private void initSaveButton() {
        saveButton = new JButton();
        saveButton.setBackground(new Color(-13553359));
        saveButton.setForeground(new Color(-10306630));
        saveButton.setText("Save");
        buttonPanel.add(saveButton);

        saveButton.addActionListener(e -> save());

        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setForeground(Color.yellow);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setForeground(new Color(-10306630));
            }
        });
    }

    private void initLoadButton() {
        loadButton = new JButton();
        loadButton.setBackground(new Color(-13553359));
        loadButton.setForeground(new Color(-10306630));
        loadButton.setText("Load");
        buttonPanel.add(loadButton);

        loadButton.addActionListener(e -> load());

        loadButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loadButton.setForeground(Color.ORANGE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loadButton.setForeground(new Color(-10306630));
            }
        });
    }

    private void initCancelButton() {
        cancelButton = new JButton();
        cancelButton.setBackground(new Color(-13553359));
        cancelButton.setForeground(new Color(-10306630));
        cancelButton.setText("Cancel");
        buttonPanel.add(cancelButton);

        cancelButton.addActionListener(e -> cancel());

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setForeground(new Color(-10306630));
            }
        });
    }

    private void initOkButton() {
        okButton = new JButton();
        okButton.setBackground(new Color(-13553359));
        okButton.setForeground(new Color(-10306630));
        okButton.setText("Ok");
        buttonPanel.add(okButton);

        okButton.addActionListener(e -> ok());

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                okButton.setForeground(Color.green);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                okButton.setForeground(new Color(-10306630));
            }
        });
    }

    private void initSettingsPanel() {
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BorderLayout(0, 0));
        settingsPanel.setBackground(new Color(-13553359));
        settingsPanel.setForeground(new Color(-10306630));
        mainPanel.add(settingsPanel, BorderLayout.CENTER);
        settingsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
    }

    private void initEnableMulingPanel() {
        enableMulingPanel = new JPanel();
        enableMulingPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        enableMulingPanel.setBackground(new Color(-13553359));
        enableMulingPanel.setForeground(new Color(-10306630));
        settingsPanel.add(enableMulingPanel, BorderLayout.NORTH);
        enableMulingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
    }

    private void initEnableMulingCB() {
        enableMulingCB = new JCheckBox();
        enableMulingCB.setBackground(new Color(-13553359));
        enableMulingCB.setForeground(new Color(-10306630));
        enableMulingCB.setText("Enable Muling");
        enableMulingPanel.add(enableMulingCB);

        enableMulingCB.addActionListener(e -> {
            if (enableMulingCB.isSelected()) {
                muleNamesTextField.setEnabled(true);
                isMuleCB.setEnabled(true);
            } else {
                muleNamesTextField.setEnabled(false);
                isMuleCB.setEnabled(false);
                isMuleCB.setSelected(false);
                runecraftersNameTextField.setEnabled(false);
            }
        });
    }

    private void initIsMuleCB() {
        isMuleCB = new JCheckBox();
        isMuleCB.setBackground(new Color(-13553359));
        isMuleCB.setForeground(new Color(-10306630));
        isMuleCB.setText("Is Mule");
        enableMulingPanel.add(isMuleCB);

        isMuleCB.setEnabled(false);

        isMuleCB.addActionListener(e -> {
            if (isMuleCB.isSelected()) {
                muleNamesTextField.setEnabled(false);
                runecraftersNameTextField.setEnabled(true);
            } else {
                runecraftersNameTextField.setEnabled(false);
                if (enableMulingCB.isSelected()) {
                    muleNamesTextField.setEnabled(true);
                }
            }
        });
    }

    private void initMuleSettingsPanel() {
        mulingSettingsPanel = new JPanel();
        mulingSettingsPanel.setLayout(new BorderLayout(0, 0));
        mulingSettingsPanel.setBackground(new Color(-13553359));
        mulingSettingsPanel.setForeground(new Color(-10306630));
        settingsPanel.add(mulingSettingsPanel, BorderLayout.CENTER);
        mulingSettingsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null));
    }

    private void initMuleNamesPanel() {
        muleNamesPanel = new JPanel();
        muleNamesPanel.setLayout(new BorderLayout(0, 0));
        muleNamesPanel.setBackground(new Color(-13553359));
        muleNamesPanel.setForeground(new Color(-10306630));
        mulingSettingsPanel.add(muleNamesPanel, BorderLayout.NORTH);
    }

    private void initInputMuleNamesLabel() {
        inputMuleNamesLabel = new JLabel();
        inputMuleNamesLabel.setBackground(new Color(-13553359));
        inputMuleNamesLabel.setForeground(new Color(-10306630));
        inputMuleNamesLabel.setHorizontalAlignment(0);
        inputMuleNamesLabel.setText("Input Mule Names. Seperate each name with a , and a space. Example: BravoTaco, Bob, Mule");
        muleNamesPanel.add(inputMuleNamesLabel, BorderLayout.CENTER);
    }

    private void initMuleNamesTextField() {
        muleNamesTextField = new JTextField();
        muleNamesTextField.setForeground(new Color(-16777216));
        muleNamesPanel.add(muleNamesTextField, BorderLayout.SOUTH);

        muleNamesTextField.setEnabled(false);
    }

    private void initRunecrafterNamePanel() {
        runecrafterNamePanel = new JPanel();
        runecrafterNamePanel.setLayout(new BorderLayout(0, 0));
        runecrafterNamePanel.setBackground(new Color(-13553359));
        runecrafterNamePanel.setForeground(new Color(-10306630));
        mulingSettingsPanel.add(runecrafterNamePanel, BorderLayout.SOUTH);
        runecrafterNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null));
    }

    private void initInputRcNameLabel() {
        inputRcNameLabel = new JLabel();
        inputRcNameLabel.setBackground(new Color(-13553359));
        inputRcNameLabel.setForeground(new Color(-10306630));
        inputRcNameLabel.setHorizontalAlignment(0);
        inputRcNameLabel.setHorizontalTextPosition(11);
        inputRcNameLabel.setText("Input Runecrafter's name");
        inputRcNameLabel.setVerticalAlignment(0);
        runecrafterNamePanel.add(inputRcNameLabel, BorderLayout.NORTH);
    }

    private void initRcNameTextField() {
        runecraftersNameTextField = new JTextField();
        runecraftersNameTextField.setForeground(new Color(-16777216));
        runecraftersNameTextField.setText("");
        runecrafterNamePanel.add(runecraftersNameTextField, BorderLayout.SOUTH);

        runecraftersNameTextField.setEnabled(false);
    }

    private Font getFont(String fontName, int style, int size, Font currentFont) {
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
