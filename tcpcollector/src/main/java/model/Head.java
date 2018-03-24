
package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "transaction",
    "signature",
    "environment",
    "fields"
})
public class Head {

    @JsonProperty("transaction")
    private Integer transaction;
    @JsonProperty("signature")
    private Integer signature;
    @JsonProperty("environment")
    private Environment environment;
    @JsonProperty("fields")
    private List<Object> fields = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("transaction")
    public Integer getTransaction() {
        return transaction;
    }

    @JsonProperty("transaction")
    public void setTransaction(Integer transaction) {
        this.transaction = transaction;
    }

    @JsonProperty("signature")
    public Integer getSignature() {
        return signature;
    }

    @JsonProperty("signature")
    public void setSignature(Integer signature) {
        this.signature = signature;
    }

    @JsonProperty("environment")
    public Environment getEnvironment() {
        return environment;
    }

    @JsonProperty("environment")
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @JsonProperty("fields")
    public List<Object> getFields() {
        return fields;
    }

    @JsonProperty("fields")
    public void setFields(List<Object> fields) {
        this.fields = fields;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
