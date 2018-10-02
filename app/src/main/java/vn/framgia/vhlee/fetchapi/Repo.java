package vn.framgia.vhlee.fetchapi;

public class Repo {
    private String mName;
    private String mDescription;
    private String mAvatar;
    private String mUrl;

    public Repo(String name, String description, String avatar, String url) {
        mName = name;
        mDescription = description;
        mAvatar = avatar;
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
