package com.data.buring;

/**
 * @author qingyang
 * @date 2018/12/20.
 */
public class Event {
    /**
     * 时间标识
     */
    private String key;
    /**
     * 谁发起的
     */
    private Who who;
    /**
     * 何时发起
     */
    private String when;
    /**
     * 什么地方
     */
    private Where where;
    /**
     * 如何发起
     */
    private String how;
    /**
     * 具体内容
     */
    private String what;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Who getWho() {
        return who;
    }

    public void setWho(Who who) {
        this.who = who;
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public Where getWhere() {
        return where;
    }

    public void setWhere(Where where) {
        this.where = where;
    }

    public String getHow() {
        return how;
    }

    public void setHow(String how) {
        this.how = how;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    private static class Who{

        private String userId;

        private String phone;

        private String userName;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    private static class Where{

        private String country;

        private String province;

        private String city;

        private String district;

        private String longitude;

        private String latitude;

        private String page;

        private String beforePage;

        public String getCountry() {

            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getBeforePage() {
            return beforePage;
        }

        public void setBeforePage(String beforePage) {
            this.beforePage = beforePage;
        }
    }
}
