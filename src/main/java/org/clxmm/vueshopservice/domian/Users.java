package org.clxmm.vueshopservice.domian;

import lombok.Data;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author clx
 * @date 2020-08-02 16:37
 */
@Table(name = "user")
@Data
public class Users extends Domain {

    private String roleName;

    private String username;

    private Date createTime;

    private Long mobile;

    private String email;

    private Integer state;


    @Transient
    private Boolean mgState = false;


    public Boolean getMgState() {
        return mgState;
    }

    public void setMgState() {
        this.mgState = ((state == 1) ? true : false);
    }
}
