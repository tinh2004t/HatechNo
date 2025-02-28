package com.hatechno.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
	@OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
	private Set<Notification> notifications;

    @Id
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String role; // ADMIN, RESIDENT

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Complaint> complaints;
}
