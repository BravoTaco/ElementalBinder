package gui;

import Utils.ArrayUtilities;
import Utils.StringUtilities;
import data.GlobalVariables;
import enums.Runes;
import github.VersionChecker;
import helpers.FileHandler;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Driver {
    public static void main(String... args) {
        GUI gui = new GUI();
        gui.start();
    }
}

public class GUI extends JDialog {

    private boolean startClicked;

    private JMenuBar menuBar;
    private JMenu saveLoadMenu;
    private JMenuItem saveMuleItem;
    private JMenuItem loadMuleItem;
    private JMenuItem saveCrafterItem;
    private JMenuItem loadCrafterItem;
    private JMenu settingsMenu;
    private JMenuItem baseSettings;
    private JMenuItem muleSettings;

    private JPanel mainPanel;
    private JPanel southPanel;
    private JPanel settingsPanel;

    private JPanel baseSettingsPanel;
    private JPanel muleSettingsPanel;
    private JPanel buttonsPanel;

    private JButton startButton;
    private JButton cancelButton;

    private JPanel runeSelectionPanel;
    private JComboBox<Runes> runeSelectorCB;
    private JLabel runeSelectorLabel;

    private JPanel muleTopPanel;
    private JCheckBox isMulingCB;
    private JCheckBox isMuleCB;

    private JPanel muleNamesPanel;
    private JPanel addMuleNamePanel;
    private JLabel enterMuleNameLabel;
    private JTextField muleNameTF;
    private JButton submitMuleNameButton;
    private JList<String> muleNamesList;
    private JButton removeButton;

    private JPanel runecrafterNamePanel;
    private JLabel runecrafterNameLabel;
    private JTextField runecrafterNameTF;
    private JButton setRCName;

    public GUI() {
        initializeSelf();

        initializeMainPanels();

        initializeMenuBar();

        addComponentsToBaseSettings();

        addComponentsToMuleSettings();

        addComponentsToButtonsPanel();
    }

