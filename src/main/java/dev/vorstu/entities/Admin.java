package dev.vorstu.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fio;
    @Column(nullable = false)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Admin(String fio, String phoneNumber, User user) {
        this.fio = fio;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }
}
