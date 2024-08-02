package com.b1.seatgrade.dto;

import com.b1.seatgrade.entity.SeatGradeType;
import jakarta.validation.constraints.NotNull;

public record SeatGradeAddRequestDto(
        @NotNull(message = "회차 정보가 누락되었습니다.")
        Long roundId,

        @NotNull(message = "좌석 등급이 누락되었습니다.")
        SeatGradeType seatGradeType,

        @NotNull(message = "좌석이 누락되었습니다.")
        Long seatId,

        @NotNull(message = "가격이 누락되었습니다.")
        Integer price
) {

}
