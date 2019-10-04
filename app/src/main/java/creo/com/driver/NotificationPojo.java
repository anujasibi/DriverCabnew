package creo.com.driver;

public class NotificationPojo {
    public String name;
    public int Image;

    public NotificationPojo(String name, int image) {
        this.Image=image;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
