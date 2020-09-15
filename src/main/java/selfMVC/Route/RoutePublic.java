package selfMVC.Route;

import selfMVC.SelfTemplate;
import selfMVC.Request;
import selfMVC.Utils;
import selfMVC.models.Message;
import selfMVC.models.Todo;
import selfMVC.models.User;
import selfMVC.service.MessageService;
import selfMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class RoutePublic {
    public static HashMap<String, Function<Request, byte[]>> routeMap() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        map.put("/", RoutePublic::routeIndex);
        map.put("/message", RoutePublic::routeMessage);
        return map;
    }

    public static byte[] routeIndex(Request request) {
//        String body = Route.html("index.html");
        User u = Route.currentUser(request);
//        body = body.replace("{username}", u.username);  //没有使用freemaker之前，简单的字符串替换来渲染前端页面
        ArrayList<Todo> todoList = TodoService.findByUserId(u.id);
        HashMap<String, Object> d = new HashMap<>();
        d.put("todos", todoList);
        d.put("currentuser", u.username);
        d.put("currentuserid", u.id);
        d.put("currentusernickname", u.nickname);
        String body = SelfTemplate.render(d, "index.ftl");

//        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
//        String response = header + body;
//        return response.getBytes(StandardCharsets.UTF_8);
        HashMap<String, String> header = new HashMap<>();
        header.put("Content-Type", "text/html");

        String response = Route.responseWithHeader(200, header, body);
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] routeMessage(Request request) {
        HashMap<String, String> data = null;
        if (request.method.equals("POST")) {
            Utils.log("this is post");
            data = request.form;
        } else if (request.method.equals("GET")) {
            Utils.log("this is get");
            data = request.query;
        } else {
            String m = String.format("unknown method (%s)", request.method);
            throw new RuntimeException(m);
        }
        Utils.log("get request args: %s", data);
        if (data != null) {
            MessageService.add(data);
        }

        ArrayList<Message> messageList = MessageService.load();
        HashMap<String, Object> d = new HashMap<>();
        d.put("messages", messageList);
        d.put("firstMessage", messageList.get(0));
        String body = SelfTemplate.render(d, "html_basic.ftl");


        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String response = header + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }
}
