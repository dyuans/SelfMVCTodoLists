package selfMVC.models;

public class Todo  extends BaseModel {
    public Integer id;
    public String content;
    public Boolean completed;
    public Integer userId;

    public Todo() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // @Override
    // public String toString() {
    //     String s = String.format(
    //             "(id: %s, content: %s, completed: %s)",
    //             this.id,
    //             this.content,
    //             this.completed
    //     );
    //
    //     return s;
    // }
}
