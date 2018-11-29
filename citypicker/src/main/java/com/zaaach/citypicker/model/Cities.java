package com.zaaach.citypicker.model;

import java.util.List;

/**
 * Created by TeeMo111 on 2018/8/21.
 */

public class Cities extends BaseBean {

    private List<City> data;

    public List<City> getData() {
        return data;
    }

    public void setData(List<City> data) {
        this.data = data;
    }
}
