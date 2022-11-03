package com.example.statepatterwithjpa.coupon.domain.state;

import com.example.statepatterwithjpa.coupon.domain.CouponStatus;
import java.util.List;

public class CouponRequestedState implements CouponState {
    public static final String EXCEPTION_FROM_REQUESTED = "Requested에선 Ready또는 Accepted로만 가능";

    @Override
    public CouponStatus getCouponStatus() {
        return CouponStatus.Requested;
    }

    @Override
    public CouponState changeState(final CouponStatus couponStatus) {
        if (notToReadyNorAccepted(couponStatus)) {
            throw new IllegalStateException(EXCEPTION_FROM_REQUESTED);
        }

        return couponStatus.getConcrete();
    }

    private boolean notToReadyNorAccepted(final CouponStatus couponStatus) {
        return !List.of(CouponStatus.Ready, CouponStatus.Accepted).contains(couponStatus);
    }
}
