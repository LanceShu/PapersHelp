package com.example.race.papershelp.Bean;

import android.graphics.Bitmap;

/**
 * Created by Kiboooo on 2017/10/20.
 */

public class ApplyProgressData {

    private String Time;
    private String Day;
    private String ProgressContent;
    private Bitmap Image;
    private boolean ProgressBarColor;

    public Bitmap getImage() {
        return Image;
    }

    public boolean isProgressBarColor() {
        return ProgressBarColor;
    }


    public String getDay() {
        return Day;
    }

    public String getTime() {
        return Time;
    }


    public String getProgressContent() {
        return ProgressContent;
    }

    public void setTime(String time) {
        Time = time;
    }

    public void setDay(String day) {
        Day = day;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    public void setProgressBarColor(boolean progressBarColor) {
        ProgressBarColor = progressBarColor;
    }

    public void setProgressContent(String progressContent) {
        ProgressContent = progressContent;
    }
}
