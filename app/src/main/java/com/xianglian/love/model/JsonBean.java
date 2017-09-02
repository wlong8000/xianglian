package com.xianglian.love.model;

import com.bigkoo.pickerview.model.IPickerViewData;

import java.util.List;


public class JsonBean implements IPickerViewData {

    private String code;
    private String name;
    private List<CityBean> next;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<CityBean> getNext() {
        return next;
    }

    public void setNext(List<CityBean> next) {
        this.next = next;
    }

    @Override
    public String getPickerViewText() {
        return this.name;
    }


    public static class CityBean implements IPickerViewData {
        private String code;
        private String name;
        private List<CityBean> next;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CityBean> getNext() {
            return next;
        }

        public void setNext(List<CityBean> next) {
            this.next = next;
        }

        @Override
        public String getPickerViewText() {
            return name;
        }
    }
}
