package org.authentication.two_factor_auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    private String secretKey;

    public User(String username, String password, String secretKey) {
        this.username = username;
        this.password = password;
        this.secretKey = secretKey;
    }
}
