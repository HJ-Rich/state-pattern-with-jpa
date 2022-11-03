package com.example.statepatterwithjpa.coupon.repository;

import com.example.statepatterwithjpa.coupon.domain.CouponStatus;
import com.example.statepatterwithjpa.coupon.domain.state.CouponState;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class CouponStatusConverter implements AttributeConverter<CouponState, String> {
    @Override
    public String convertToDatabaseColumn(final CouponState attribute) {
        return attribute.getCouponStatus().name();
    }

    @Override
    public CouponState convertToEntityAttribute(final String dbData) {
        final var couponStatus = CouponStatus.from(dbData);

        return couponStatus.getConcrete();
    }
}
