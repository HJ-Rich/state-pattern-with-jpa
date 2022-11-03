package com.example.statepatterwithjpa.coupon.repository;


import com.example.statepatterwithjpa.coupon.domain.Coupon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface CouponRepository extends Repository<Coupon, Long> {
    Coupon save(Coupon coupon);

    Optional<Coupon> findById(Long id);

    List<Coupon> findBySendMemberIdOrReceiveMemberId(Long memberId, Long memberId2);

    Optional<Coupon> findByIdAndSendMemberId(Long couponId, Long id);
}
