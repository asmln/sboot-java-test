package asmln.test.sboot.controllers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Freemarker template example
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    private Configuration freemarkerConfig;
    private Configuration freemarkerStrConfig;

    private AtomicInteger count = new AtomicInteger(0);

    public TemplateController(@Autowired @Qualifier("freemarkerConfig") Configuration freemarkerConfig,
                              @Autowired @Qualifier("freemarkerStrConfig") Configuration freemarkerStrConfig) {
        this.freemarkerConfig = freemarkerConfig;
        this.freemarkerStrConfig = freemarkerStrConfig;
    }

    @RequestMapping(value = "/html", method = RequestMethod.GET, produces = "text/html")
    @ResponseBody
    public String html(@RequestParam(name = "user", defaultValue = "comrade") String userName) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<>();
        root.put("userName", userName);
        root.put("count", count.getAndAdd(1));
        Template tmpl = this.freemarkerConfig.getTemplate("html.ftl");
        Writer writer = new StringWriter();
        tmpl.process(root, writer);
        return writer.toString();
    }

    @RequestMapping(value = "/text", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String text(@RequestParam(name = "user", defaultValue = "comrade") String userName) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<>();
        root.put("userName", userName);
        root.put("count", count.getAndAdd(1));
        Template tmpl = this.freemarkerConfig.getTemplate("text.ftl");
        Writer writer = new StringWriter();
        tmpl.process(root, writer);
        return writer.toString();
    }

    @RequestMapping(value = "/text-import", method = RequestMethod.GET, produces = "text/plain")
    @ResponseBody
    public String textWithImport(@RequestParam(name = "user", defaultValue = "comrade") String userName) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<>();
        root.put("userName", userName);
        root.put("count", count.getAndAdd(1));
        Template tmpl = this.freemarkerStrConfig.getTemplate("text");
        Writer writer = new StringWriter();
        tmpl.process(root, writer);
        return writer.toString();
    }

    @RequestMapping(value = "/html-str", method = RequestMethod.GET, produces = "text/html")
    @ResponseBody
    public String htmlFromString(@RequestParam(name = "user", defaultValue = "comrade") String userName) throws IOException, TemplateException {
        Map<String, Object> root = new HashMap<>();
        root.put("userName", userName);
        root.put("count", count.getAndAdd(1));
        String templateStr =
            "<html>" +
                "<head>" +
                    "<title>HTML from String</title>" +
                    "<style type=\"text/css\">${css}</style>" +
                "</head>" +
                "<body>" +
                "<h1>Freemarker String HTML</h1>" +
                "<div>Hello ${userName}!</div>" +
                "<div>Count: ${count}</div>" +
                "</body>" +
            "</html>";
        Template tmpl = new Template("name", new StringReader(templateStr),
                this.freemarkerConfig);
        Writer writer = new StringWriter();
        String cssStr = "h1 {color: #66161A;} div {margin-left: 10px;}";
        root.put("css", cssStr);
        tmpl.process(root, writer);
        return writer.toString();
    }

}
