
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
    "time",
    "no",
    "vals"
})
public class Datum {

    @JsonProperty("time")
    private String time;
    @JsonProperty("no")
    private Integer no;
    @JsonProperty("vals")
    private List<Double> vals = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonProperty("no")
    public Integer getNo() {
        return no;
    }

    @JsonProperty("no")
    public void setNo(Integer no) {
        this.no = no;
    }

    @JsonProperty("vals")
    public List<Double> getVals() {
        return vals;
    }

    @JsonProperty("vals")
    public void setVals(List<Double> vals) {
        this.vals = vals;
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
