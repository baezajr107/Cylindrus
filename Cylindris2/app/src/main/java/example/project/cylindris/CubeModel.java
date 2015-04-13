package example.project.cylindris;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Scanner;

public class CubeModel
{
    private float pos[];
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;
    private final int vertexStride = COORDS_PER_VERTEX * 4;
    private final int mProgram;
    private float uv[];
    private float norms[];
    private short face_v[];
    private short face_vt[];
    private short face_vn[];
    private int num_verts;
    static final int COORDS_PER_VERTEX = 3;
    public float color[] = {0,0,0,0};
    public float angleOffset;
    boolean taken=false;

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    public CubeModel(Context context, String object,float offsetAngle,boolean istaken) throws Exception
    {
        taken = istaken;
        angleOffset = offsetAngle;
        Scanner inOBJ;
        AssetManager mgr = context.getAssets();
        int num_pos=0, num_uv=0, num_norms=0, num_faces=0;
//        System.out.println("Begin loading");
        inOBJ = new Scanner(new BufferedReader(new InputStreamReader(mgr.open(object + ".obj"))));
        while(inOBJ.hasNext())
        {
            String line = inOBJ.nextLine();
            String type = line.substring(0, 2);

            if(type.compareTo("v ") == 0)
                num_pos++;
            else if(type.compareTo("vt") == 0)
                num_uv++;
            else if(type.compareTo("vn") == 0)
                num_norms++;
            else if(type.compareTo("f ") == 0)
                num_faces++;
        }
        num_verts = num_faces * 3;
        inOBJ.close();
        pos = new float[num_pos*3];
//        System.out.println("pos: " + num_pos*3);
        uv = new float[num_uv*2];
//        System.out.println("uv: " + num_uv*2);
        norms = new float[num_norms*3];
//        System.out.println("norms: " + num_norms*3);
        face_v = new short[num_faces*3];
//        System.out.println("face_v: " + num_faces*3);
        face_vt = new short[num_faces*3];
//        System.out.println("face_vt: " + num_faces*3);
        face_vn = new short[num_faces*3];
//        System.out.println("face_vn: " + num_faces*3);
        Scanner OBJ;
        OBJ = new Scanner(new BufferedReader(new InputStreamReader(mgr.open(object + ".obj"))));
        int p=0,u=0,n=0,f=0;
        while(OBJ.hasNext())
        {
            String[] line = OBJ.nextLine().split("\\s");
            String type = line[0];

            if(type.compareTo("v") == 0)
            {
                for(int i=1;i<4;i++)
                {
                    pos[p]=Float.valueOf(line[i]);
                    p++;
                }
            }
            else if(type.compareTo("vt") == 0)
            {
                for(int i=1;i<3;i++)
                {
                    uv[u]=Float.valueOf(line[i]);
                    u++;
                }
            }
            else if(type.compareTo("vn") == 0)
            {
                for(int i=1;i<4;i++)
                {
                    norms[n]=Float.valueOf(line[i]);
                    n++;
                }
            }
            else if(type.compareTo("f") == 0)
            {
                for(int i=1;i<4;i++)
                {
                    face_v[f] = (short)(Integer.parseInt(line[i].substring(0, line[i].indexOf("/")))-1);
                    face_vt[f] = (short)(Character.getNumericValue(line[i].charAt(2))-1);
                    face_vn[f] = (short)(Character.getNumericValue(line[i].charAt(4))-1);
                    f++;
                }
            }
        }
        OBJ.close();
//        System.out.println(pos.toString());
//        System.out.println(uv.toString());
//        System.out.println(norms.toString());
//        System.out.println(face_v.toString());
//        System.out.println(face_vt.toString());
//        System.out.println(face_vn.toString());
        //Store model coords
        ByteBuffer bb = ByteBuffer.allocateDirect(pos.length*4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(pos);
        vertexBuffer.position(0);
        //Draw List Buffer
        ByteBuffer dlb = ByteBuffer.allocateDirect(face_v.length*2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(face_v);
        drawListBuffer.position(0);
        int vertexShader = CubeTestRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = CubeTestRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);
    }
    public void draw(float[] mvpMatrix)
    {
        GLES20.glUseProgram(mProgram);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, face_v.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
