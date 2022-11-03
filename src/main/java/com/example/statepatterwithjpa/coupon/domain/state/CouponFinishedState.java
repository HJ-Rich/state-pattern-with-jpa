package com.example.statepatterwithjpa.coupon.domain.state;

import com.example.statepatterwithjpa.coupon.domain.CouponStatus;

public class CouponFinishedState implements CouponState {
    public static final String EXCEPTION_FROM_FINISHED = "Finished에선 쿠폰 상태 변경 불가";

    @Override
    public CouponStatus getCouponStatus() {
        return CouponStatus.Finished;
    }

    @Override
    public CouponState changeState(final CouponStatus couponStatus) {
        throw new IllegalStateException(EXCEPTION_FROM_FINISHED);
    }
}
