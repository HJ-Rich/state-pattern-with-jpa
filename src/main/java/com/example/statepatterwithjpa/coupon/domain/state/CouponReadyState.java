package com.example.statepatterwithjpa.coupon.domain.state;

import com.example.statepatterwithjpa.coupon.domain.CouponStatus;
import java.util.List;

public class CouponReadyState implements CouponState {
    public static final String EXCEPTION_FROM_READY = "Ready 에선 Requested 또는 Finished로만 가능";

    @Override
    public CouponStatus getCouponStatus() {
        return CouponStatus.Ready;
    }

    @Override
    public CouponState changeState(final CouponStatus couponStatus) {
        if (notToRequestedNorFinished(couponStatus)) {
            throw new IllegalStateException(EXCEPTION_FROM_READY);
        }

        return couponStatus.getConcrete();
    }

    private boolean notToRequestedNorFinished(final CouponStatus couponStatus) {
        return !List.of(CouponStatus.Requested, CouponStatus.Finished).contains(couponStatus);
    }
}
