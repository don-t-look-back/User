package com.example.dlbuser.domains.member.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT", nullable = false, unique = true)
    private Long id;

    private String memberId;

    private String memberPassword;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;


    public void updatePassword(PasswordEncoder passwordEncoder){

        this.memberPassword = passwordEncoder.encode("1234");
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password){

        this.memberPassword = passwordEncoder.encode(password);
    }

    public void updateDeletedAt(){

        this.deletedAt = LocalDateTime.now();
    }

}
