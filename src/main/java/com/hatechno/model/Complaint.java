package com.hatechno.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "complaints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String status; // PENDING, RESOLVED

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
