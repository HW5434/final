package com.kh.kh13fb.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertScheduleAddVO {
    private int concertScheduleNo;
    private int concertRequestNo;
    private ConcertScheduleVO concertSchedule;
    private List<Integer> actors;
    private String concertScheduleStart;
    private String concertScheduleEnd;
}