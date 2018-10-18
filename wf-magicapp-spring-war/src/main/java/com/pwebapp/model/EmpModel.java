package com.pwebapp.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class EmpModel implements Serializable {

    private static final long serialVersionUID = -3315599904254615410L;

    private String ename;
    private String job;
    private Date hiredate;
    private BigDecimal sal;
    private BigDecimal comm;

    public EmpModel() { // For Default implementation

    }

    public EmpModel(String ename, String job, Date hiredate, BigDecimal sal, BigDecimal comm) {
        super();
        this.ename = ename;
        this.job = job;
        this.hiredate = hiredate;
        this.sal = sal;
        this.comm = comm;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public BigDecimal getSal() {
        return sal;
    }

    public void setSal(BigDecimal sal) {
        this.sal = sal;
    }

    public BigDecimal getComm() {
        return comm;
    }

    public void setComm(BigDecimal comm) {
        this.comm = comm;
    }

    @Override
    public String toString() {
        return "EmpModel [ename=" + ename + ", job=" + job + ", hiredate=" + hiredate + ", sal=" + sal + ", comm="
                + comm + "]";
    }

}
