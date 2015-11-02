package com.jsb.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JammyQtheLab on 2015/11/2.
 */
public class InviteInfo implements Parcelable {

    /**
     *
     *  id	int	信息编号	Y
     inviterid	int	邀请者编号	Y
     invitername	string	邀请者编号
     inviterphone	string	邀请者电话
     inviteeid	int	被邀请者编号	Y
     read	int	别邀请者是否处理  0未处理1处理
     readtime	Timestamp	处理时间
     invitetime	Timestamp	邀请时间
     agreed	int	结果是否同意 0不同意  1同意
     */

    private Integer id;
    private Integer inviterid;
    private String invitername;
    private String inviterphone;
    private Integer inviteeid;
    private Integer read;
    private Integer agreed;
    private Long readtime;
    private Long invitetime;

    @Override
    public String toString() {
        return "InviteInfo{" +
                "id=" + id +
                ", inviterid=" + inviterid +
                ", invitername='" + invitername + '\'' +
                ", inviterphone='" + inviterphone + '\'' +
                ", inviteeid=" + inviteeid +
                ", read=" + read +
                ", agreed=" + agreed +
                ", readtime=" + readtime +
                ", invitetime=" + invitetime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInviterid() {
        return inviterid;
    }

    public void setInviterid(Integer inviterid) {
        this.inviterid = inviterid;
    }

    public String getInvitername() {
        return invitername;
    }

    public void setInvitername(String invitername) {
        this.invitername = invitername;
    }

    public String getInviterphone() {
        return inviterphone;
    }

    public void setInviterphone(String inviterphone) {
        this.inviterphone = inviterphone;
    }

    public Integer getInviteeid() {
        return inviteeid;
    }

    public void setInviteeid(Integer inviteeid) {
        this.inviteeid = inviteeid;
    }

    public Integer getRead() {
        return read;
    }

    public void setRead(Integer read) {
        this.read = read;
    }

    public Integer getAgreed() {
        return agreed;
    }

    public void setAgreed(Integer agreed) {
        this.agreed = agreed;
    }

    public Long getReadtime() {
        return readtime;
    }

    public void setReadtime(Long readtime) {
        this.readtime = readtime;
    }

    public Long getInvitetime() {
        return invitetime;
    }

    public void setInvitetime(Long invitetime) {
        this.invitetime = invitetime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.inviterid);
        dest.writeString(this.invitername);
        dest.writeString(this.inviterphone);
        dest.writeValue(this.inviteeid);
        dest.writeValue(this.read);
        dest.writeValue(this.agreed);
        dest.writeValue(this.readtime);
        dest.writeValue(this.invitetime);
    }

    public InviteInfo() {
    }

    protected InviteInfo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.inviterid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.invitername = in.readString();
        this.inviterphone = in.readString();
        this.inviteeid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.read = (Integer) in.readValue(Integer.class.getClassLoader());
        this.agreed = (Integer) in.readValue(Integer.class.getClassLoader());
        this.readtime = (Long) in.readValue(Long.class.getClassLoader());
        this.invitetime = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Parcelable.Creator<InviteInfo> CREATOR = new Parcelable.Creator<InviteInfo>() {
        public InviteInfo createFromParcel(Parcel source) {
            return new InviteInfo(source);
        }

        public InviteInfo[] newArray(int size) {
            return new InviteInfo[size];
        }
    };
}
