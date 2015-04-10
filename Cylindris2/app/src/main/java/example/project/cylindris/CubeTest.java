package example.project.cylindris;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;

import example.project.cylindris.ImportTestRenderer;
public class CubeTest extends Activity {
    private GLSurfaceView mGLView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create an OpenGL ES view
        mGLView = new CubeTestSurfaceView(this);
        //Set view to mGLView
        setContentView(mGLView);
    }
}
class CubeTestSurfaceView extends GLSurfaceView
{
    private final CubeTestRenderer  mRenderer;
    private float mPreviousX;
    private float mPreviousY;
    private final float TOUCH_SCALE_FACTOR = 60.0f / 320;
    public CubeTestSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mRenderer = new CubeTestRenderer();
        mRenderer.context = context;
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        // Render the view only when there is a change in the drawing data
        // Turn off for continuous render when no user events ex. touch
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.


        switch (e.getAction()) {
            case MotionEvent.ACTION_UP:

                float x = e.getX();
                float y = e.getY();
                DisplayMetrics metrics = this.getContext().getResources().getDisplayMetrics();
                int height = metrics.heightPixels;
                int width = metrics.widthPixels;

                //left or right
                if(y<=height*.8 && y>=height*.2) {
                    float rotateAngle = -24f;
                    if (x > width / 2)
                        rotateAngle = -1*rotateAngle;

                    mRenderer.setAngle(mRenderer.getAngle() + rotateAngle);
                }
                //down
                else if(y>height*.8){

                }
                //rotate
                else if(y<height*.2){



                }
                requestRender();
            break;
        }

        return true;
    }
}
