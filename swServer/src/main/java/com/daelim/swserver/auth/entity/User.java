package com.daelim.swserver.auth.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class User {

    @Id
    private String userId;
    @Column(length = 10, nullable = false, columnDefinition = "TEXT")
    private String userName;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String userPassword;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String userEmail;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String userDeviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;

        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
