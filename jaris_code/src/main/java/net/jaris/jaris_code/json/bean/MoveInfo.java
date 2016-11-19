package net.jaris.jaris_code.json.bean;

/**
 * Created by Jaris on 2016/10/22.
 * Email:jaris.w@foxmail.com
 * QQ:jaris.w@foxmail.com
 * 微信：weixin-wushijia
 */

public class MoveInfo {

    private int id;
    private String movieName;
    private String summary;

    public MoveInfo() {
    }

    public MoveInfo(int id, String movieName, String summary) {
        this.id = id;
        this.movieName = movieName;
        this.summary = summary;
    }

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "MoveInfo{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
