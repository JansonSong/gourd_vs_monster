package com.sjq.gourd.creature;

import com.sjq.gourd.constant.Constant;

public class ImagePosition {
    private double layoutX;
    private double layoutY;
    private double imageWidth;
    private double imageHeight;

    public ImagePosition(double layoutX, double layoutY) {
        this.layoutX = layoutX;
        this.layoutY = layoutY;
    }

    public ImagePosition(double layoutX, double layoutY,
                         double imageWidth, double imageHeight) {
        this.layoutX = layoutX;
        this.layoutY = layoutY;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    public void setLayoutX(double layoutX) {
        if(layoutX <= 0) layoutX = 0;
        if(layoutX >= Constant.FIGHT_PANE_WIDTH - imageWidth)
            layoutX = Constant.FIGHT_PANE_WIDTH - imageWidth;
        this.layoutX = layoutX;
    }

    public void setLayoutY(double layoutY) {
        if(layoutY <= 0) layoutY = 0;
        if(layoutY >= Constant.FIGHT_PANE_HEIGHT - imageHeight)
            layoutY = Constant.FIGHT_PANE_HEIGHT - imageHeight;
        this.layoutY = layoutY;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }

    public double getDistance(ImagePosition imagePosition) {
        double x = layoutX - imagePosition.layoutX;
        double y = layoutY - imagePosition.layoutY;
        return Math.sqrt(x * x + y * y);
    }

    public int getRelativePosClose(ImagePosition imagePosition) {
        double deltaX = layoutX - imagePosition.layoutX;
        double deltaY = layoutY - imagePosition.layoutY;
        double x = Math.abs(deltaX);
        double y = Math.abs(deltaY);
        if (x < y) {
            if (deltaX < 0) return Constant.Direction.RIGHT;
            else return Constant.Direction.LEFT;
        } else {
            if (deltaY < 0) return Constant.Direction.DOWN;
            else return Constant.Direction.UP;
        }
    }

    public int getRelativePosFar(ImagePosition imagePosition) {
        double deltaX = layoutX - imagePosition.layoutX;
        double deltaY = layoutY - imagePosition.layoutY;
        double x = Math.abs(deltaX);
        double y = Math.abs(deltaY);
        if (x < y) {
            if (deltaY < 0) return Constant.Direction.DOWN;
            else return Constant.Direction.UP;
        } else {
            if (deltaY < 0) return Constant.Direction.RIGHT;
            else return Constant.Direction.LEFT;
        }
    }
}
