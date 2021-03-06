package io.choerodon.wiki.domain.application.entity.iam;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Zenger on 2018/7/19.
 */
public class RoleE {

    private Long id;
    private String name;
    private String code;
    private String description;
    private String level;
    private Boolean enabled;
    private List<LabelE> labels;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<LabelE> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelE> labels) {
        this.labels = labels;
    }
}
