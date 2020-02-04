package github;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class VersionChecker {

    private static boolean hasRunInitialCheck = false;

    private static float currentVersion = 0f;
    private static float gitHubVersion = 0f;

    private static String url = "TempUrl";

    private static InfoCache infoCache;

    public static boolean needsUpdated(String gitHubUserName, String gitHubProjectName, double thisScriptVersion) {
        if (!hasRunInitialCheck) {
            infoCache = new InfoCache(gitHubUserName, gitHubProjectName, thisScriptVersion);
            getVersions(infoCache);
        }
        System.out.println("GitHub Version: [" + gitHubVersion + "] :: " + "Script Version: [" + currentVersion + "]");
        if (gitHubVersion < currentVersion)
            System.out.println("Currently using experimental build!");
        return gitHubVersion > currentVersion;
    }

    public static boolean needsUpdated() {
        if (!hasRunInitialCheck) {
            throw new NullPointerException("Specify Paramters First. The paramters will than be cached!");
        }
        return gitHubVersion > currentVersion;
    }

    public static float getCurrentVersion() {
        return currentVersion;
    }

    private static void setCurrentVersion(String version) {
        currentVersion = Float.parseFloat(version);
    }

    public static float getGitHubVersion() {
        return gitHubVersion;
    }

    private static void setGitHubVersion(String version) {
        gitHubVersion = Float.parseFloat(version);
    }

    private static void getVersions(InfoCache infoCache) {
        setGitHubLink(infoCache.getGitHubUserName(), infoCache.getGitHubProjectName());
        setGitHubVersion(returnGitHubVersion());
        setCurrentVersion("" + infoCache.getThisScriptVersion());
        hasRunInitialCheck = true;
    }

    private static void setGitHubLink(String gitHubUserName, String gitHubProjectName) {
        url = String.format("https://api.github.com/repos/%s/%s/releases/latest", gitHubUserName, gitHubProjectName);
    }

    private static String returnGitHubVersion() {
        try {
            URL currentUrl = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) currentUrl.openConnection();
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            if (sb.toString().contains("\"tag_name\":")) {
                int startPoint = sb.toString().indexOf("\"tag_name\":");
                int endPoint = sb.toString().indexOf(",", startPoint);
                return sb.toString().substring(startPoint + 13, endPoint - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0";
    }

}
