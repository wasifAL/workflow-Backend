package com.wsf.workflow.entity;

import com.wsf.workflow.entity.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User extends BaseEntity<Long> {

    @NotBlank(message = "Username is required")
    @Column(unique = true, nullable = false)
    private String username;

    @Nullable
    @NotBlank(message = "Password is required")
    private String password;

    @Email
    @NotEmpty(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    @Nullable
    private String role;
}
