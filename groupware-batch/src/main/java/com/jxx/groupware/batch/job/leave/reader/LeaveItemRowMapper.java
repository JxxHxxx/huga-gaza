package com.jxx.groupware.batch.job.leave.reader;

import com.jxx.groupware.batch.job.leave.item.LeaveItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LeaveItemRowMapper implements RowMapper<LeaveItem> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter nanoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    @Override
    public LeaveItem mapRow(ResultSet rs, int rowNum) throws SQLException {

        String memberPk = rs.getString("MEMBER_PK");
        LocalDateTime createTime = null;
        try {
            createTime = LocalDateTime.parse(rs.getString("CREATE_TIME"), nanoFormatter);
        } catch (DateTimeParseException e) {
            createTime = LocalDateTime.parse(rs.getString("CREATE_TIME"), formatter);
        }
        boolean memberActive = rs.getBoolean("MEMBER_ACTIVE");
        float totalLeave = rs.getFloat("TOTAL_LEAVE");
        float remainingLeave = rs.getFloat("REMAINING_LEAVE");
        String name = rs.getString("NAME");
        String memberId = rs.getString("MEMBER_ID");
        Integer experienceYears = rs.getInt("EXPERIENCE_YEARS");
        LocalDate enteredDate = LocalDate.parse(rs.getString("ENTERED_DATE"));
        long vacationId = rs.getLong("VACATION_ID");
        String deducted = rs.getString("LEAVE_DEDUCT");
        String vacationStatus = rs.getString("VACATION_STATUS");
        String vacationType = rs.getString("VACATION_TYPE");
        LocalDateTime startDateTime = LocalDateTime.parse(rs.getString("START_DATE_TIME"), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(rs.getString("END_DATE_TIME"), formatter);
        String companyId = rs.getString("COMPANY_ID");
        String departmentId = rs.getString("DEPARTMENT_ID");
        boolean orgActive = rs.getBoolean("ORG_ACTIVE");
        Float useLeaveValue = rs.getFloat("USE_LEAVE_VALUE");
        String lastDuration = rs.getString("LAST_DURATION");

        return new LeaveItem(memberPk,
                createTime,
                memberActive,
                totalLeave,
                remainingLeave,
                name,
                memberId,
                experienceYears,
                enteredDate,
                vacationId,
                deducted,
                vacationStatus,
                vacationType,
                startDateTime,
                endDateTime,
                companyId,
                departmentId,
                orgActive,
                useLeaveValue,
                lastDuration);
    }
}
