package com.landscape.mocknetapi.sample;

import com.google.gson.Gson;
import com.landscape.mocknetapi.util.JSONS;

/**
 * Created by landscape on 2016/10/20.
 */

public class UserBean {


    /**
     * name : nick
     * sex : man
     * age : 18
     * jobs : 1
     */

    private String name;
    private String sex;
    private int age;
    private int jobs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getJobs() {
        return jobs;
    }

    public void setJobs(int jobs) {
        this.jobs = jobs;
    }

    @Override public String toString() {
        return JSONS.parseJson(this);
    }
}
