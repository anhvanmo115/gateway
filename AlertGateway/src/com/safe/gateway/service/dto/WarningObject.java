/* 
* Copyright 2017 Viettel Telecom. All rights reserved. 
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 
 */
package com.safe.gateway.service.dto;

import java.util.List;

/**
 *
 * @author: AnTV
 * @version: 1.0
 * @since: 19-05-2017
 */
public class WarningObject {

    private Long warningId;
    /**
     * 1- Cảnh báo 2- Mất kết nối 3 bình thường
     */
    private int state;
    //Thời điểm gửi tin về
    private String timeWarning;
    private int warningType;

    private Long tblDeviceGid;
    private String tblDeviceName;
    private String tblDeviceMobile;
    private String tblDeviceCodeDevice;
    private String tblDeviceIMEI;
    private String tblDeviceAddress;
    private Long tblDeviceIdSetlement;

    private String tblSettlementsName;
    private String tblSettlementsManagerBuilding;
    private String tblSettlementsMobileBuilding;
    private String tblSettlementsNameManager;
    private String tblSettlementsMobileManager;
    private String tblSettlementsAddress;
    private Long tblSettlementsDepid;
    private String tblSettlementsLat;
    private String tblSettlementsLong;

    private String deptName;
    private String deptTelephone;

    private String userName;
    private String usersFullName;
    private String usersCellphone;

    private String tblTmpDeviceStatusPortStatus;
    //Tổng Số tổ PCCC phụ trách tòa nhà - truy vấn từ bảng tbl_rescue_team
    private int rescueTeamNumber;
    //Tổng Số gười trong tất cả các tổ phụ trách tòa nhà - truy vấn từ tbl_rescue_team và tbl_rescue_team_member
    private int rescueTeamMemberNumber;
    //Tổng Số loại công cụ dụng cụ có trong tòa nhà  - truy vấn từ bảng tbl_tool, theo trường id_settlements
    private int toolNumber;
    //Tổng Số loại công cụ dụng cụ có trong tòa nhà  - truy vấn từ bảng tbl_tool, theo trường tool_type và id_settlements
    private int toolTypeNumber;
    //loại hình kinh doanh - truy vấn từ bảng tbl_settlements (field_work) và mst_division (lấy dvs_name thông qua dvs_value) với dvs_group_cd=017
    private String fieldWork;

    private List<WarningUser> listWarningUser;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTimeWarning() {
        return timeWarning;
    }

    public void setTimeWarning(String timeWarning) {
        this.timeWarning = timeWarning;
    }

    public Long getTblDeviceGid() {
        return tblDeviceGid;
    }

    public void setTblDeviceGid(Long tblDeviceGid) {
        this.tblDeviceGid = tblDeviceGid;
    }

    public String getTblDeviceName() {
        return tblDeviceName;
    }

    public void setTblDeviceName(String tblDeviceName) {
        this.tblDeviceName = tblDeviceName;
    }

    public String getTblDeviceMobile() {
        return tblDeviceMobile;
    }

    public void setTblDeviceMobile(String tblDeviceMobile) {
        this.tblDeviceMobile = tblDeviceMobile;
    }

    public String getTblDeviceCodeDevice() {
        return tblDeviceCodeDevice;
    }

    public void setTblDeviceCodeDevice(String tblDeviceCodeDevice) {
        this.tblDeviceCodeDevice = tblDeviceCodeDevice;
    }

    public String getTblDeviceIMEI() {
        return tblDeviceIMEI;
    }

    public void setTblDeviceIMEI(String tblDeviceIMEI) {
        this.tblDeviceIMEI = tblDeviceIMEI;
    }

    public Long getTblDeviceIdSetlement() {
        return tblDeviceIdSetlement;
    }

    public void setTblDeviceIdSetlement(Long tblDeviceIdSetlement) {
        this.tblDeviceIdSetlement = tblDeviceIdSetlement;
    }

    public String getTblSettlementsName() {
        return tblSettlementsName;
    }

    public void setTblSettlementsName(String tblSettlementsName) {
        this.tblSettlementsName = tblSettlementsName;
    }

    public String getTblSettlementsMobileBuilding() {
        return tblSettlementsMobileBuilding;
    }

