package com.example.statepatterwithjpa.coupon.domain.state;

import com.example.statepatterwithjpa.coupon.domain.CouponStatus;

public interface CouponState {
    CouponStatus getCouponStatus();

    CouponState changeState(CouponStatus couponStatus);
}
