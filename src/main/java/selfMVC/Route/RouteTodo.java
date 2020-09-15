package selfMVC.Route;

import selfMVC.SelfTemplate;
import selfMVC.Request;
import selfMVC.models.Todo;
import selfMVC.models.User;
import selfMVC.models.UserRole;
import selfMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;


public class RouteTodo {
        public static HashMap<String, Function<Request, byte[]>> routeMap() {
            HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
            map.put("/todo", RouteTodo::index);
            map.put("/todo/add", RouteTodo::add);
            map.put("/todo/delete", RouteTodo::delete);
            map.put("/todo/edit", RouteTodo::edit);
            map.put("/todo/update", RouteTodo::update);
            return map;
        }

        public static String responseWithHeader(int code, HashMap<String, String> headerMap, String body) {
            String header = String.format("HTTP/1.1 %s\r\n", code);

            for (String key: headerMap.keySet()) {
                String value = headerMap.get(key);
                String item = String.format("%s: %s \r\n", key, value);
                header = header + item;
            }
            String response =  String.format("%s\r\n%s", header, body);
            return response;
        }


        public static byte[] index(Request request) {
            User current = Route.currentUser(request);

            if (current.role.equals(UserRole.guest )) {
                return redirect("/login");
            }

            ArrayList<Todo> todoList = TodoService.findByUserId(current.id);
            HashMap<String, Object> d = new HashMap<>();
            d.put("todos", todoList);
            d.put("currentuser", current.username);
            String body = SelfTemplate.render(d, "todo_index.ftl");

            HashMap<String, String> header = new HashMap<>();
            header.put("Content-Type", "text/html");

            String response = responseWithHeader(200, header, body);
            return response.getBytes(StandardCharsets.UTF_8);
        }

        public static byte[] redirect(String url) {
            String body = "";
            HashMap<String, String> header = new HashMap<>();
            header.put("Location", url);
            String response = responseWithHeader(302, header, body);
            return response.getBytes(StandardCharsets.UTF_8);
        }

        public static byte[] add(Request request) {
            User current = Route.currentUser(request);
            HashMap<String, String> data = request.form;
            TodoService.add(data, current.id);
            return redirect("/todo");
        }

        public static byte[] delete(Request request) {
            HashMap<String, String> data = request.query;
            Integer id = Integer.parseInt(data.get("id"));
            TodoService.delete(id);
            return redirect("/todo");
        }

        public static byte[] edit(Request request) {
            HashMap<String, String> data = request.query;
            Integer id = Integer.parseInt(data.get("id"));
            Todo todo = TodoService.findById(id);

            String body = Route.html("todo_edit.html");
            body = body.replace("{id}", todo.id.toString());
            body = body.replace("{content}", todo.content);

            HashMap<String, String> header = new HashMap<>();
            header.put("Content-Type", "text/html");
            String response = responseWithHeader(200, header, body);
            return response.getBytes(StandardCharsets.UTF_8);
        }

        public static byte[] update(Request request) {
            HashMap<String, String> data = request.form;
            Integer id = Integer.parseInt(data.get("id"));
            String content = data.get("content");
            TodoService.update(id, content);
            return redirect("/todo");
        }
}
