package com.xpanse.ims.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CLSSQSEvent {
    private String version;
    private String id;
    @JsonProperty("detail-type")
    private String detailType;
    private String source;
    private String account;
    private String time;
    private String region;
    private CLSEvent detail;

}
