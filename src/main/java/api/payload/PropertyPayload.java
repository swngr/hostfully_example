package api.payload;

import java.util.List;

public class PropertyPayload {

    String id;
    String alias;
    String countryCode;
    List<Object> createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<Object> getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(List<Object> createdAt) {
        this.createdAt = createdAt;
    }
}
