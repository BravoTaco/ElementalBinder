package helpers;

import data.GlobalVariables;
import data.SavedData;

import java.io.*;

public class FileHandler {
    private static final String muleFileLocation = System.getProperty("user.home") + File.separator + "OSBot" + File.separator + "Data" + File.separator + "ElementalBinder" + File.separator + "MuleSettings.ini";
    private static final String crafterFileLocation = System.getProperty("user.home") + File.separator + "OSBot" + File.separator + "Data" + File.separator + "ElementalBinder" + File.separator + "CrafterSettings.ini";
    private static final String dir = System.getProperty("user.home") + File.separator + "OSBot" + File.separator + "Data" + File.separator + "ElementalBinder" + File.separator;

    public static boolean loadSavedData(boolean crafterSave) {
        try {
            File saveFile = getSaveFile(crafterSave);
            if (saveFile == null) {
                GlobalVariables.script.log("No save file exists!");
                return false;
            }

            FileInputStream f = new FileInputStream(saveFile);
            ObjectInputStream o = new ObjectInputStream(f);

            GlobalVariables.savedData = (SavedData) o.readObject();

            f.close();
            o.close();

            return true;
        } catch (IOException | ClassNotFoundException ie) {
            ie.printStackTrace();
        }
        return false;
    }

    public static boolean saveSavedData(boolean crafterSave) {
        try {
            File saveFile = getSaveFile(crafterSave);
            if (saveFile == null)
                saveFile = createSaveFile(crafterSave);

            FileOutputStream f = new FileOutputStream(saveFile);
            ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(GlobalVariables.savedData);
            o.close();
            f.close();

            return true;
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return false;
    }

    private static File createSaveFile(boolean crafterSave) throws IOException {
        File saveFile = null;
        if (createDirectory(crafterSave)) {
            String path = (crafterSave) ? crafterFileLocation : muleFileLocation;
            saveFile = new File(path);
            if (saveFile.createNewFile()) {
                GlobalVariables.script.log("Created new save file!");
            }
        }
        return saveFile;
    }

    private static boolean createDirectory(boolean crafterSave) {
        try {
            if (getSaveFile(crafterSave) == null) {
                File directory = new File(dir);
                if (directory.mkdirs()) {
                    System.out.println("Made Directory!");
                    return true;
                } else if (directory.exists()) {
                    System.out.println("Directory Already exists!");
                    return true;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private static File getSaveFile(boolean crafterSave) {
        try {
            File file = (crafterSave) ? new File(crafterFileLocation) : new File(muleFileLocation);
            if (file.exists()) {
                return file;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
