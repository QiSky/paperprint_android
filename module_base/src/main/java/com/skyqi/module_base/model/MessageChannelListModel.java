package com.skyqi.module_base.model;///

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/25 2:04 下午
/// * @Description: 文件说明
///
public class MessageChannelListModel {

    ///最新消息时间
    private Long time;

    ///ID
    private Long id;

    ///标题
    private String nickName;

    ///最新消息内容
    private String content;

    ///频道类型
    private Integer type;

    ///是否已读
    private boolean isRead;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
