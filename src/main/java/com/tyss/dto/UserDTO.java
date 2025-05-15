package com.tyss.dto;

import com.tyss.entity.User;
import com.tyss.enums.Role;
import com.tyss.enums.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private Status status;
    private Role role;
    private Double userTotalScore;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.role = user.getRole();
        this.userTotalScore = user.getUserTotalScore();
    }
}

