package com.example.jpa.pojo;

import com.example.jpa.enums.UserStatusConverter;
import com.example.jpa.enums.UserStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
@Data
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "birthday")
    private String birthday;
    // getter,setter

    @Column(name = "updatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "status")
    @Convert(converter = UserStatusConverter.class)
    private UserStatusEnum status;

    @PrePersist
    protected void prePersist() {
        Date updateTime = new Date();
        this.updateTime = updateTime;
        super.prePersist();
    }
}
