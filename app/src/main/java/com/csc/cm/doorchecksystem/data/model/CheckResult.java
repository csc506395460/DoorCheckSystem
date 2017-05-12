package com.csc.cm.doorchecksystem.data.model;

import java.util.List;

/**
 * Created by admin on 2017/2/8.
 */
public class CheckResult {

    public List<CheckInfo> list;
    public int signNum;
    public int totalNum;
    public int unSignNum;

    public class CheckInfo {
        public String cardNo;
        public String controlName;
        public String doorName;
        public String employeeCode;
        public int employeeId;
        public String employeeName;
        public String eventName;
        public long eventTime;

    }
}
