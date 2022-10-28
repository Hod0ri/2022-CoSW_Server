package com.daelim.swserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    private String userId;
    private String userName;
    private String userPassword;

    @OneToMany
    @JoinColumn(name = "user_device_id")
    private List<Alarm> userDevice;

}
