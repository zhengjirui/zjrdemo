package com.metshow.bz.commons.bean;

import com.kwan.base.bean.POJO;

/**
 * Created by Mr.Kwan on 2016-11-16.
 */

public class VersionMsg extends POJO {

    private String CreateDate;//"\/Date(928120800000+0800)\/",
    private int Force;//32767,
    private String Id;//9223372036854775807,
    private String Message;//"String content",
    private String ModifyTime;//"\/Date(928120800000+0800)\/",
    private String ProgramId;//9223372036854775807,
    private String Type;//32767,
    private String Url;//"String content",
    private String Version;//"String content"

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public int getForce() {
        return Force;
    }

    public void setForce(int force) {
        Force = force;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(String modifyTime) {
        ModifyTime = modifyTime;
    }

    public String getProgramId() {
        return ProgramId;
    }

    public void setProgramId(String programId) {
        ProgramId = programId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }
}
