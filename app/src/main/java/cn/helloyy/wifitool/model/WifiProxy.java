package cn.helloyy.wifitool.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by wangyu on 2016/9/28.
 */

@Entity
public class WifiProxy {
    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String host;

    @NotNull
    private int port;

    @NotNull
    private int used;

    @NotNull
    private Date createDate;

    @NotNull
    private Date modifyDate;

    @Generated(hash = 143747224)
    public WifiProxy(Long id, @NotNull String host, int port, int used,
            @NotNull Date createDate, @NotNull Date modifyDate) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.used = used;
        this.createDate = createDate;
        this.modifyDate = modifyDate;
    }

    @Generated(hash = 1086530603)
    public WifiProxy() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getUsed() {
        return this.used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return this.modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    
}
