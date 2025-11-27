package tp05.templateMethod2.template;

/**
 * SQLTemplateException
 */
public class SQLTemplateException extends RuntimeException {
    public SQLTemplateException(Exception e) {
        super(e);
    }

    public SQLTemplateException(String message) {
        super(message);
    }
}