package com.example.statepatterwithjpa;

import com.example.statepatterwithjpa.coupon.domain.Coupon;
import com.example.statepatterwithjpa.coupon.domain.CouponStatus;
import com.example.statepatterwithjpa.coupon.domain.state.CouponRequestedState;
import com.example.statepatterwithjpa.coupon.repository.CouponRepository;
import com.example.statepatterwithjpa.member.domain.Member;
import com.example.statepatterwithjpa.member.repository.MemberRepository;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Transactional(readOnly = true)
@RestController
public class SuperController {
    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;

    public SuperController(final MemberRepository memberRepository, final CouponRepository couponRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
    }

    @Transactional
    @PostMapping("/api/members")
    public Member member(@RequestBody Member member) {
        return memberRepository.save(member);
    }

    @GetMapping("/api/members/{memberId}")
    public Member memberById(@PathVariable Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    @GetMapping("/api/members/{memberId}/coupons")
    public List<Coupon> couponsOnMember(@PathVariable Long memberId) {
        return couponRepository.findBySendMemberIdOrReceiveMemberId(memberId, memberId);
    }

    @Transactional
    @PostMapping("/api/members/{memberId}/coupons")
    public Coupon couponOnMember(@PathVariable Long memberId, @RequestBody Coupon coupon) {
        final var member = memberRepository.findById(memberId).orElseThrow();
        if (!Objects.equals(coupon.getSendMemberId(), member.getId())) {
            throw new IllegalArgumentException();
        }

        return couponRepository.save(coupon);
    }

    @Transactional
    @PatchMapping("/api/members/{memberId}/coupons/{couponId}")
    public Coupon couponStatusChange(@PathVariable Long memberId, @PathVariable Long couponId,
                                     @RequestBody CouponStatus couponStatus) {
        final var member = memberRepository.findById(memberId).orElseThrow();
        final var coupon = couponRepository.findByIdAndSendMemberId(couponId, member.getId()).orElseThrow();

        coupon.changeStatus(couponStatus);
        return coupon;
    }

    @Transactional
    @PostConstruct
    public void setup() {
        final var ?????? = memberRepository.save(Member.builder().name("??????").build());
        final var ????????? = memberRepository.save(Member.builder().name("?????????").build());
        final var ????????????_???????????? = couponRepository.save(
                Coupon.builder()
                        .title("?????? ?????????~")
                        .description("??????????????? ?????????")
                        .sendMemberId(?????????.getId())
                        .receiveMemberId(??????.getId())
                        .couponState(new CouponRequestedState())
                        .build()
        );

        log.info("?????? {}", ??????);
        log.info("????????? {}", ?????????);
        log.info("????????????_???????????? {}", ????????????_????????????);
    }
}
