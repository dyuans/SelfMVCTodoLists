package selfMVC.Route;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import selfMVC.Request;
import selfMVC.models.Todo;
import selfMVC.models.User;
import selfMVC.service.TodoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;


public class RouteAjaxTodo {
        public static HashMap<String, Function<Request, byte[]>> routeMap() {
            HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
            map.put("/ajax/todo", RouteAjaxTodo::index);
            map.put("/ajax/todo/add", RouteAjaxTodo::add);
            map.put("/ajax/todo/all", RouteAjaxTodo::all);

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


        public static byte[] all(Request request) {
            HashMap<String, String> headers = new HashMap<>();
            ArrayList<Todo> todoList = TodoService.load();
            headers.put("Content-Type", "application/json");
            String body = JSON.toJSONString(todoList);

            String response = Route.responseWithHeader(200, headers, body);
            return response.getBytes(StandardCharsets.UTF_8);
        }


        public static byte[] index(Request request) {
            String body = Route.html("ajax_todo.html");
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
            String jsonString = request.body;
            JSONObject jsonForm = JSONObject.parseObject(jsonString);
            String content = jsonForm.getString("content");

            HashMap<String, String> form = new HashMap<>();
            form.put("content", content);
            Todo todo = TodoService.add(form, current.id);

            HashMap<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            String body = JSON.toJSONString(todo);

            String response = Route.responseWithHeader(200, headers, body);
            return response.getBytes(StandardCharsets.UTF_8);
        }

        // public static byte[] delete(Request request) {
        //     HashMap<String, String> data = request.query;
        //     Integer id = Integer.parseInt(data.get("id"));
        //     TodoService.delete(id);
        //     return redirect("/todo");
        // }
        //
        // public static byte[] edit(Request request) {
        //     HashMap<String, String> data = request.query;
        //     Integer id = Integer.parseInt(data.get("id"));
        //     Todo todo = TodoService.findById(id);
        //
        //     String body = Route.html("todo_edit.html");
        //     body = body.replace("{id}", todo.id.toString());
        //     body = body.replace("{content}", todo.content);
        //
        //     HashMap<String, String> header = new HashMap<>();
        //     header.put("Content-Type", "text/html");
        //     String response = responseWithHeader(200, header, body);
        //     return response.getBytes(StandardCharsets.UTF_8);
        // }
        //
        // public static byte[] update(Request request) {
        //     HashMap<String, String> data = request.form;
        //     Integer id = Integer.parseInt(data.get("id"));
        //     String content = data.get("content");
        //     TodoService.update(id, content);
        //     return redirect("/todo");
        // }
}
