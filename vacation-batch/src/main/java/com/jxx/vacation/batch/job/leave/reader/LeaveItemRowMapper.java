package com.jxx.vacation.batch.job.leave.reader;

import com.jxx.vacation.batch.job.leave.item.LeaveItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LeaveItemRowMapper implements RowMapper<LeaveItem> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LeaveItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        String memberPk = rs.getString("MEMBER_PK");
        boolean memberActive = rs.getBoolean("MEMBER_ACTIVE");
        float remainingLeave = rs.getFloat("REMAINING_LEAVE");
        long vacationId = rs.getLong("VACATION_ID");
        boolean deducted = rs.getBoolean("DEDUCTED");
        String vacationStatus = rs.getString("VACATION_STATUS");
        String vacationType = rs.getString("VACATION_TYPE");
        LocalDateTime startDateTime = LocalDateTime.parse(rs.getString("START_DATE_TIME"), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(rs.getString("END_DATE_TIME"), formatter);
        String companyId = rs.getString("COMPANY_ID");
        String departmentId = rs.getString("DEPARTMENT_ID");
        boolean orgActive = rs.getBoolean("ORG_ACTIVE");

        return new LeaveItem(memberPk,
                memberActive,
                remainingLeave,
                vacationId,
                deducted,
                vacationStatus,
                vacationType,
                startDateTime,
                endDateTime,
                companyId,
                departmentId,
                orgActive);
    }
}