package cn.com.ethank.mylibrary.resourcelibrary.common_util.printer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zjgz on 2017/11/9.
 */

public class BitmapBean implements Serializable {
    //第一行
    String first_line_text;//托盘编号
    int textSize_firstline;
    int width_start_first;
    int height_start_first;
    //第二行
    String second_line_text;//目标工位
    int textSize_secondline;
    int width_start_second;
    int height_start_second;
    //第二行
    String second_line_two_text;//订单生产编号
    int textSize_secondline_two;
    int width_start_second_two;
    int height_start_second_two;
    //第三行
    String three_line_text;//物料清单
    int textSize_threeline;
    int width_start_three;
    int height_start_three;

    //第四行
    String four_line_text;//物料名称
    int textSize_fourline;
    int width_start_four;
    int height_start_four;
    //第四行
    String four_line_two_text;//数量
    int textSize_fourline_two;
    int width_start_four_two;
    int height_start_four_two;

    List<MaterBean> materDatas;
    int textSize_mater;//物料字体大小
    int width_mater_first;//第一列x
    int width_mater_second;//第二列x

    public int getTextSize_mater() {
        return textSize_mater;
    }

    public void setTextSize_mater(int textSize_mater) {
        this.textSize_mater = textSize_mater;
    }

    public int getWidth_mater_first() {
        return width_mater_first;
    }

    public void setWidth_mater_first(int width_mater_first) {
        this.width_mater_first = width_mater_first;
    }

    public int getWidth_mater_second() {
        return width_mater_second;
    }

    public void setWidth_mater_second(int width_mater_second) {
        this.width_mater_second = width_mater_second;
    }

    public String getFirst_line_text() {
        return first_line_text;
    }

    public void setFirst_line_text(String first_line_text) {
        this.first_line_text = first_line_text;
    }

    public int getTextSize_firstline() {
        return textSize_firstline;
    }

    public void setTextSize_firstline(int textSize_firstline) {
        this.textSize_firstline = textSize_firstline;
    }

    public int getWidth_start_first() {
        return width_start_first;
    }

    public void setWidth_start_first(int width_start_first) {
        this.width_start_first = width_start_first;
    }

    public int getHeight_start_first() {
        return height_start_first;
    }

    public void setHeight_start_first(int height_start_first) {
        this.height_start_first = height_start_first;
    }

    public String getSecond_line_text() {
        return second_line_text;
    }

    public void setSecond_line_text(String second_line_text) {
        this.second_line_text = second_line_text;
    }

    public int getTextSize_secondline() {
        return textSize_secondline;
    }

    public void setTextSize_secondline(int textSize_secondline) {
        this.textSize_secondline = textSize_secondline;
    }

    public int getWidth_start_second() {
        return width_start_second;
    }

    public void setWidth_start_second(int width_start_second) {
        this.width_start_second = width_start_second;
    }

    public int getHeight_start_second() {
        return height_start_second;
    }

    public void setHeight_start_second(int height_start_second) {
        this.height_start_second = height_start_second;
    }

    public String getSecond_line_two_text() {
        return second_line_two_text;
    }

    public void setSecond_line_two_text(String second_line_two_text) {
        this.second_line_two_text = second_line_two_text;
    }

    public int getTextSize_secondline_two() {
        return textSize_secondline_two;
    }

    public void setTextSize_secondline_two(int textSize_secondline_two) {
        this.textSize_secondline_two = textSize_secondline_two;
    }

    public int getWidth_start_second_two() {
        return width_start_second_two;
    }

    public void setWidth_start_second_two(int width_start_second_two) {
        this.width_start_second_two = width_start_second_two;
    }

    public int getHeight_start_second_two() {
        return height_start_second_two;
    }

    public void setHeight_start_second_two(int height_start_second_two) {
        this.height_start_second_two = height_start_second_two;
    }

    public String getThree_line_text() {
        return three_line_text;
    }

    public void setThree_line_text(String three_line_text) {
        this.three_line_text = three_line_text;
    }

    public int getTextSize_threeline() {
        return textSize_threeline;
    }

    public void setTextSize_threeline(int textSize_threeline) {
        this.textSize_threeline = textSize_threeline;
    }

    public int getWidth_start_three() {
        return width_start_three;
    }

    public void setWidth_start_three(int width_start_three) {
        this.width_start_three = width_start_three;
    }

    public int getHeight_start_three() {
        return height_start_three;
    }

    public void setHeight_start_three(int height_start_three) {
        this.height_start_three = height_start_three;
    }

    public String getFour_line_text() {
        return four_line_text;
    }

    public void setFour_line_text(String four_line_text) {
        this.four_line_text = four_line_text;
    }

    public int getTextSize_fourline() {
        return textSize_fourline;
    }

    public void setTextSize_fourline(int textSize_fourline) {
        this.textSize_fourline = textSize_fourline;
    }

    public int getWidth_start_four() {
        return width_start_four;
    }

    public void setWidth_start_four(int width_start_four) {
        this.width_start_four = width_start_four;
    }

    public int getHeight_start_four() {
        return height_start_four;
    }

    public void setHeight_start_four(int height_start_four) {
        this.height_start_four = height_start_four;
    }

    public String getFour_line_two_text() {
        return four_line_two_text;
    }

    public void setFour_line_two_text(String four_line_two_text) {
        this.four_line_two_text = four_line_two_text;
    }

    public int getTextSize_fourline_two() {
        return textSize_fourline_two;
    }

    public void setTextSize_fourline_two(int textSize_fourline_two) {
        this.textSize_fourline_two = textSize_fourline_two;
    }

    public int getWidth_start_four_two() {
        return width_start_four_two;
    }

    public void setWidth_start_four_two(int width_start_four_two) {
        this.width_start_four_two = width_start_four_two;
    }

    public int getHeight_start_four_two() {
        return height_start_four_two;
    }

    public void setHeight_start_four_two(int height_start_four_two) {
        this.height_start_four_two = height_start_four_two;
    }

    public List<MaterBean> getMaterDatas() {
        return materDatas;
    }

    public void setMaterDatas(List<MaterBean> materDatas) {
        this.materDatas = materDatas;
    }

    public static class MaterBean implements Serializable {
        private String name;
        private int number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

}
