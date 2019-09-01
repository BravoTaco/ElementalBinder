package helpers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHandler {
    private static final String fileLocation = System.getProperty("user.home") + File.separator + "OSBot" + File.separator + "Data" + File.separator + "ElementalBinder" + File.separator + "Settings.ini";
    private static final String dir = System.getProperty("user.home") + File.separator + "OSBot" + File.separator + "Data" + File.separator + "ElementalBinder" + File.separator;

    public static String loadRcerInfo() {
        File saveFile = getSaveFile();
        if (saveFile != null) {
            try {
                StringBuilder sb = new StringBuilder();
                FileReader fr = new FileReader(saveFile);
                ArrayList<Character> rcerName = new ArrayList<>();
                int c;
                while ((c = fr.read()) != -1) {
                    rcerName.add((char) c);
                }
                for (int i = rcerName.lastIndexOf('|') + 1; i < rcerName.size(); i++) {
                    sb.append(rcerName.get(i));
                }
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String loadMuleInfo() {
        File saveFile = getSaveFile();
        if (saveFile != null) {
            try {
                StringBuilder sb = new StringBuilder();
                FileReader fr = new FileReader(saveFile);
                int c;
                while ((c = fr.read()) != -1) {
                    if ((char) c == '\n') {
                        sb.append(", ");
                    } else if ((char) c == '|') {
                        break;
                    }
                    if ((char) c != '\n') {
                        sb.append((char) c);
                    }
                }
                sb.deleteCharAt(sb.lastIndexOf(", "));
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static boolean saveInfo(String muleInfo, String rcerInfo) {
        return save(muleInfo, rcerInfo);
    }

    private static boolean save(String muleInfo, String rcerInfo) {
        try {
            File saveFile = getSaveFile();
            if (saveFile == null) {
                if (createDirectory()) {
                    saveFile = new File(fileLocation);
                    if (saveFile.createNewFile()) {
                        System.out.println("Created a new SaveFile!");
                    }
                }
            } else {
                System.out.println("Save File Exists. Overwriting current file!");
            }
            StringBuilder sb = new StringBuilder();
            if (saveFile.exists() && saveFile.canWrite()) {
                FileWriter fw = new FileWriter(saveFile);
                System.out.println("Writing Save info to File!");
                char[] muleInfoArray = muleInfo.toCharArray();
                for (int i = 0; i < muleInfo.length(); i++) {
                    if (muleInfoArray[i] != ' ' && muleInfoArray[i] != ',') {
                        sb.append(muleInfoArray[i]);
                    } else if (muleInfoArray[i] == ' ' && muleInfoArray[i - 1] == ',') {
                        sb.append('\n');
                    } else if (muleInfoArray[i] == ' ' && muleInfoArray[i - 1] != ',') {
                        sb.append(muleInfoArray[i]);
                    }
                }
                sb.lastIndexOf("   ");
                sb.append("\n ||");
                fw.write(sb.toString());
                char[] rcerName = rcerInfo.toCharArray();
                sb = new StringBuilder();
                for (char c : rcerName) {
                    if (c != '|' && c != '\n') {
                        sb.append(c);
                    }
                }
                fw.write(sb.toString());
                fw.close();
                System.out.println("Saving Process Complete!");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private static boolean createDirectory() {
        try {
            if (getSaveFile() == null) {
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

    private static File getSaveFile() {
        try {
            File file = new File(fileLocation);
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
