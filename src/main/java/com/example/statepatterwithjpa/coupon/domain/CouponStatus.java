package com.example.statepatterwithjpa.coupon.domain;

import com.example.statepatterwithjpa.coupon.domain.state.CouponAcceptedState;
import com.example.statepatterwithjpa.coupon.domain.state.CouponFinishedState;
import com.example.statepatterwithjpa.coupon.domain.state.CouponReadyState;
import com.example.statepatterwithjpa.coupon.domain.state.CouponRequestedState;
import com.example.statepatterwithjpa.coupon.domain.state.CouponState;
import java.util.Arrays;
import java.util.function.Supplier;

public enum CouponStatus {
    Ready(CouponReadyState::new),
    Requested(CouponRequestedState::new),
    Accepted(CouponAcceptedState::new),
    Finished(CouponFinishedState::new),
    ;

    private final Supplier<CouponState> couponStateSupplier;

    CouponStatus(final Supplier<CouponState> couponStateSupplier) {
        this.couponStateSupplier = couponStateSupplier;
    }

    public static CouponStatus from(final String dbData) {
        return Arrays.stream(CouponStatus.values())
                .filter(value -> value.name().equalsIgnoreCase(dbData))
                .findAny()
                .orElseThrow();
    }

    public CouponState getConcrete() {
        return couponStateSupplier.get();
    }
}
