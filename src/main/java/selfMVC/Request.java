package selfMVC;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Request {
// header, body
    // header 里面又分请求行, 和其他部分
    // 请求行里面又分  method(Get, Post), path
    // path 里面可以带参数
    // Body 里面也可以带参数

    public String rawData;
    public String method;
    public String path;
    public String body;
    public HashMap<String, String> query;
    public HashMap<String, String> form;
    public HashMap<String, String>  headers;
    public HashMap<String, String> cookies;

    public Request(String rawRequest) {
        this.rawData = rawRequest;
        String[] parts = rawRequest.split("\r\n\r\n", 2);
        this.body = parts[1];
        String headers = parts[0];
        this.addHeaders(headers);

        String[] lines = headers.split("\r\n");
        String requestLine = lines[0];
        String[] requestLineData = requestLine.split(" ");
        this.method = requestLineData[0];

        this.parsePath(requestLineData[1]);
        this.parseForm(body);

        log("path query: %s", this.query);
    }

    private void addHeaders(String headerString) {
        this.headers = new HashMap<>();
        String[] lines = headerString.split("\r\n");
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];
            String[] kv = line.split(":", 2);
            this.headers.put(kv[0].strip(), kv[1].strip());
        }

        if(this.headers.containsKey("Cookie")) {
            this.cookies = new HashMap<>();
            String cookieString = this.headers.get("Cookie");
            String args[] = cookieString.split(";");
            for (String kvString: args) {
                String[] kv = kvString.split("=", 2);
                if (kv.length >= 2) {
                    this.cookies.put(kv[0].strip(), kv[1].strip());
                } else {
                    this.cookies.put(kv[0].strip(), kv[0].strip());
                }
            }
        } else {
            this.cookies = new HashMap<>();
        }
    }

    private static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    private void parsePath(String path) {
        // path = URLDecoder.decode(path, StandardCharsets.UTF_8);
        Integer index = path.indexOf("?");

        if (index.equals(-1)) {
            this.path = path;
            this.query = null;
        } else {
            this.path = path.substring(0, index);
            String queryString = path.substring(index + 1);
            log("queryString: %s", queryString);
            String[] args = queryString.split("&");
            this.query = new HashMap<>();
            for (String e: args) {
                // author=
                log("e: %s", e);
                String[] kv = e.split("=", 2);
                String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                String v = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                log("k: <%s>, v: <%s>", k, v);
                this.query.put(k, v);
            }

            // 等价于下面
            // for (int i = 0; i < args.length; i++) {
            //     String e = args[i];
            // }
        }
    }

    private void parseForm(String body) {
        // ""
        // "   "
        body = body.strip();
        String contentType = this.headers.get("Content-Type");
        if (contentType == null) {
            this.form = new HashMap<>();
        } else if (contentType.equals("application/x-www-form-urlencoded")) {
            if (body.length() > 0) {
                // author=ddd&message=ddd
                String formString = body;
                log("queryString: %s", formString);
                String[] args = formString.split("&");
                this.form = new HashMap<>();
                for (String e: args) {
                    // author=
                    log("e: %s", e);
                    String[] kv = e.split("=", 2);
                    String k = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                    String v = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                    log("k: <%s>, v: <%s>", k, v);
                    this.form.put(k, v);
                }
            }
        } else if (contentType.equals("application/json")) {
            this.form = new HashMap<>();
        } else {
            this.form = new HashMap<>();
        }
    }
}
