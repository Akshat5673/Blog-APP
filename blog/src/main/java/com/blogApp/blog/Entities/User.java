package com.blogApp.blog.Entities;

import com.blogApp.blog.Enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name", nullable = false, length = 100,unique = true)
    private String name;

    @Column(name = "user_email", nullable = false, length = 100,unique = true)
    private String userEmail;

    @Column(name = "user_password", nullable = false, length = 100,unique = true)
    private String password;

    @Column(name = "about", length = 300)
    private String about;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role",
//            joinColumns = @JoinColumn(name ="user_id",referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
//    )
//    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.userEmail;
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
