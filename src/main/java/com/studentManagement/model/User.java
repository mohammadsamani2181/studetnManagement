package com.studentManagement.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "studentM_User")
public class User extends BaseEntity implements UserDetails {
    @Column(name = "sso_id", nullable = false)
    private Long ssoId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false,unique = true)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

//    @Column(name = "password", length = 255)
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "studentM_school_user_roles")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<SchoolRole> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (SchoolRole role : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
