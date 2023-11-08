package cn.darkjrong.jpa.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 * 公共属性父类
 *
 * @author Rong.Jia
 * @date 2023/01/31
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class IBase extends BaseId implements Serializable {

    private static final long serialVersionUID = 7373982631198174412L;

    /**
     * 添加人
     */
    @ApiModelProperty("添加人")
    private String createdUser;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    private String updatedUser;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        IBase iBase = (IBase) o;
        return getId() != null && Objects.equals(getId(), iBase.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