    public void setTblSettlementsMobileBuilding(String tblSettlementsMobileBuilding) {
        this.tblSettlementsMobileBuilding = tblSettlementsMobileBuilding;
    }

    public String getTblSettlementsNameManager() {
        return tblSettlementsNameManager;
    }

    public void setTblSettlementsNameManager(String tblSettlementsNameManager) {
        this.tblSettlementsNameManager = tblSettlementsNameManager;
    }

    public String getTblSettlementsMobileManager() {
        return tblSettlementsMobileManager;
    }

    public void setTblSettlementsMobileManager(String tblSettlementsMobileManager) {
        this.tblSettlementsMobileManager = tblSettlementsMobileManager;
    }

    public String getTblSettlementsAddress() {
        return tblSettlementsAddress;
    }

    public void setTblSettlementsAddress(String tblSettlementsAddress) {
        this.tblSettlementsAddress = tblSettlementsAddress;
    }

    public Long getTblSettlementsDepid() {
        return tblSettlementsDepid;
    }

    public void setTblSettlementsDepid(Long tblSettlementsDepid) {
        this.tblSettlementsDepid = tblSettlementsDepid;
    }

    public String getTblSettlementsLat() {
        return tblSettlementsLat;
    }

    public void setTblSettlementsLat(String tblSettlementsLat) {
        this.tblSettlementsLat = tblSettlementsLat;
    }

    public String getTblSettlementsLong() {
        return tblSettlementsLong;
    }

    public void setTblSettlementsLong(String tblSettlementsLong) {
        this.tblSettlementsLong = tblSettlementsLong;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsersFullName() {
        return usersFullName;
    }

    public void setUsersFullName(String usersFullName) {
        this.usersFullName = usersFullName;
    }

    public String getUsersCellphone() {
        return usersCellphone;
    }

    public void setUsersCellphone(String usersCellphone) {
        this.usersCellphone = usersCellphone;
    }

    public String getTblTmpDeviceStatusPortStatus() {
        return tblTmpDeviceStatusPortStatus;
    }

    public void setTblTmpDeviceStatusPortStatus(String tblTmpDeviceStatusPortStatus) {
        this.tblTmpDeviceStatusPortStatus = tblTmpDeviceStatusPortStatus;
    }

    public String getTblSettlementsManagerBuilding() {
        return tblSettlementsManagerBuilding;
    }

    public void setTblSettlementsManagerBuilding(String tblSettlementsManagerBuilding) {
        this.tblSettlementsManagerBuilding = tblSettlementsManagerBuilding;
    }

    public String getTblDeviceAddress() {
        return tblDeviceAddress;
    }

    public void setTblDeviceAddress(String tblDeviceAddress) {
        this.tblDeviceAddress = tblDeviceAddress;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptTelephone() {
        return deptTelephone;
    }

    public void setDeptTelephone(String deptTelephone) {
        this.deptTelephone = deptTelephone;
    }

    public int getWarningType() {
        return warningType;
    }

    public void setWarningType(int warningType) {
        this.warningType = warningType;
    }

    public Long getWarningId() {
        return warningId;
    }

    public void setWarningId(Long warningId) {
        this.warningId = warningId;
    }

    public List<WarningUser> getListWarningUser() {
        return listWarningUser;
    }

    public void setListWarningUser(List<WarningUser> listWarningUser) {
        this.listWarningUser = listWarningUser;
    }

    public int getRescueTeamNumber() {
        return rescueTeamNumber;
    }

    public void setRescueTeamNumber(int rescueTeamNumber) {
        this.rescueTeamNumber = rescueTeamNumber;
    }

    public int getRescueTeamMemberNumber() {
        return rescueTeamMemberNumber;
    }

    public void setRescueTeamMemberNumber(int rescueTeamMemberNumber) {
        this.rescueTeamMemberNumber = rescueTeamMemberNumber;
    }

    public int getToolNumber() {
        return toolNumber;
    }

    public void setToolNumber(int toolNumber) {
        this.toolNumber = toolNumber;
    }

    public int getToolTypeNumber() {
        return toolTypeNumber;
    }

    public void setToolTypeNumber(int toolTypeNumber) {
        this.toolTypeNumber = toolTypeNumber;
    }

    public String getFieldWork() {
        return fieldWork;
    }

    public void setFieldWork(String fieldWork) {
        this.fieldWork = fieldWork;
    }

}
