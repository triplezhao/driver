package potato.driver.data.bean;

/**
 * Created by ztw on 2015/7/6.
 */
public class AppBean {
    private String title;
    private String content;
    private String icon;
    private String apkUrl;

    public AppBean(String title, String content, String icon, String apkUrl) {
        this.title = title;
        this.content = content;
        this.icon = icon;
        this.apkUrl = apkUrl;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
