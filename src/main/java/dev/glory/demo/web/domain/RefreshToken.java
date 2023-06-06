package dev.glory.demo.web.domain;

import static jakarta.persistence.FetchType.LAZY;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import dev.glory.demo.web.domain.base.BaseEntity;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "user_refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    @Comment("refresh token")
    private String refreshToken;

    @Comment("토큰 만료일시")
    @Column(nullable = false)
    private LocalDateTime expireDt;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public RefreshToken(String refreshToken, LocalDateTime expireDt, User user) {
        this.refreshToken = refreshToken;
        this.expireDt = expireDt;
        this.user = user;
    }

    public void updateRefreshToken(String refreshToken, LocalDateTime expireDt) {
        this.refreshToken = refreshToken;
        this.expireDt = expireDt;
    }
}
