package example.project.cylindris;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class CubeTestRenderer implements GLSurfaceView.Renderer
{
    //GLModel WILL be used in final version, imports and represents .obj files
    public Context context;
    public CubeModel[][] test = new CubeModel[15][16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];
    public volatile float mAngle;
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
        // initialize a cube
        try {




            for(int i=0;i<15;i++) {
                int offsetAngle = 180;
                float[] colors = {0.1f,0.1f,0.1f,1f};
                for(int j=0;j<16;j++) {
                    test[i][j] = new CubeModel(context, "testcube", offsetAngle, false);
                    test[i][j].color[0] = .2f;
                    test[i][j].color[1] = .2f;
                    test[i][j].color[2] = .2f;
                    test[i][j].color[3] = .5f;
                    offsetAngle +=24.5;
                }
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
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        float heightoffset = 0;
        for(int i=0;i<test.length;i++) {
            for (int j = 0;j<test[i].length;j++) {
                if (!test[i][j].taken)
                    continue;
                float[] scratch = new float[16];

                // Redraw background color

                // Set the camera position (View matrix)

                //Projection Matrix, offset, FOV, aspect ratio, near clipping, far clipping

                // Calculate the projection and view transformation
                Matrix.setIdentityM(mRotationMatrix, 0);
                Matrix.translateM(mRotationMatrix, 0, 0f, 0f, -2.8f);
                Matrix.setRotateM(mRotationMatrix, 0, mAngle + test[i][j].angleOffset, 0, -1f, 0);
                Matrix.translateM(mRotationMatrix, 0, 0f, 0f, 2.8f);
                Matrix.scaleM(mRotationMatrix, 0, 0.5f, 0.5f, 0.5f);
                Matrix.translateM(mRotationMatrix, 0, 0f, -14f+heightoffset, 0f);


                Matrix.setLookAtM(mViewMatrix, 0, 0, 7f, -19, 0f, 0f, 0f, 0f, 1.0f, 0f);
//        //Projection Matrix, offset, FOV, aspect ratio, near clipping, far clipping
                Matrix.perspectiveM(mProjectionMatrix, 0, 60f, 3.0f / 5.0f, 0.1f, 100f);
//        // Calculate the projection and view transformation
                Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
                Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

//        Matrix.setRotateM(mMVPMatrix, 0, mAngle, 0, -1f, 0);
//        Matrix.scaleM(mMVPMatrix, 0, 0.1f, 0.1f, 1.0f);
//        // Combine the rotation matrix with the projection and camera view
//        // Note that the mMVPMatrix factor *must be first* in order
//        // for the matrix multiplication product to be correct.
//        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
                // Draw shape

                //GLES20.glDisable(GLES20.GL_CULL_FACE);
                test[i][j].draw(scratch);
            }
            heightoffset+=2.02;
        }
    }
    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }
}
