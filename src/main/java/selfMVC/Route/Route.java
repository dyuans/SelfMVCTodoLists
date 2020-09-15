package selfMVC.Route;

import selfMVC.Request;
import selfMVC.Utils;
import selfMVC.models.Session;
import selfMVC.models.User;
import selfMVC.service.SessionService;
import selfMVC.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Function;

public class Route {
    public static HashMap<String, Function<Request, byte[]>> routeMapAll() {
        HashMap<String, Function<Request, byte[]>> map = new HashMap<>();
        // map.put("/doge.gif", Route::routeImage);
        // map.put("/doge1.jpg", Route::routeImage);
        // map.put("/doge2.gif", Route::routeImage);
        map.put("/static", Route::routeImage);


        map.putAll(RouteTodo.routeMap());
        map.putAll(RouteUser.routeMap());
        map.putAll(RoutePublic.routeMap());
        map.putAll(RouteAjaxTodo.routeMap());
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

    public static String html(String filename) {
        String dir = "templates";
        String path = dir + "/" + filename;
        Utils.log("html path: %s", path);
        byte[] body = new byte[1];
        // 读取文件
        // 如果想读取 image 文件下的文件, 就用 image/doge.gif
        try (InputStream is = Utils.fileStream(path)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String r = new String(body);
        return r;

    }

    public static User currentUser(Request request) {
         if (request.cookies.containsKey("session_id")) {
             String sessionId = request.cookies.get("session_id");
             Session session = SessionService.findById(sessionId);
             if (session == null) {
                 return UserService.guest();
             } else {
                 User u = UserService.findById(session.userId);
                 if (u == null) {
                     return UserService.guest();
                 } else {
                     return u;
                 }
             }
         } else {
             return UserService.guest();
         }
    }



    public static byte[] routeMessageAdd() {
        String body = "messageAdd";
        String header = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n";
        String response = header + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] routeLogin() {
        String body =  html("Utils.login.html");
        String response = "HTTP/1.1 200 very OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] routeImage(Request request) {
        String imageName = request.query.get("file");
        String dir = "static";
        String path = String.format("%s/%s", dir, imageName);
        Utils.log("routeImage: %s", path);
        // body

        String contentType = "";

        if(imageName.endsWith("css")) {
            contentType = "text/css";
        } else {
            contentType = "image/gif";
        }

        String header = String.format("HTTP/1.1 200 very OK\r\nContent-Type: %s;\r\n\r\n", contentType);
        byte[] body = new byte[1];
        // 读取文件
        // 如果想读取 image 文件下的文件, 就用 image/doge.gif
        try (InputStream is = Utils.fileStream(path)) {
            body = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] part1 = header.getBytes(StandardCharsets.UTF_8);
        byte[] response = new byte[part1.length + body.length];
        System.arraycopy(part1, 0, response, 0, part1.length);
        System.arraycopy(body, 0, response, part1.length, body.length);

        // 也可以用 ByteArrayOutputStream
        // ByteArrayOutputStream response2 = new ByteArrayOutputStream();
        // response2.write(header.getBytes());
        // response2.write(body);
        return response;
    }

    public static byte[] route404(Request request) {
        String body = "<html><body><h1>404</h1><br><img src='/doge2.gif'></body></html>";
        String response = "HTTP/1.1 404 NOT OK\r\nContent-Type: text/html;\r\n\r\n" + body;
        return response.getBytes(StandardCharsets.UTF_8);
    }
}
