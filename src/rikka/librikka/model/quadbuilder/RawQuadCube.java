package rikka.librikka.model.quadbuilder;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import rikka.librikka.math.MathAssitant;

import java.util.List;

/**
 * Represent a 'raw' cube
 *
 * @author Rikka0_0
 */
@SideOnly(Side.CLIENT)
public class RawQuadCube implements IRawElement<RawQuadCube> {
    private final float[][] vertexes;
    private final TextureAtlasSprite[] icons;

    public RawQuadCube(float maxX, float maxY, float maxZ, TextureAtlasSprite icon) {
        this(maxX, maxY, maxZ, new TextureAtlasSprite[]{icon, icon, icon, icon, icon, icon});
    }

    public RawQuadCube(float maxX, float maxY, float maxZ, TextureAtlasSprite[] icons) {
        this.icons = icons;
        vertexes = new float[8][];
        float x = maxX / 2.0F;
        float z = maxZ / 2.0F;

        //Top
        this.vertexes[0] = new float[]{x, maxY, z};
        this.vertexes[1] = new float[]{x, maxY, -z};
        this.vertexes[2] = new float[]{-x, maxY, -z};
        this.vertexes[3] = new float[]{-x, maxY, z};

        //Bottom
        this.vertexes[4] = new float[]{x, 0, z};
        this.vertexes[5] = new float[]{x, 0, -z};
        this.vertexes[6] = new float[]{-x, 0, -z};
        this.vertexes[7] = new float[]{-x, 0, z};
    }

    @Deprecated
    public RawQuadCube(double[][] vertexes, TextureAtlasSprite[] icons) {
        this.icons = icons;
        this.vertexes = new float[vertexes.length][];

        for (int i = 0; i < vertexes.length; i++) {
            this.vertexes[i] = new float[vertexes[i].length];
            for (int j = 0; j < vertexes[i].length; j++)
                this.vertexes[i][j] = (float) vertexes[i][j];
        }
    }

    public RawQuadCube(float[][] vertexes, TextureAtlasSprite[] icons) {
        this.icons = icons;
        this.vertexes = new float[vertexes.length][];

        for (int i = 0; i < vertexes.length; i++) {
            this.vertexes[i] = new float[vertexes[i].length];
            for (int j = 0; j < vertexes[i].length; j++)
                this.vertexes[i][j] = vertexes[i][j];
        }
    }

    @Override
    public RawQuadCube clone() {
        return new RawQuadCube(this.vertexes, this.icons);
    }

    @Override
    public RawQuadCube translateCoord(float x, float y, float z) {
        for (int i = 0; i < this.vertexes.length; i++) {
            this.vertexes[i][0] += x;
            this.vertexes[i][1] += y;
            this.vertexes[i][2] += z;
        }

        return this;
    }
    
    /**
     * 	.^x<br>
     * . out of the screen<br>
     * x into the screen<br>
     * ^ point towards the positive direction of X Axis<br>
     * Right-Hand-Rule applies<br>
     * Check out https://open.gl/transformations for details
     * @param angle Rotate the part by a given angle
     */
    @Override
    public RawQuadCube rotateAroundX(float angle) {
        float cos = MathAssitant.cosAngle(angle);
        float sin = MathAssitant.sinAngle(angle);

        for (int i = 0; i < this.vertexes.length; i++) {						//	X	Y	Z
            float x = this.vertexes[i][0];										//	1	0	0
            float y = this.vertexes[i][1] * cos - this.vertexes[i][2] * sin;	//	0	cos	-sin
            float z = this.vertexes[i][1] * sin + this.vertexes[i][2] * cos;	//	0	sin	cos
            this.vertexes[i][0] = x;
            this.vertexes[i][1] = y;
            this.vertexes[i][2] = z;
        }

        return this;
    }

    /**
     * 	.^x<br>
     * . out of the screen<br>
     * x into the screen<br>
     * ^ point towards the positive direction of Y Axis<br>
     * Right-Hand-Rule applies<br>
     * Check out https://open.gl/transformations for details
     * @param angle Rotate the part by a given angle
     */
    @Override
    public RawQuadCube rotateAroundY(float angle) {
        float cos = MathAssitant.cosAngle(angle);
        float sin = MathAssitant.sinAngle(angle);

        for (int i = 0; i < this.vertexes.length; i++) {						//	X		Y	Z
            float x = 	this.vertexes[i][0] * cos + this.vertexes[i][2] * sin;	//	cos		0	sin
            float y = 	this.vertexes[i][1];									//	0		1	0
            float z = - this.vertexes[i][0] * sin + this.vertexes[i][2] * cos;	//	-sin	0	cos
            this.vertexes[i][0] = x;
            this.vertexes[i][1] = y;
            this.vertexes[i][2] = z;
        }

        return this;
    }
    
