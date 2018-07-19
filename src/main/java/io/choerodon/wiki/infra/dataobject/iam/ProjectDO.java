package io.choerodon.wiki.infra.dataobject.iam;

/**
 * Created by ernst on 2018/7/9.
 */
public class ProjectDO {

    private Long id;
    private String name;
    private Long organizationId;
    private String code;
    private Boolean enabled;

    public ProjectDO() {

    }

    public ProjectDO(Long id) {
        this.id = id;
    }

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

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
