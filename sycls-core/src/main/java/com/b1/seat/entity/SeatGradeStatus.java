package com.b1.seat.entity;

import com.b1.exception.customexception.SeatGradeAlreadySoldOutException;
import com.b1.exception.errorcode.SeatGradeErrorCode;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Seat Grade Status")
@Getter
@RequiredArgsConstructor
public enum SeatGradeStatus {
    ENABLE("ENABLE"),
    DISABLE("DISABLE"),
    ;
    private final String value;

    public static void checkEnable(Set<SeatGrade> seatGrades) {
        seatGrades.forEach(
                sg -> {
                    if (sg.getStatus().equals(DISABLE)) {
                        log.error("이미 매진된 좌석 | request {}", sg.getStatus());
                        throw new SeatGradeAlreadySoldOutException(
                                SeatGradeErrorCode.SEAT_GRADE_ALREADY_SOLD_OUT);
                    }
                });
    }

}