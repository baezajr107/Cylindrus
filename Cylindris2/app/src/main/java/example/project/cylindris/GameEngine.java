package example.project.cylindris;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;

import example.project.cylindris.ImportTestRenderer;
public class GameEngine extends Activity {
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
class GameEngineView extends GLSurfaceView
{
    private final CubeTestRenderer  mRenderer;
    private float mPreviousX;
    private float mPreviousY;
    private final float TOUCH_SCALE_FACTOR = 10;
    public GameEngineView(Context context) {
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

        float x = e.getX();
//        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
//                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
//                if (y > getHeight() / 2) {
//                    dx = dx * -1 ;
//                }

                // reverse direction of rotation to left of the mid-line
//                if (x < getWidth() / 2) {
//                    dy = dy * -1 ;
//                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx) * TOUCH_SCALE_FACTOR));
                requestRender();
        }

        mPreviousX = x;
//        mPreviousY = y;
        return true;
    }
}
