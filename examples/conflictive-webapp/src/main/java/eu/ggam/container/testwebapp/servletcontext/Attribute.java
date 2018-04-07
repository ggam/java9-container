package eu.ggam.container.testwebapp.servletcontext;

/**
 *
 * @author Guillermo González de Agüero
 */
public class Attribute {

    private String value;

    public Attribute(String value) {
        this.value = value;
    }

    public void addValue(String addedValue) {
        this.value += addedValue;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

}
