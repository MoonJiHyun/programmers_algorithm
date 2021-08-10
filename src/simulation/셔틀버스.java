package simulation;

import java.util.*;

class 셔틀버스 {
    private int getMinute(String t) {
        String[] times = t.split(":");

        return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
    }

    private String getMinuteToString(int m) {
        StringBuilder sb = new StringBuilder();
        sb.append(m / 60 < 10 ? "0" + (m / 60) : m / 60);
        sb.append(":");
        sb.append(m % 60 < 10 ? "0" + (m % 60) : m % 60);
        return sb.toString();
    }

    public String solution(int n, int t, int m, String[] timetable) {
        int waitingCrewCnt = timetable.length;
        int currN = 1;
        int currM = 1;
        int currBusTime = getMinute("09:00");

        int[] crewMinutes = new int[waitingCrewCnt];

        for (int i = 0; i < waitingCrewCnt; i++) {
            crewMinutes[i] = getMinute(timetable[i]);
        }

        Arrays.sort(crewMinutes);


        for (int currCrewTime : crewMinutes) {
            // 1. 첫번째 크루보다 먼저 버스가 지나갈 경우
            while (currBusTime < currCrewTime) {
                if (currN == n) return getMinuteToString(currBusTime);
                currN++;
                currM = 1;
                currBusTime += t;
            }

            // 2. 자리가 있으면 현재 크루 타기
            if (currM < m) {
                currM++;
                continue;
            }

            // 3. 자리가 없으면

            // 3-1. 마지막 버스 운행이면 현재 크루보다 1분 일찍 와서 타기
            if (currN == n) return getMinuteToString(currCrewTime - 1);

            // 3-2. 버스 운행이 남아 있으면 다음 버스 타기
            currN++;
            currM = 1;
            currBusTime += t;
        }

        return getMinuteToString(currBusTime);
    }
}