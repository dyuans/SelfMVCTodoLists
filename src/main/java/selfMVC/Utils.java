package selfMVC;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Utils {
    public static void log(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

    public static void save(String path, String data) {
        try (FileOutputStream out = new FileOutputStream(path)) {
            out.write(data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            String s = String.format("Save file <%s>, error: <%s>", path, e);
            throw new RuntimeException(s);
        }
    }

    public static String load(String path) {
        try (FileInputStream is = new FileInputStream(path)) {
            String content = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return content;
        } catch (IOException e) {
            String s = String.format("load file <%s>, error: <%s>", path, e);
            throw new RuntimeException(s);
        }
    }

    public static void main(String[] args) {
        String filename = "a.txt";
        String content = "1111";
        save(filename, content);
        String r = load(filename);
        log("a.txt: <%s>", r);
    }

    public static InputStream fileStream(String path) throws FileNotFoundException {
        String resource = String.format("%s.class", Utils.class.getSimpleName());
        Utils.log("resource %s", resource);
        Utils.log("resource path %s", Utils.class.getResource(""));
        var res = Utils.class.getResource(resource);
        if (res != null && res.toString().startsWith("jar:")) {
            // 打包后, templates 放在 jar 包的根目录下, 要加 / 才能取到
            // 不加 / 就是从 类 的当前包目录下取
            path = String.format("/%s", path);
            InputStream is = Utils.class.getResourceAsStream(path);
            if (is == null) {
                throw new FileNotFoundException(String.format("在 jar 里面找不到 %s", path));
            } else {
                return is;
            }
        } else {
            path = String.format("build/resources/main/%s", path);
            return new FileInputStream(path);
        }
    }
}
