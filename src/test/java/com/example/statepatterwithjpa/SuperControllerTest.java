package com.example.statepatterwithjpa;

import static com.example.statepatterwithjpa.coupon.domain.state.CouponAcceptedState.EXCEPTION_FROM_ACCEPTED;
import static com.example.statepatterwithjpa.coupon.domain.state.CouponFinishedState.EXCEPTION_FROM_FINISHED;
import static com.example.statepatterwithjpa.coupon.domain.state.CouponReadyState.EXCEPTION_FROM_READY;
import static com.example.statepatterwithjpa.coupon.domain.state.CouponRequestedState.EXCEPTION_FROM_REQUESTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.statepatterwithjpa.coupon.domain.Coupon;
import com.example.statepatterwithjpa.coupon.domain.CouponStatus;
import com.example.statepatterwithjpa.coupon.domain.state.CouponAcceptedState;
import com.example.statepatterwithjpa.coupon.domain.state.CouponFinishedState;
import com.example.statepatterwithjpa.coupon.domain.state.CouponReadyState;
import com.example.statepatterwithjpa.coupon.domain.state.CouponRequestedState;
import com.example.statepatterwithjpa.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("쿠폰 상태 변경 통합 테스트")
@SpringBootTest
class SuperControllerTest {
    @Autowired
    private SuperController controller;

    @DisplayName("Ready 상태의 쿠폰은")
    @Nested
    class CouponReadyStateTest {
        private Member richard;
        private Member arthur;
        private Coupon readyCoupon;

        @BeforeEach
        void setUp() {
            richard = controller.member(Member.builder().name("리차드").build());
            arthur = controller.member(Member.builder().name("아서").build());

            final var readyStateCouponRequest = Coupon.builder()
                    .sendMemberId(richard.getId())
                    .title("쿠폰제목")
                    .description("쿠폰상세메시지")
                    .couponState(new CouponReadyState())
                    .build();

            readyCoupon = controller.couponOnMember(richard.getId(), readyStateCouponRequest);
        }

        @DisplayName("Ready 로 변경 시도시 예외")
        @Test
        void fail_from_ready_to_ready() {
            // when & then
            assertThatThrownBy(
                    () -> controller.couponStatusChange(richard.getId(), readyCoupon.getId(), CouponStatus.Ready))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_READY);
        }