    /**
     * 	.^x<br>
     * . out of the screen<br>
     * x into the screen<br>
     * ^ point towards the positive direction of Z Axis<br>
     * Right-Hand-Rule applies<br>
     * Check out https://open.gl/transformations for details
     * @param angle Rotate the part by a given angle
     */
    @Override
    public RawQuadCube rotateAroundZ(float angle) {
        float cos = MathAssitant.cosAngle(angle);
        float sin = MathAssitant.sinAngle(angle);

        for (int i = 0; i < this.vertexes.length; i++) {						//	X		Y	Z
            float x = this.vertexes[i][0] * cos - this.vertexes[i][1] * sin;	//	cos	-sin	0
            float y = this.vertexes[i][0] * sin + this.vertexes[i][1] * cos;	//	sin	cos		0
            float z = this.vertexes[i][2];										//	0		0	1
            this.vertexes[i][0] = x;
            this.vertexes[i][1] = y;
            this.vertexes[i][2] = z;
        }

        return this;
    }

    @Override
    public RawQuadCube rotateToVec(float xStart, float yStart, float zStart, float xEnd, float yEnd, float zEnd) {
        float distance = MathAssitant.distanceOf(xStart, yStart, zStart, xEnd, yEnd, zEnd);
        this.rotateAroundY((float) (Math.atan2(zStart - zEnd, xEnd - xStart) * 180 / Math.PI));
        this.rotateAroundVector((float) (Math.acos((yEnd - yStart) / distance) * 180 / Math.PI), (zEnd - zStart) / distance, 0, (xStart - xEnd) / distance);

        return this;
    }

    @Override
    public RawQuadCube rotateToDirection(EnumFacing direction) {
        switch (direction) {
            case DOWN:
                this.rotateAroundX(180);
                break;
            case NORTH:
                this.rotateAroundY(180);
                this.rotateAroundX(270);
                break;
            case SOUTH:
                this.rotateAroundX(90);
                break;
            case WEST:
                this.rotateAroundY(270);
                this.rotateAroundZ(90);
                break;
            case EAST:
                this.rotateAroundY(90);
                this.rotateAroundZ(270);
                break;
            default:
                break;
        }

        return this;
    }

    @Override
    public RawQuadCube rotateAroundVector(float angle, float x, float y, float z) {
        //Normalize the axis vector
        float length = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / length;
        y = y / length;
        z = z / length;

        angle = angle * 0.01745329252F;    //Cast to radian
        float cos = MathHelper.cos(angle);
        float sin = MathHelper.sin(angle);

        for (int i = 0; i < this.vertexes.length; i++) {
            float d0 = this.vertexes[i][0] * (cos + x * x * (1 - cos)) + this.vertexes[i][1] * (x * y * (1 - cos) - z * sin) + this.vertexes[i][2] * (x * z * (1 - cos) + y * sin);
            float d1 = this.vertexes[i][0] * (x * y * (1 - cos) + z * sin) + this.vertexes[i][1] * (cos + y * y * (1 - cos)) + this.vertexes[i][2] * (y * z * (1 - cos) - x * sin);
            float d2 = this.vertexes[i][0] * (x * z * (1 - cos) - y * sin) + this.vertexes[i][1] * (y * z * (1 - cos) + x * sin) + this.vertexes[i][2] * (cos + z * z * (1 - cos));
            this.vertexes[i][0] = d0;
            this.vertexes[i][1] = d1;
            this.vertexes[i][2] = d2;
        }

        return this;
    }
    
	@Override
	public IRawModel scale(float scale) {
		for (int i = 0; i < this.vertexes.length; i++) {
            this.vertexes[i][0] *= scale;
            this.vertexes[i][1] *= scale;
            this.vertexes[i][2] *= scale;
		}
		return this;
	}

