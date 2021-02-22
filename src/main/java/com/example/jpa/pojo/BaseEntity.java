package com.example.jpa.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@EqualsAndHashCode
@MappedSuperclass  // 属性都将映射到其子类的数据库字段中。
public class BaseEntity {
    @Column(name = "createtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @PrePersist
    protected void prePersist() {
        createTime = new Date();
    }
}
