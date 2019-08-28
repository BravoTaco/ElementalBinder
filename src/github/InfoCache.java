package github;

public class InfoCache {
    private String gitHubUserName;
    private String gitHubProjectName;
    private double thisScriptVersion;

    public InfoCache(String gitHubUserName, String gitHubProjectName, double thisScriptVersion) {
        this.gitHubUserName = gitHubUserName;
        this.gitHubProjectName = gitHubProjectName;
        this.thisScriptVersion = thisScriptVersion;
    }

    public String getGitHubUserName() {
        return gitHubUserName;
    }

    public String getGitHubProjectName() {
        return gitHubProjectName;
    }

    public double getThisScriptVersion() {
        return thisScriptVersion;
    }

    @Override
    public String toString() {
        return String.format("UserName: [%s] :: ProjectName [%s] :: ThisScriptVersion: [%.2f]", this.gitHubUserName, this.gitHubProjectName, this.thisScriptVersion);
    }
}

