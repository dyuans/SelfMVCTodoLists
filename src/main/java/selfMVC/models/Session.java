package selfMVC.models;

public class Session  extends BaseModel {
    public String sessionId;
    public Integer userId;

    public Session() {

    }

    // @Override
    // public String toString() {
    //     String s = String.format(
    //             "(sessionId: %s, userId: %s)",
    //             this.sessionId,
    //             this.userId
    //     );
    //
    //     return s;
    // }
}
