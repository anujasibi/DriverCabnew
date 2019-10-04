package creo.com.driver;

public class ReviewPojo {
    public String name,description;
    public int image;

    public ReviewPojo(String name, int image, String description) {
        this.name=name;
        this.image=image;
        this.description=description;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }
}
