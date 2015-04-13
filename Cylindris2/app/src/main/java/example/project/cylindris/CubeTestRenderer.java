package example.project.cylindris;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.util.Arrays;
import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CubeTestRenderer implements GLSurfaceView.Renderer
{
    //GLModel WILL be used in final version, imports and represents .obj files
    public Context context;
    public CubeModel[][] active = new CubeModel[15][16];
    public CubeModel[][] passive = new CubeModel[100][16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    public volatile float mAngle;
    public boolean[][] occupationMatrix = new boolean[15][16];
    public int completedRows = 0;
    public static int loadShader(int type, String shaderCode)
    {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
        // Set the background frame color
       GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glEnable( GLES20.GL_DEPTH_TEST );
        GLES20.glDepthFunc( GLES20.GL_LEQUAL );
        GLES20.glDepthMask( true );
        // initialize a cube
        try {

            for(int i=0;i<15;i++) {
                float offsetAngle = 0;
                Random value = new Random();
                for(int j=0;j<16;j++) {
                    active[i][j] = new CubeModel(context, "testcube", offsetAngle, false);
//                    active[i][j].color[0] = value.nextFloat();
//                    active[i][j].color[1] = value.nextFloat();
//                    active[i][j].color[2] = value.nextFloat();
                    active[i][j].color[3] = 0;
                    offsetAngle +=22.5;
                }
            }

                float offsetAngle = 0;
                for(int j=0;j<16;j++) {
                    passive[passive.length-1][j] = new CubeModel(context, "testcube", offsetAngle, false);
                    offsetAngle +=22.5;
                }

            float[] white = {1f,1f,1f,1f};
            for(int i=0;i<16;i++){
                passive[99][i].color = white;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }
    public void onDrawFrame(GL10 unused)
    {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);

        float heightoffset = 0;
        for(int i=0;i<15;i++) {
            for (int j=0; j<16;j++){
                if (!occupationMatrix[i][j])
                    continue;
                float[] scratch = new float[16];

                Matrix.setIdentityM(mRotationMatrix, 0);
                Matrix.translateM(mRotationMatrix, 0, 0f, 0f, -2.18f);
                Matrix.setRotateM(mRotationMatrix, 0, mAngle + active[i][j].angleOffset, 0, -1f, 0);
                Matrix.translateM(mRotationMatrix, 0, 0f, 0f, 2.18f);
                Matrix.scaleM(mRotationMatrix, 0, 0.5f, 0.5f, 0.5f);
                Matrix.translateM(mRotationMatrix, 0, 0f, -14f+heightoffset, 0f);
                Matrix.setLookAtM(mViewMatrix, 0, 0, 6f, -16, 0f, 0f, 0f, 0f, 1.0f, 0f);
                Matrix.perspectiveM(mProjectionMatrix, 0, 60f, 3.0f / 5.0f, 0.1f, 100f);
                Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
                Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

                GLES20.glDisable(GLES20.GL_CULL_FACE);
                active[i][j].draw(scratch);
            }
            heightoffset+=2;
        }

        heightoffset = 0;
        for(int i=99;i>=passive.length-1-completedRows;i--) {
            for (int j=0; j<16;j++){
                float[] scratch = new float[16];

                Matrix.setIdentityM(mRotationMatrix, 0);
                Matrix.translateM(mRotationMatrix, 0, 0f, 0f, -2.18f);
                Matrix.setRotateM(mRotationMatrix, 0, mAngle + passive[i][j].angleOffset, 0, -1f, 0);
                Matrix.translateM(mRotationMatrix, 0, 0f, 0f, 2.18f);
                Matrix.scaleM(mRotationMatrix, 0, 0.5f, 0.5f, 0.5f);
                Matrix.translateM(mRotationMatrix, 0, 0f, -16f-heightoffset, 0f);
                Matrix.setLookAtM(mViewMatrix, 0, 0, 6f, -16, 0f, 0f, 0f, 0f, 1.0f, 0f);
                Matrix.perspectiveM(mProjectionMatrix, 0, 60f, 3.0f / 5.0f, 0.1f, 100f);
                Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
                Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

                GLES20.glDisable(GLES20.GL_CULL_FACE);
                passive[i][j].draw(scratch);
            }
            heightoffset+=2;
        }
    }
    public void allocateLine(int layer){
        try {

            float offsetAngle = 0;
        for(int i=0;i<16;i++){
            passive[layer][i] =  new CubeModel(context, "testcube", offsetAngle, false);
            offsetAngle +=22.5;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }
}