    private void addComponentsToMuleSettings() {
        muleTopPanel = new JPanel();
        setDefaultColors(muleTopPanel);
        muleSettingsPanel.add(muleTopPanel, BorderLayout.NORTH);

        isMulingCB = new JCheckBox("Enable Muling");
        setDefaultColors(isMulingCB);
        muleTopPanel.add(isMulingCB);
        isMulingCB.addActionListener(e -> {
            if (isMulingCB.isSelected()) {
                isMuleCB.setEnabled(true);
                GlobalVariables.savedData.setMuling(true);
                setCanModifyMules(true);
            } else {
                isMuleCB.setEnabled(false);
                isMuleCB.setSelected(false);
                GlobalVariables.savedData.setIsMule(false);
                GlobalVariables.savedData.setMuling(false);
                setCanModifyCraftersName(false);
                setCanModifyMules(false);
            }
        });

        isMuleCB = new JCheckBox("Is Mule");
        setDefaultColors(isMuleCB);
        muleTopPanel.add(isMuleCB);
        isMuleCB.setEnabled(false);
        isMuleCB.addActionListener(e -> {
            if (isMuleCB.isSelected()) {
                GlobalVariables.savedData.setIsMule(true);
                setCanModifyMules(false);
                setCanModifyCraftersName(true);
            } else {
                GlobalVariables.savedData.setIsMule(false);
                setCanModifyMules(true);
                setCanModifyCraftersName(false);
            }
        });

        muleNamesPanel = new JPanel(new BorderLayout());
        setDefaultColors(muleNamesPanel);
        muleSettingsPanel.add(muleNamesPanel, BorderLayout.CENTER);

        addMuleNamePanel = new JPanel();
        setDefaultColors(addMuleNamePanel);
        muleNamesPanel.add(addMuleNamePanel, BorderLayout.NORTH);

        enterMuleNameLabel = new JLabel("Enter the name of a mule: ");
        setDefaultColors(enterMuleNameLabel);
        addMuleNamePanel.add(enterMuleNameLabel);

        muleNameTF = new JTextField(20);
        setDefaultColors(muleNameTF);
        addMuleNamePanel.add(muleNameTF);

        submitMuleNameButton = new JButton("Add");
        setDefaultColors(submitMuleNameButton);
        addMuleNamePanel.add(submitMuleNameButton);
        submitMuleNameButton.addActionListener(e -> onAdd());
        submitMuleNameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (submitMuleNameButton.isEnabled())
                    submitMuleNameButton.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (submitMuleNameButton.isEnabled())
                    submitMuleNameButton.setForeground(Color.cyan);
            }
        });

        muleNamesList = new JList<>();
        muleNamesList.setFixedCellWidth(100);
        muleNamesList.setFixedCellHeight(30);
        muleNamesList.setVisibleRowCount(5);
        setDefaultColors(muleNamesList);
        JScrollPane scrollPane = new JScrollPane(muleNamesList);
        setDefaultColors(scrollPane);
        muleNamesPanel.add(scrollPane, BorderLayout.CENTER);
        muleNamesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!removeButton.isEnabled())
                    removeButton.setEnabled(true);
            }
        });

        removeButton = new JButton("Remove");
        setDefaultColors(removeButton);
        muleNamesPanel.add(removeButton, BorderLayout.SOUTH);
        removeButton.addActionListener(e -> onRemove());
        removeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (removeButton.isEnabled())
                    removeButton.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (removeButton.isEnabled())
                    removeButton.setForeground(Color.cyan);
            }
        });
        removeButton.setEnabled(false);

        runecrafterNamePanel = new JPanel();
        setDefaultColors(runecrafterNamePanel);
        muleSettingsPanel.add(runecrafterNamePanel, BorderLayout.SOUTH);

        runecrafterNameLabel = new JLabel("Enter Runecrafter's name: ");
        setDefaultColors(runecrafterNameLabel);
        runecrafterNamePanel.add(runecrafterNameLabel);

        runecrafterNameTF = new JTextField(20);
        setDefaultColors(runecrafterNameTF);
        runecrafterNamePanel.add(runecrafterNameTF);

        setRCName = new JButton("Set");
        setDefaultColors(setRCName);
        runecrafterNamePanel.add(setRCName);
        setRCName.addActionListener(e -> onSetName());
        setRCName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (setRCName.isEnabled())
                    setRCName.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (setRCName.isEnabled())
                    setRCName.setForeground(Color.cyan);
            }
        });

        setCanModifyMules(false);

        setCanModifyCraftersName(false);

    }

    private void onSetName() {
        String rcName = runecrafterNameTF.getText();
        if (!StringUtilities.isStringEmpty(rcName)) {
            GlobalVariables.savedData.setRunecrafterName(rcName);
        }
    }

    private void onAdd() {
        String muleName = muleNameTF.getText();
        if (!StringUtilities.isStringEmpty(muleName)) {
            GlobalVariables.savedData.muleNames().add(muleName);
            String[] temp = ArrayUtilities.getArrayFromHashSet(GlobalVariables.savedData.muleNames());
            muleNamesList.setListData(temp);
            muleNameTF.setText(null);
            removeButton.setEnabled(false);
        }
    }

    private void onRemove() {
        String muleName = muleNamesList.getSelectedValue();
        GlobalVariables.savedData.muleNames().remove(muleName);
        muleNamesList.setListData(ArrayUtilities.getArrayFromHashSet(GlobalVariables.savedData.muleNames()));
        removeButton.setEnabled(false);
    }

    private void setCanModifyCraftersName(boolean canModifyCraftersName) {
        setRCName.setEnabled(canModifyCraftersName);
        runecrafterNameTF.setEnabled(canModifyCraftersName);
        runecrafterNameLabel.setEnabled(canModifyCraftersName);
    }

    private void setCanModifyMules(boolean canModifyMules) {
        enterMuleNameLabel.setEnabled(canModifyMules);
        muleNameTF.setEnabled(canModifyMules);
        submitMuleNameButton.setEnabled(canModifyMules);
        muleNamesList.setEnabled(canModifyMules);
    }

    private void addComponentsToButtonsPanel() {
        cancelButton = new JButton("Cancel");
        setDefaultColors(cancelButton);
        cancelButton.addActionListener((e) -> onCancel());
        buttonsPanel.add(cancelButton);

        startButton = new JButton("Start");
        setDefaultColors(startButton);
        startButton.addActionListener((e) -> onStart());
        buttonsPanel.add(startButton);

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setForeground(Color.cyan);
            }
        });

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setForeground(Color.cyan);
            }
        });
    }

    private void addComponentsToBaseSettings() {
        runeSelectionPanel = new JPanel();
        setDefaultColors(runeSelectionPanel);
        baseSettingsPanel.add(runeSelectionPanel);

        runeSelectorLabel = new JLabel("Select type of rune: ");
        setDefaultColors(runeSelectorLabel);
        runeSelectionPanel.add(runeSelectorLabel);

        runeSelectorCB = new JComboBox<>(Runes.values());
        setDefaultColors(runeSelectorCB);
        runeSelectionPanel.add(runeSelectorCB);
    }

    private void initializeSelf() {
        setDefaultColors(this);
        setModal(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setMinimumSize(new Dimension(400, 300));
        setTitle("Elemental Binder: v" + VersionChecker.getCurrentVersion());
    }

    private void initializeMainPanels() {
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray.brighter()));
        mainPanel.setLayout(new BorderLayout());
        setDefaultColors(mainPanel);
        add(mainPanel);

        southPanel = new JPanel();
        setDefaultColors(southPanel);
        southPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray.brighter()));
        southPanel.setLayout(new BorderLayout());
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        buttonsPanel = new JPanel();
        setDefaultColors(buttonsPanel);
        southPanel.add(buttonsPanel, BorderLayout.EAST);

        settingsPanel = new JPanel();
        settingsPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray.brighter()));
        settingsPanel.setLayout(new CardLayout());
        setDefaultColors(settingsPanel);
        mainPanel.add(settingsPanel, BorderLayout.CENTER);

        baseSettingsPanel = new JPanel();
        setDefaultColors(baseSettingsPanel);
        baseSettingsPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray.brighter()));
        baseSettingsPanel.setLayout(new BorderLayout());
        settingsPanel.add(baseSettingsPanel, "BaseSettings");

        muleSettingsPanel = new JPanel();
        setDefaultColors(muleSettingsPanel);
        muleSettingsPanel.setBorder(BorderFactory.createLineBorder(Color.darkGray.brighter()));
        muleSettingsPanel.setLayout(new BorderLayout());
        settingsPanel.add(muleSettingsPanel, "MuleSettings");

    }

    private void initializeMenuBar() {
        menuBar = new JMenuBar();
        setDefaultColors(menuBar);
        setJMenuBar(menuBar);

        // Save Load
        saveLoadMenu = new JMenu("Save/Load");
        setDefaultColors(saveLoadMenu);
        menuBar.add(saveLoadMenu);

        saveMuleItem = new JMenuItem("Save Mule");
        setDefaultColors(saveMuleItem);
        saveMuleItem.addActionListener(e -> onSave(false));
        saveLoadMenu.add(saveMuleItem);


        loadMuleItem = new JMenuItem("Load Mule");
        setDefaultColors(loadMuleItem);
        loadMuleItem.addActionListener(e -> onLoad(false));
        saveLoadMenu.add(loadMuleItem);

        saveCrafterItem = new JMenuItem("Save Crafter");
        setDefaultColors(saveCrafterItem);
        saveCrafterItem.addActionListener(e -> onSave(true));
        saveLoadMenu.add(saveCrafterItem);

        loadCrafterItem = new JMenuItem("Load Crafter");
        setDefaultColors(loadCrafterItem);
        loadCrafterItem.addActionListener(e -> onLoad(true));
        saveLoadMenu.add(loadCrafterItem);
        // End Save Load

        // Settings
        settingsMenu = new JMenu("Settings");
        setDefaultColors(settingsMenu);
        menuBar.add(settingsMenu);

        baseSettings = new JMenuItem("Core");
        setDefaultColors(baseSettings);
        baseSettings.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) settingsPanel.getLayout();
            cardLayout.show(settingsPanel, "BaseSettings");
        });
        settingsMenu.add(baseSettings);

        muleSettings = new JMenuItem("Mule");
        setDefaultColors(muleSettings);
        muleSettings.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) settingsPanel.getLayout();
            cardLayout.show(settingsPanel, "MuleSettings");
        });
        settingsMenu.add(muleSettings);
        // End Settings
    }

    private void onLoad(boolean crafterSave) {
        FileHandler.loadSavedData(crafterSave);
        runeSelectorCB.setSelectedItem(GlobalVariables.savedData.selectedRune());
        isMulingCB.setSelected(GlobalVariables.savedData.isMuling());
        if (isMulingCB.isSelected())
            setCanModifyMules(true);
        isMuleCB.setSelected(GlobalVariables.savedData.isMule());
        if (GlobalVariables.savedData.isMule()) {
            isMuleCB.setEnabled(true);
            setCanModifyMules(false);
            runecrafterNameTF.setText(GlobalVariables.savedData.runecrafterName());
        }
        muleNamesList.setListData(ArrayUtilities.getArrayFromHashSet(GlobalVariables.savedData.muleNames()));
    }

    private void onSave(boolean crafterSave) {
        GlobalVariables.savedData.setSelectedRune((Runes) runeSelectorCB.getSelectedItem());
        FileHandler.saveSavedData(crafterSave);
    }

    private void onStart() {
        startClicked = true;
        GlobalVariables.savedData.setSelectedRune((Runes) runeSelectorCB.getSelectedItem());
        setVisible(false);
    }

    private void onCancel() {
        setVisible(false);
        dispose();
    }

    private void setDefaultColors(Component component) {
        component.setBackground(Color.darkGray);
        component.setForeground(Color.cyan);
    }

    public void start() {
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(GlobalVariables.script.getBot().getCanvas());
        setVisible(true);
    }

    public boolean wasExited() {
        return !isVisible() && !startClicked;
    }
}
