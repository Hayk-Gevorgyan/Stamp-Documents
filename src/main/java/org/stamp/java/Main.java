package org.stamp.java;

public class Main {
    public static void main(String[] args) {
        StampBuilder stampBuilder = new StampBuilder();
        stampBuilder.setImage();
        stampBuilder.setSignature();
        stampBuilder.Build();
        Stamping.stamp();
    }
}