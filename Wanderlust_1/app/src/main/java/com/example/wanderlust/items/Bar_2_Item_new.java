package com.example.wanderlust.items;
/***
 * 1.日程内容
 * 2.图片地址
 * 3.日程类型
 * 4.发布日志位置
 * 5.发布日期的日期
 * 根据发布日期查询
 */
public class Bar_2_Item_new {
    private long _id;
    private String scheduleContent;
    private String scheduleImgUrl;
    private String scheduleType;
    private String scheduleLocation;
<<<<<<< HEAD
    private String scheduleDate;
    private String scheduleTime;

    public Bar_2_Item_new(long _id, String scheduleContent, String scheduleImgUrl, String scheduleType, String scheduleLocation, String scheduleDate, String scheduleTime) {
=======
    private Long scheduleDate;

    public Bar_2_Item_new(long _id, String scheduleContent, String scheduleImgUrl, String scheduleType, String scheduleLocation, Long scheduleDate) {
>>>>>>> 31e70a928f48566179ec37decb13b737efa8325d
        this.scheduleContent = scheduleContent;
        this.scheduleImgUrl = scheduleImgUrl;
        this._id = _id;
        this.scheduleType = scheduleType;
        this.scheduleLocation = scheduleLocation;
        this.scheduleDate = scheduleDate;
        this.scheduleTime = scheduleTime;
    }
    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getScheduleContent() {
        return scheduleContent;
    }

    public void setScheduleContent(String scheduleContent) {
        this.scheduleContent = scheduleContent;
    }

    public String getScheduleImgUrl() {
        return scheduleImgUrl;
    }

    public void setScheduleImgUrl(String scheduleImgUrl) {
        this.scheduleImgUrl = scheduleImgUrl;
    }

    public String getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(String scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getScheduleLocation() {
        return scheduleLocation;
    }

    public void setScheduleLocation(String scheduleLocation) {
        this.scheduleLocation = scheduleLocation;
    }

    public Long getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Long scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }
}
