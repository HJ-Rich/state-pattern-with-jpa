package com.example.statepatterwithjpa.coupon.domain;

import com.example.statepatterwithjpa.coupon.domain.state.CouponState;
import com.example.statepatterwithjpa.coupon.repository.CouponStatusConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Entity
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long sendMemberId;
    private Long receiveMemberId;
    private String title;
    private String description;
    @Convert(converter = CouponStatusConverter.class)
    private CouponState couponState;

    protected Coupon() {
    }

    @Builder
    public Coupon(final Long id, final Long sendMemberId, final Long receiveMemberId, final String title,
                  final String description,
                  final CouponState couponState) {
        this.id = id;
        this.sendMemberId = sendMemberId;
        this.receiveMemberId = receiveMemberId;
        this.title = title;
        this.description = description;
        this.couponState = couponState;
    }

    public void changeStatus(final CouponStatus couponStatus) {
        this.couponState = this.couponState.changeState(couponStatus);
    }
}
