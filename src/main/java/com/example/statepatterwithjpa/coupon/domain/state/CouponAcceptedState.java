package com.example.statepatterwithjpa.coupon.domain.state;

import com.example.statepatterwithjpa.coupon.domain.CouponStatus;
import java.util.List;

public class CouponAcceptedState implements CouponState {
    public static final String EXCEPTION_FROM_ACCEPTED = "Accepted에선 Ready 또는 Finished로만 가능";

    @Override
    public CouponStatus getCouponStatus() {
        return CouponStatus.Accepted;
    }

    @Override
    public CouponState changeState(final CouponStatus couponStatus) {
        if (notToReadyNorFinished(couponStatus)) {
            throw new IllegalStateException(EXCEPTION_FROM_ACCEPTED);
        }

        return couponStatus.getConcrete();
    }

    private boolean notToReadyNorFinished(final CouponStatus couponStatus) {
        return !List.of(CouponStatus.Ready, CouponStatus.Finished).contains(couponStatus);
    }
}
