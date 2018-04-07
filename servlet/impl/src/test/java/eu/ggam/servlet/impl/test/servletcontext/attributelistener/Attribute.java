package eu.ggam.servlet.impl.test.servletcontext.attributelistener;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.value);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attribute other = (Attribute) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    

    
}
