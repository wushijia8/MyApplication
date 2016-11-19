package net.jaris.jaris_code.json.bean;

import java.util.List;

/**
 * Created by Jaris on 2016/10/30.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */

public class DataInfo {


    /**
     * rs_code : 1000
     * data : {"count":5,"items":[{"id":45,"title":"坚果"},{"id":132,"title":"炒货"},{"id":166,"title":"蜂蜜"},{"id":195,"title":"果冻"},{"id":196,"title":"巧克力"}]}
     * rs_msg : success
     */

    private String rs_code;
    /**
     * count : 5
     * items : [{"id":45,"title":"坚果"},{"id":132,"title":"炒货"},{"id":166,"title":"蜂蜜"},{"id":195,"title":"果冻"},{"id":196,"title":"巧克力"}]
     */

    private DataBean data;
    private String rs_msg;

    @Override
    public String toString() {
        return "DataInfo{" +
                "rs_code='" + rs_code + '\'' +
                ", data=" + data +
                ", rs_msg='" + rs_msg + '\'' +
                '}';
    }

    public String getRs_code() {
        return rs_code;
    }

    public void setRs_code(String rs_code) {
        this.rs_code = rs_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getRs_msg() {
        return rs_msg;
    }

    public void setRs_msg(String rs_msg) {
        this.rs_msg = rs_msg;
    }

    public static class DataBean {
        private int count;

        @Override
        public String toString() {
            return "DataBean{" +
                    "count=" + count +
                    ", items=" + items +
                    '}';
        }

        /**
         * id : 45
         * title : 坚果
         */

        private List<ItemsBean> items;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            private int id;
            private String title;

            @Override
            public String toString() {
                return "ItemsBean{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
