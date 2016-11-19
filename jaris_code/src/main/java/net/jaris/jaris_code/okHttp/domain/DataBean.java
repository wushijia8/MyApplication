package net.jaris.jaris_code.okHttp.domain;

import java.util.List;

/**
 * Created by Jaris on 2016/9/2.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */
public class DataBean {


    /**
     * id : 62323
     * movieName : 《星际迷航3》终极版预告片
     * coverImg : http://img31.mtime.cn/mg/2016/09/01/171305.47419564.jpg
     * movieId : 195062
     * url : http://vfx.mtime.cn/Video/2016/09/01/mp4/160901142838353426_480.mp4
     * hightUrl : http://vfx.mtime.cn/Video/2016/09/01/mp4/160901142838353426.mp4
     * videoTitle : 星际迷航3：超越星辰 终极版预告片
     * videoLength : 71
     * rating : 7.9
     * type : ["动作","冒险","科幻","惊悚"]
     * summary : 进取号大战漫天敌舰
     */

    private List<ItemData> trailers;

    public List<ItemData> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<ItemData> trailers) {
        this.trailers = trailers;
    }

    public static class ItemData {
        private int id;
        private String movieName;
        private String coverImg;
        private int movieId;
        private String url;
        private String hightUrl;
        private String videoTitle;
        private int videoLength;
        private double rating;
        private String summary;
        private List<String> type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getCoverImg() {
            return coverImg;
        }

        public void setCoverImg(String coverImg) {
            this.coverImg = coverImg;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHightUrl() {
            return hightUrl;
        }

        public void setHightUrl(String hightUrl) {
            this.hightUrl = hightUrl;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public int getVideoLength() {
            return videoLength;
        }

        public void setVideoLength(int videoLength) {
            this.videoLength = videoLength;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }
    }
}
