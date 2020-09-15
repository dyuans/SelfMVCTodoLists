package selfMVC;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;

public class SelfTemplate {
    static Configuration config;
    //
    static {
        // static 里面的东西只会被初始化一次
        config = new Configuration(
                Configuration.VERSION_2_3_28);

        String resource = String.format("%s.class", Utils.class.getSimpleName());
        Utils.log("resource %s", resource);
        Utils.log("resource path %s", Utils.class.getResource(""));
        var res = Utils.class.getResource(resource);
        if (res != null && res.toString().startsWith("jar:")) {
            config.setClassForTemplateLoading(Utils.class, "/templates");
        } else {
            try {
                File f = new File("build/resources/main/templates");
                config.setDirectoryForTemplateLoading(f);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        config.setDefaultEncoding("utf-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(false);
        config.setWrapUncheckedExceptions(true);
    }

    public static String render(Object data, String templateFileName) {
        Template template;
        try {
            template = config.getTemplate(templateFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(result);
        try {
            template.process(data, writer);
        } catch (TemplateException | IOException e) {
            String messsage = String.format("模板 process 失败 <%s> error<%s>", data, e);
            throw new RuntimeException(messsage, e);
        }
        return result.toString();
    }
}
