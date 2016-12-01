package asmln.test.sboot.data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;

/**
 * Template for Freemarker
 */
@Entity
public class Template {
    @Id
    public String name;

    public String content;

    public Calendar lastModified = Calendar.getInstance();

    public Template() {
    }

    public Template(String name, String text, Calendar lastModified) {
        this.name = name;
        this.content = text;
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return String.format(
                "Template[name=%s, text='%s']",
                name, content);
    }
}
