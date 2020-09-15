package selfMVC.Route;

import selfMVC.SelfTemplate;
import selfMVC.Request;
import selfMVC.Utils;
import selfMVC.models.Todo;
import selfMVC.models.User;
import selfMVC.service.SessionService;
import selfMVC.service.TodoService;
import selfMVC.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;


public class RouteUser {
        public static HashMap<String, Function<Request, byte[]>> routeMap() {
            HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
            map.put("/login", RouteUser::login);
            map.put("/register", RouteUser::register);
            return map;
        }

        public static byte[] login(Request request) {
            HashMap<String, String> header = new HashMap<>();
            header.put("Content-Type", "text/html");
            HashMap<String, String> data = null;
            if (request.method.equals("POST")) {
                data = request.form;
            }

            Utils.log("login: <%s>", data);

            String loginResult = "";
            Utils.log("login before data");
            if (data != null) {
                Utils.log("login data not null");
                String username = data.get("username");
                String password = data.get("password");
                if (UserService.validLogin(username, password)) {
                    String sessionId = UUID.randomUUID().toString();
                    User user = UserService.findByUsername(username);
                    SessionService.add(sessionId, user.id);
                    header.put("Set-Cookie", String.format("session_id=%s", sessionId));
                    loginResult = "登录成功";


                    ArrayList<Todo> todoList = TodoService.findByUserId(user.id);
                    HashMap<String, Object> d = new HashMap<>();
                    d.put("todos", todoList);
                    d.put("currentuser", user.username);
                    d.put("currentuserid", user.id);
                    d.put("currentusernickname", user.nickname);
                    String body = SelfTemplate.render(d, "index.ftl");

                    String response = Route.responseWithHeader(200, header, body);
                    return response.getBytes(StandardCharsets.UTF_8);

                } else {
                    loginResult = "登录失败";

                }
            }


            HashMap<String, Object> d = new HashMap<>();
            d.put("result", loginResult);
            String body = SelfTemplate.render(d, "login.ftl");



            String response = Route.responseWithHeader(200, header, body);
            return response.getBytes(StandardCharsets.UTF_8);
        }


        public static byte[] register(Request request) {
            HashMap<String, String> data = null;
            if (request.method.equals("POST")) {
                data = request.form;
            }

            String registerResult = "";
            if (data != null) {
                UserService.add(data);
                registerResult = "注册成功";
            }

            HashMap<String, Object> d = new HashMap<>();
            d.put("registerResult", registerResult);


//            String body = Route.html("register.html");
//            body = body.replace("{registerResult}", registerResult);
            String body = SelfTemplate.render(d, "register.ftl");

            HashMap<String, String> header = new HashMap<>();
            header.put("Content-Type", "text/html");

            String response = Route.responseWithHeader(200, header, body);
            return response.getBytes(StandardCharsets.UTF_8);
        }

}