    @Override
    public void bake(List<BakedQuad> list) {
        float uMin, uMax, vMin, vMax;

        //Down - Yneg
        if (this.icons[0] != null) {
            uMin = 0;
            uMax = 16;    //For 32x32 64x64 textures, this number is still 16 !!!!!
            vMin = 0;
            vMax = 16;
            list.add(BakedQuadHelper.bake(
            		this.vertexes[7][0], this.vertexes[7][1], this.vertexes[7][2], uMin, vMin, //uMin, vMax
            		this.vertexes[6][0], this.vertexes[6][1], this.vertexes[6][2], uMin, vMax, //uMin, vMin
            		this.vertexes[5][0], this.vertexes[5][1], this.vertexes[5][2], uMax, vMax, //uMax, vMin
            		this.vertexes[4][0], this.vertexes[4][1], this.vertexes[4][2], uMax, vMin, //uMax, vMax
            		this.icons[0]));
        }

        //Up - Ypos
        if (this.icons[1] != null) {
            uMin = 0;
            uMax = 16;
            vMin = 0;
            vMax = 16;
            list.add(BakedQuadHelper.bake(
                    this.vertexes[0][0], this.vertexes[0][1], this.vertexes[0][2], uMax, vMax,
                    this.vertexes[1][0], this.vertexes[1][1], this.vertexes[1][2], uMax, vMin,
                    this.vertexes[2][0], this.vertexes[2][1], this.vertexes[2][2], uMin, vMin,
                    this.vertexes[3][0], this.vertexes[3][1], this.vertexes[3][2], uMin, vMax,
                    this.icons[1]));
        }

        //North - Zneg
        if (this.icons[2] != null) {
            uMin = 0;
            uMax = 16;
            vMin = 0;
            vMax = 16;
            list.add(BakedQuadHelper.bake(
                    this.vertexes[2][0], this.vertexes[2][1], this.vertexes[2][2], uMax, vMin,
                    this.vertexes[1][0], this.vertexes[1][1], this.vertexes[1][2], uMin, vMin,
                    this.vertexes[5][0], this.vertexes[5][1], this.vertexes[5][2], uMin, vMax,
                    this.vertexes[6][0], this.vertexes[6][1], this.vertexes[6][2], uMax, vMax,
                    this.icons[2]));
        }

        //South - Zpos
        if (this.icons[3] != null) {
            uMin = 0;
            uMax = 16;
            vMin = 0;
            vMax = 16;
            list.add(BakedQuadHelper.bake(
                    this.vertexes[3][0], this.vertexes[3][1], this.vertexes[3][2], uMin, vMin,
                    this.vertexes[7][0], this.vertexes[7][1], this.vertexes[7][2], uMin, vMax,
                    this.vertexes[4][0], this.vertexes[4][1], this.vertexes[4][2], uMax, vMax,
                    this.vertexes[0][0], this.vertexes[0][1], this.vertexes[0][2], uMax, vMin,
                    this.icons[3]));
        }

        //West - Xneg
        if (this.icons[4] != null) {
            uMin = 0;
            uMax = 16;
            vMin = 0;
            vMax = 16;
            list.add(BakedQuadHelper.bake(
                    this.vertexes[3][0], this.vertexes[3][1], this.vertexes[3][2], uMax, vMin,
                    this.vertexes[2][0], this.vertexes[2][1], this.vertexes[2][2], uMin, vMin,
                    this.vertexes[6][0], this.vertexes[6][1], this.vertexes[6][2], uMin, vMax,
                    this.vertexes[7][0], this.vertexes[7][1], this.vertexes[7][2], uMax, vMax, 
                    this.icons[4]));
        }

        //East - Xpos
        if (this.icons[5] != null) {
            uMin = 0;
            uMax = 16;
            vMin = 0;
            vMax = 16;
            list.add(BakedQuadHelper.bake(
                    this.vertexes[1][0], this.vertexes[1][1], this.vertexes[1][2], uMax, vMin,
                    this.vertexes[0][0], this.vertexes[0][1], this.vertexes[0][2], uMin, vMin,
                    this.vertexes[4][0], this.vertexes[4][1], this.vertexes[4][2], uMin, vMax,
                    this.vertexes[5][0], this.vertexes[5][1], this.vertexes[5][2], uMax, vMax,
            		this.icons[5]));
        }
    }
}
