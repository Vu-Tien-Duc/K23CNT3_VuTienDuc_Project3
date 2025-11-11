package k23cnt3.vtdLesson05.entity;

public class Info {
    String name;
    String nickName;
    String email;
    String webSite;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Info(String name, String nickName, String email, String webSite) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.webSite = webSite;
    }

    public Info() {
    }
}
