package cn.darkjrong.jpa.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/**
 * ID
 *
 * @author Rong.Jia
 * @date 2023/11/08
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class BaseId implements Serializable {

    private static final long serialVersionUID = -7955624612400157634L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseId baseId = (BaseId) o;
        return id != null && Objects.equals(id, baseId.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
