package rikka.librikka.math;

import net.minecraft.util.math.MathHelper;

public class MathAssitant {
    public static final float PI = (float) Math.PI;
    
    public static float cosAngle (float angle) {
    	return MathHelper.cos(angle * 0.01745329252F);
    }
    
    public static float sinAngle (float angle) {
    	return MathHelper.sin(angle * 0.01745329252F);
    }
    
    /**
     * Calculate the distance between two points (3d)
     *
     * @param xStart Start X coordinate
     * @param yStart Start Y coordinate
     * @param zStart Start Z coordinate
     * @param xEnd   End X coordinate
     * @param yEnd   End Y coordinate
     * @param zEnd   End Z coordinate
     */
    public static double distanceOf(double xStart, double yStart, double zStart, double xEnd, double yEnd, double zEnd) {
        return Math.sqrt((xStart - xEnd) * (xStart - xEnd) +
                (yStart - yEnd) * (yStart - yEnd) +
                (zStart - zEnd) * (zStart - zEnd));
    }

    public static float distanceOf(float xStart, float yStart, float zStart, float xEnd, float yEnd, float zEnd) {
        return MathHelper.sqrt((xStart - xEnd) * (xStart - xEnd) +
                (yStart - yEnd) * (yStart - yEnd) +
                (zStart - zEnd) * (zStart - zEnd));
    }
}
