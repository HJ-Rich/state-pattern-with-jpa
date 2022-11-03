package com.example.statepatterwithjpa.coupon.domain.state;

import static com.example.statepatterwithjpa.coupon.domain.state.CouponAcceptedState.EXCEPTION_FROM_ACCEPTED;
import static com.example.statepatterwithjpa.coupon.domain.state.CouponFinishedState.EXCEPTION_FROM_FINISHED;
import static com.example.statepatterwithjpa.coupon.domain.state.CouponReadyState.EXCEPTION_FROM_READY;
import static com.example.statepatterwithjpa.coupon.domain.state.CouponRequestedState.EXCEPTION_FROM_REQUESTED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.statepatterwithjpa.coupon.domain.Coupon;
import com.example.statepatterwithjpa.coupon.domain.CouponStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@DisplayName("쿠폰 상태 변경 단위 테스트")
class CouponStateTest {

    @DisplayName("Ready 상태의 쿠폰은")
    @Nested
    class CouponReadyStateTest {
        private Coupon coupon;

        @BeforeEach
        void setup() {
            // given
            this.coupon = Coupon.builder()
                    .id(1L)
                    .sendMemberId(1L)
                    .receiveMemberId(null)
                    .title("쿠폰제목")
                    .description("쿠폰 메시지")
                    .couponState(new CouponReadyState())
                    .build();
        }

        @DisplayName("Ready 로 변경 시도시 예외")
        @Test
        void fail_from_ready_to_ready() {
            // when & then
            assertThatThrownBy(() -> coupon.changeStatus(CouponStatus.Ready))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_READY);
        }

        @DisplayName("Requested 로 변경 가능")
        @Test
        void success_from_ready_to_requested() {
            // when
            coupon.changeStatus(CouponStatus.Requested);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Requested);
        }

        @DisplayName("Accepted 로 변경 시도 시 예외")
        @Test
        void fail_from_ready_to_accepted() {
            // when & then
            assertThatThrownBy(() -> coupon.changeStatus(CouponStatus.Accepted))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_READY);
        }

        @DisplayName("Finished 로 변경 가능")
        @Test
        void fail_from_ready_to_finished() {
            // when
            coupon.changeStatus(CouponStatus.Finished);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Finished);
        }
    }

    @DisplayName("Requested 상태의 쿠폰은")
    @Nested
    class CouponRequestedStateTest {
        private Coupon coupon;

        @BeforeEach
        void setup() {
            // given
            this.coupon = Coupon.builder()
                    .id(1L)
                    .sendMemberId(1L)
                    .receiveMemberId(null)
                    .title("쿠폰제목")
                    .description("쿠폰 메시지")
                    .couponState(new CouponRequestedState())
                    .build();
        }

        @DisplayName("Ready 로 변경 가능")
        @Test
        void success_from_requested_to_ready() {
            // when
            coupon.changeStatus(CouponStatus.Ready);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Ready);
        }

        @DisplayName("Requested 로 변경 시도 시 예외")
        @Test
        void fail_from_requested_to_requested() {
            // when & then
            assertThatThrownBy(() -> coupon.changeStatus(CouponStatus.Requested))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_REQUESTED);
        }

        @DisplayName("Accepted 로 변경 가능")
        @Test
        void success_from_requested_to_accepted() {
            // when
            coupon.changeStatus(CouponStatus.Accepted);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Accepted);
        }

        @DisplayName("Finished 로 변경 시도 시 예외")
        @Test
        void fail_from_requested_to_finished() {
            // when & then
            assertThatThrownBy(() -> coupon.changeStatus(CouponStatus.Finished))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_REQUESTED);
        }
    }


    @DisplayName("Accepted 상태의 쿠폰은")
    @Nested
    class CouponAcceptedStateTest {
        private Coupon coupon;

        @BeforeEach
        void setup() {
            // given
            this.coupon = Coupon.builder()
                    .id(1L)
                    .sendMemberId(1L)
                    .receiveMemberId(null)
                    .title("쿠폰제목")
                    .description("쿠폰 메시지")
                    .couponState(new CouponAcceptedState())
                    .build();
        }

        @DisplayName("Ready 로 변경 가능")
        @Test
        void success_from_accepted_to_ready() {
            // when
            coupon.changeStatus(CouponStatus.Ready);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Ready);
        }

        @DisplayName("Requested 로 변경 시도 시 예외")
        @Test
        void fail_from_accepted_to_requested() {
            // when & then
            assertThatThrownBy(() -> coupon.changeStatus(CouponStatus.Requested))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_ACCEPTED);
        }

        @DisplayName("Accepted 로 변경 시도 시 예외")
        @Test
        void success_from_requested_to_accepted() {
            // when & then
            assertThatThrownBy(() -> coupon.changeStatus(CouponStatus.Accepted))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessageContaining(EXCEPTION_FROM_ACCEPTED);
        }

        @DisplayName("Finished 로 변경 가능")
        @Test
        void fail_from_requested_to_finished() {
            // when
            coupon.changeStatus(CouponStatus.Finished);

            // then
            assertThat(coupon.getCouponState().getCouponStatus()).isEqualTo(CouponStatus.Finished);
        }
    }

    @ParameterizedTest(name = "Finished 상태의 쿠폰은 어떤 상태로도 수정할 수 없다 : 변경 시도 상태 {0}")
    @EnumSource(CouponStatus.class)
    void finished_coupon_can_not_be_changed(CouponStatus couponStatus) {
        final var finishedCoupon = Coupon.builder()
                .id(1L)
                .sendMemberId(1L)
                .receiveMemberId(null)
                .title("쿠폰제목")
                .description("쿠폰 메시지")
                .couponState(new CouponFinishedState())
                .build();

        // when & then
        assertThatThrownBy(() -> finishedCoupon.changeStatus(couponStatus))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(EXCEPTION_FROM_FINISHED);
    }
}
