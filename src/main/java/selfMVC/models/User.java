package selfMVC.models;

public class User  extends BaseModel {
    public Integer id;
    public String username;
    public String password;
    public String nickname;
    public UserRole role;
    // public Boolean deleted;

    public User() {

    }

    // @Override
    // public String toString() {
    //     String s = String.format(
    //             "(id: %s, userbane: %s, password: %s, nickname: %s, role: %s)",
    //             this.id,
    //             this.username,
    //             this.password,
    //             this.nickname,
    //             this.role
    //     );
    //
    //     return s;
    // }
}
