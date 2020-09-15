package selfMVC.service;

import selfMVC.Utils;
import selfMVC.models.ModelFactory;
import selfMVC.models.User;
import selfMVC.models.UserRole;

import java.util.ArrayList;
import java.util.HashMap;

// impot ../Utils


public class UserService {
    public static User add(HashMap<String, String> form) {
        String username = form.get("username");
        String password = form.get("password");
        String nickname = form.get("nickname");
        User m = new User();
        m.password = password;
        m.username = username;
        m.nickname = nickname;
        m.role = UserRole.normal;

        ArrayList<User> all = load();
        if (all.isEmpty()){
            m.id = 1;
        } else {
            User last = all.get(all.size() - 1);
            m.id = last.id + 1;
        }
        all.add(m);
        save(all);

        return m;
    }

    public static void save(ArrayList<User> list) {
        // 数据文件用的是 类名.txt
        String className = User.class.getSimpleName();
        // ModelFactory.save(className, list, new SerializerUser());
        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.id.toString());
            lines.add(model.username);
            lines.add(model.password);
            lines.add(model.nickname);
            lines.add(model.role.toString());
            return lines;
        });
    }

    public static ArrayList<User> load() {
        String className = User.class.getSimpleName();
        // ArrayList<User> rs = ModelFactory.load(className, 2, new DeserializerUser());
        ArrayList<User> rs = ModelFactory.load(className, 5, modelData -> {
            Integer id = Integer.parseInt(modelData.get(0));
            String username = modelData.get(1);
            String password = modelData.get(2);
            String nickname = modelData.get(3);
            UserRole role = UserRole.valueOf(modelData.get(4));

            User m = new User();
            m.id = id;
            m.username = username;
            m.password = password;
            m.nickname = nickname;
            m.role = role;

            return m;
        });
        return rs;
    }

    public static Boolean validLogin(String username, String password) {
        Utils.log("validLogin in");
        ArrayList<User> userList = load();
        Utils.log("userList: %s", userList);
        for (User u: userList) {
            if (u.username.equals(username) && u.password.equals(password)) {
                return true;
            }
        }

        Utils.log("validLogin end");
        return false;
    }

    public static User findByUsername(String username) {
        ArrayList<User> userList = load();
        for (User u: userList) {
            if (u.username.equals(username)) {
                return u;
            }
        }
        return null;
    }

    public static User findById(Integer id) {
        ArrayList<User> userList = load();
        for (User u: userList) {
            if (u.id.equals(id)) {
                return u;
            }
        }
        return null;
    }

    public static User guest() {
        User u = new User();
        u.id = -1;
        u.username = "游客";
        u.role = UserRole.guest;
        u.nickname = "nothing here";
        return u;
    }

    public static void main(String[] args) {
       Utils.log("%s ", findByUsername("linxi"));
       Utils.log("%s ", findByUsername("bucunzai"));
    }

}