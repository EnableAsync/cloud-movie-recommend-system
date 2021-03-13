package com.rai.model.recom;

/**
 * @Auther EnableAsync
 * @Date 2021/3/13
 */
public class Recommendation {
    // 电影ID
    private int mid;

    // 电影的推荐得分
    private Double score;

    public Recommendation() {
    }

    public Recommendation(int mid, Double score) {
        this.mid = mid;
        this.score = score;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
