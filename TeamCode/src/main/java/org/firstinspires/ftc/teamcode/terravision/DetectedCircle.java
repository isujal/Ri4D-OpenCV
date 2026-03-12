package org.firstinspires.ftc.teamcode.terravision;

public class DetectedCircle {

    public double x;
    public double y;
    public double radius;
    public double area;

    public DetectedCircle(double x, double y, double radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.area = Math.PI * radius * radius;
    }
}