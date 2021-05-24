package com.example.openglesdrawingbasics;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {
    GLSurfaceView glSurfaceView;
    TwoDimentionalObject twoDimentionalObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Getting the glSurfaceView handle
        glSurfaceView=findViewById(R.id.glSurfaceView);
        glSurfaceView.setEGLContextClientVersion(2);
        //Setting a rendered on the glSurfaceView
        glSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
                twoDimentionalObject = new TwoDimentionalObject();
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                GLES20.glViewport(0,0, width, height);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                twoDimentionalObject.drawTraingle();
            }
        });

    }
}