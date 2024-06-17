package com.example.dlbuser.domains.memberToken.entity;

import com.example.dlbuser.domains.memberToken.entity.constant.RemainingTokenTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Entity
@Table(name = "member_token")
@Getter
@Builder
@AllArgsConstructor @NoArgsConstructor
public class MemberToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String refreshToken;

    private String accessToken;

    private LocalDateTime tokenExpirationTime;

    private Long memberId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;


    public static MemberToken create(String accessToken, String refreshToken, LocalDateTime tokenExpiredTime, Long memberId) {
        final MemberToken memberToken = MemberToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenExpirationTime(tokenExpiredTime)
                .memberId(memberId)
                .build();

        return memberToken;
    }

    public void updateRefreshTokenExpireTime(LocalDateTime now, RemainingTokenTime remainingTokenTime) {
        final long hours = ChronoUnit.HOURS.between(now, tokenExpirationTime);
        if (hours <= remainingTokenTime.getRemainingTime()) {
            updateTokenExpireTime(now.plusWeeks(2));
        }
    }

    public void updateTokenExpireTime(LocalDateTime tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public void expire(LocalDateTime now) {
        if (tokenExpirationTime.isAfter(now)) {
            this.tokenExpirationTime = now;
        }
    }


}
