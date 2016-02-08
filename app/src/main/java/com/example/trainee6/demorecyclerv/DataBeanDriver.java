package com.example.trainee6.demorecyclerv;

/**
 * Created by Trainee6 on 2/3/2016.
 */
public class DataBeanDriver
{
    private String id,name,mobile,email,carno,carmodel,carcompany;
    private int hour,status;

    public DataBeanDriver()
    {

    }

    public DataBeanDriver(String carcompany, String carmodel, String carno, String email, int hour, String id, String mobile, String name,int status) {
        this.carcompany = carcompany;
        this.carmodel = carmodel;
        this.carno = carno;
        this.email = email;
        this.hour = hour;
        this.id = id;
        this.mobile = mobile;
        this.name = name;
        this.status=status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCarcompany() {
        return carcompany;
    }

    public void setCarcompany(String carcompany) {
        this.carcompany = carcompany;
    }

    public String getCarmodel() {
        return carmodel;
    }

    public void setCarmodel(String carmodel) {
        this.carmodel = carmodel;
    }

    public String getCarno() {
        return carno;
    }

    public void setCarno(String carno) {
        this.carno = carno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