        @DisplayName("Requested 로 변경 가능")
        @Test
        void success_from_ready_to_requested() {
            // when
            final var coupon = controller.couponStatusChange(
                    richard.getId(), readyCoupon.getId(), CouponStatus.Requested);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Requested);
        }

        @DisplayName("Accepted 로 변경 시도 시 예외")
        @Test
        void fail_from_ready_to_accepted() {
            // when & then
            assertThatThrownBy(
                    () -> controller.couponStatusChange(richard.getId(), readyCoupon.getId(), CouponStatus.Accepted))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_READY);
        }

        @DisplayName("Finished 로 변경 가능")
        @Test
        void fail_from_ready_to_finished() {
            // when
            final var coupon = controller.couponStatusChange(
                    richard.getId(), readyCoupon.getId(), CouponStatus.Finished);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Finished);
        }
    }

    @DisplayName("Requested 상태의 쿠폰은")
    @Nested
    class CouponRequestedStateTest {
        private Member richard;
        private Member arthur;
        private Coupon requestedCoupon;

        @BeforeEach
        void setUp() {
            richard = controller.member(Member.builder().name("리차드").build());
            arthur = controller.member(Member.builder().name("아서").build());

            final var readyStateCouponRequest = Coupon.builder()
                    .sendMemberId(richard.getId())
                    .title("쿠폰제목")
                    .description("쿠폰상세메시지")
                    .couponState(new CouponRequestedState())
                    .build();

            requestedCoupon = controller.couponOnMember(richard.getId(), readyStateCouponRequest);
        }

        @DisplayName("Ready 로 변경 가능")
        @Test
        void success_from_requested_to_ready() {
            // when
            final var coupon = controller.couponStatusChange(
                    richard.getId(), requestedCoupon.getId(), CouponStatus.Ready);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Ready);
        }

        @DisplayName("Requested 로 변경 시도 시 예외")
        @Test
        void fail_from_requested_to_requested() {
            // when & then
            assertThatThrownBy(
                    () -> controller.couponStatusChange(richard.getId(), requestedCoupon.getId(),
                            CouponStatus.Requested))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_REQUESTED);
        }

        @DisplayName("Accepted 로 변경 가능")
        @Test
        void success_from_requested_to_accepted() {
            // when
            final var coupon = controller.couponStatusChange(richard.getId(), requestedCoupon.getId(),
                    CouponStatus.Accepted);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Accepted);
        }

        @DisplayName("Finished 로 변경 시도 시 예외")
        @Test
        void fail_from_requested_to_finished() {
            // when & then
            assertThatThrownBy(() -> controller.couponStatusChange(richard.getId(), requestedCoupon.getId(),
                    CouponStatus.Finished))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_REQUESTED);
        }
    }

    @DisplayName("Accepted 상태의 쿠폰은")
    @Nested
    class CouponAcceptedStateTest {
        private Member richard;
        private Member arthur;
        private Coupon acceptedCoupon;

        @BeforeEach
        void setUp() {
            richard = controller.member(Member.builder().name("리차드").build());
            arthur = controller.member(Member.builder().name("아서").build());

            final var acceptedStateCouponRequest = Coupon.builder()
                    .sendMemberId(richard.getId())
                    .title("쿠폰제목")
                    .description("쿠폰상세메시지")
                    .couponState(new CouponAcceptedState())
                    .build();

            acceptedCoupon = controller.couponOnMember(richard.getId(), acceptedStateCouponRequest);
        }

        @DisplayName("Ready 로 변경 가능")
        @Test
        void success_from_accepted_to_ready() {
            // when
            final var coupon = controller.couponStatusChange(richard.getId(), acceptedCoupon.getId(),
                    CouponStatus.Ready);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Ready);
        }

        @DisplayName("Requested 로 변경 시도 시 예외")
        @Test
        void fail_from_accepted_to_requested() {
            // when & then
            assertThatThrownBy(() -> controller.couponStatusChange(richard.getId(), acceptedCoupon.getId(),
                    CouponStatus.Requested))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_ACCEPTED);
        }

        @DisplayName("Accepted 로 변경 시도 시 예외")
        @Test
        void success_from_requested_to_accepted() {
            // when & then
            assertThatThrownBy(
                    () -> controller.couponStatusChange(richard.getId(), acceptedCoupon.getId(), CouponStatus.Accepted))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_ACCEPTED);
        }

        @DisplayName("Finished 로 변경 가능")
        @Test
        void fail_from_requested_to_finished() {
            // when
            final var coupon = controller.couponStatusChange(richard.getId(), acceptedCoupon.getId(),
                    CouponStatus.Finished);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Finished);
        }
    }

    @ParameterizedTest(name = "Finished 상태의 쿠폰은 어떤 상태로도 수정할 수 없다 : 변경 시도 상태 {0}")
    @EnumSource(CouponStatus.class)
    void finished_coupon_can_not_be_changed(CouponStatus couponStatus) {
        final var richard = controller.member(Member.builder().name("리차드").build());
        final var arthur = controller.member(Member.builder().name("아서").build());

        final var finishedStateCouponRequest = Coupon.builder()
                .sendMemberId(richard.getId())
                .title("쿠폰제목")
                .description("쿠폰상세메시지")
                .couponState(new CouponFinishedState())
                .build();

        final var finishedCoupon = controller.couponOnMember(richard.getId(), finishedStateCouponRequest);

        // when & then
        assertThatThrownBy(() -> controller.couponStatusChange(richard.getId(), finishedCoupon.getId(), couponStatus))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(EXCEPTION_FROM_FINISHED);
    }
}
