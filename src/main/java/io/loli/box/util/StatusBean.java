package io.loli.box.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StatusBean implements Serializable {
    private static final long serialVersionUID = 2165887525472049534L;
    private String status;
    private String message;

    public StatusBean() {

    }

    public StatusBean(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
