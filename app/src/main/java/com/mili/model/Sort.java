package com.mili.model;

import java.io.Serializable;
import java.util.List;

public class Sort implements Serializable {

    /**
     * created_at : 2018-09-21 09:58:13
     * icon : https://xiaochengxu.niucha.ren/storage/image/item/j8wZTrUfH8gjVz5rs41VbcUoepk5EDWC8En1XzsS.png
     * id : 307
     * name : 中秋
     * pid : 0
     * sort : 0
     * status : 1
     * updated_at : 2018-09-28 16:11:38
     * sub : [{"created_at":"2018-04-28 14:33:49","icon":"","id":2,"name":"生日蛋糕","pid":1,"sort":2,"status":1,"updated_at":"2018-08-21 14:17:37"},{"created_at":"2018-04-28 14:33:49","icon":"","id":3,"name":"甜点饮品","pid":1,"sort":3,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":4,"name":"京菜鲁菜","pid":1,"sort":4,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":5,"name":"自助餐","pid":1,"sort":5,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":6,"name":"火锅","pid":1,"sort":6,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":7,"name":"西餐","pid":1,"sort":7,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":8,"name":"小吃快餐","pid":1,"sort":8,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":9,"name":"日韩料理","pid":1,"sort":9,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":10,"name":"聚餐宴请","pid":1,"sort":10,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":11,"name":"烧烤烤肉","pid":1,"sort":11,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":12,"name":"川湘菜","pid":1,"sort":12,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":13,"name":"江浙菜","pid":1,"sort":13,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":14,"name":"香锅烤鱼","pid":1,"sort":14,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":15,"name":"粤菜","pid":1,"sort":15,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":16,"name":"中式烧烤/烤串","pid":1,"sort":16,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":17,"name":"西北菜","pid":1,"sort":17,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":18,"name":"咖啡酒吧","pid":1,"sort":18,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":19,"name":"云贵菜","pid":1,"sort":19,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":20,"name":"生鲜蔬果","pid":1,"sort":20,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":21,"name":"东北菜","pid":1,"sort":21,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":22,"name":"东南亚菜","pid":1,"sort":22,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":23,"name":"海鲜","pid":1,"sort":23,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":24,"name":"素食","pid":1,"sort":24,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":25,"name":"客家菜","pid":1,"sort":25,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":26,"name":"创意菜","pid":1,"sort":26,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":27,"name":"美团外卖","pid":1,"sort":27,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-04-28 14:33:49","icon":"","id":28,"name":"霸王餐","pid":1,"sort":28,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-07-31 14:44:03","icon":"","id":290,"name":"东北菜","pid":1,"sort":290,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-07-31 14:45:02","icon":"","id":291,"name":"东北菜好吃","pid":1,"sort":291,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-07-31 14:45:27","icon":"","id":292,"name":"湖南菜","pid":1,"sort":292,"status":1,"updated_at":"2018-08-21 14:13:01"},{"created_at":"2018-07-31 14:48:57","icon":"","id":295,"name":"东北菜","pid":1,"sort":295,"status":1,"updated_at":"2018-08-21 14:13:01"}]
     */

    private String created_at;
    private String icon;
    private int id;
    private String name;
    private int pid;
    private int sort;
    private int status;
    private String updated_at;
    private List<SortItem> sub;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<SortItem> getSub() {
        return sub;
    }

    public void setSub(List<SortItem> sub) {
        this.sub = sub;
    }

    public static class SortItem {
        /**
         * created_at : 2018-04-28 14:33:49
         * icon :
         * id : 2
         * name : 生日蛋糕
         * pid : 1
         * sort : 2
         * status : 1
         * updated_at : 2018-08-21 14:17:37
         */

        private String created_at;
        private String icon;
        private int id;
        private String name;
        private int pid;
        private int sort;
        private int status;
        private String updated_at;

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
