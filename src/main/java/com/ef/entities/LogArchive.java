package com.ef.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mohamed on 15/10/17.
 */
@Entity
@Table(name = "log_archive")
public class LogArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip")
    private String ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
