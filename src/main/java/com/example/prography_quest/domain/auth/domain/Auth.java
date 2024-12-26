package com.example.prography_quest.domain.auth.domain;

import com.example.prography_quest.domain.user.domain.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "auth")
public class Auth {
    @Id
    @Column(name = "auth_id")
    private Long id;
    private String Email;
    private String password;

    @Builder
    public Auth(Long id, String email, String password) {
        this.id = id;
        this.Email = email;
        this.password = password;
    }
}
