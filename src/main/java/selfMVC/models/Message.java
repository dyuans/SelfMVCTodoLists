package selfMVC.models;

public class Message extends BaseModel {
    public String author;
    public String message;

    public Message() {

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    // @Override
    // public String toString() {
    //     String s = String.format(
    //             "(author: %s, message: %s)",
    //             this.author,
    //             this.message
    //     );
    //     return s;
    // }
}
