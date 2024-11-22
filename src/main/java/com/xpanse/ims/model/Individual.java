package com.xpanse.ims.model;

import lombok.Data;
import java.util.List;

@Data
public class Individual {
    private List<ContactPointsItem> contactPoints;
    private Name name;
}