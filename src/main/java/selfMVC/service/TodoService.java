package selfMVC.service;

import selfMVC.models.ModelFactory;
import selfMVC.models.Todo;

import java.util.ArrayList;
import java.util.HashMap;

// impot ../Utils


public class TodoService {
    public static Todo add(HashMap<String, String> form, Integer userId) {
        String content = form.get("content");
        Todo m = new Todo();
        m.content = content;
        m.completed = false;
        m.userId = userId;


        TodoService todoService = new TodoService();
        ArrayList<Todo> all = todoService.load();
        if (all.isEmpty()){
            m.id = 1;
        } else {
            Todo last = all.get(all.size() - 1);
            m.id = last.id + 1;
        }
        all.add(m);
        save(all);

        return m;
    }

    public static void save(ArrayList<Todo> list) {
        // 数据文件用的是 类名.txt
        String className = Todo.class.getSimpleName();
        // ModelFactory.save(className, list, new SerializerTodo());
        ModelFactory.save(className, list, model -> {
            ArrayList<String> lines = new ArrayList<>();
            lines.add(model.id.toString());
            lines.add(model.content);
            lines.add(model.completed.toString());
            lines.add(model.userId.toString());
            return lines;
        });
    }

    public  static ArrayList<Todo> load() {
        String className = Todo.class.getSimpleName();
        // ArrayList<Todo> rs = ModelFactory.load(className, 2, new DeserializerTodo());
        ArrayList<Todo> rs = ModelFactory.load(className, 4, modelData -> {
            Integer id = Integer.parseInt(modelData.get(0));
            String content = modelData.get(1);
            Boolean completed = Boolean.parseBoolean(modelData.get(2));
            Integer userId = Integer.parseInt(modelData.get(3));

            Todo m = new Todo();
            m.id = id;
            m.content = content;
            m.completed = completed;
            m.userId = userId;

            return m;
        });
        return rs;
    }

    public static String todoListShowString() {
        ArrayList<Todo> all = load();
        StringBuilder result = new StringBuilder();
        for (Todo m: all) {
            String s = String.format("%s: %s <br>", m.id, m.content);
            result.append(s);
        }
        return result.toString();
    }


    public static ArrayList<Todo> findByUserId(Integer userId) {
        ArrayList<Todo> ms = load();

        ArrayList<Todo> rs = new ArrayList<>();

        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.userId.equals(userId)) {
                rs.add(e);
            }
        }
        return rs;
    }

    public static String todoListHtml(Integer userId) {
        ArrayList<Todo> all = findByUserId(userId);
        StringBuilder result = new StringBuilder();
        for (Todo m: all) {
            String s = String.format(
                    "<h3>%s: %s " +
                            "<a href=\"/todo/edit?id=%s\">编辑</a> " +
                            "<a href=\"/todo/delete?id=%s\">删除</a></h3>",
                    m.id,
                    m.content,
                    m.id,
                    m.id
            );
            result.append(s);
        }
        return result.toString();
    }

    public static void delete(Integer id) {
        ArrayList<Todo> ms = load();

        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.id.equals(id)) {
                ms.remove(e);
            }
        }
        save(ms);
    }

    public static void update(Integer id, String content) {
        ArrayList<Todo> ms = load();

        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.id.equals(id)) {
                e.content = content;
            }
        }
        save(ms);
    }

    public static Todo findById(Integer id) {
        ArrayList<Todo> ms = load();

        for (int i = 0; i < ms.size(); i++) {
            Todo e = ms.get(i);
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;
    }
}