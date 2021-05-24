package com.example.openglesdrawingbasics;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TwoDimentionalObject {
    //This variable will contain the vertices (x,y,z)
    float[] triangleVertices;
    float [] vertexColors;
    //Variables for the mvpMatrix
    float[] projectionMatrix = new float[16];
    float[] viewMatrix = new float[16];
    float[] productMatrix = new float[16];
    //This variable will store the total no of vertices
    int vertexCount=3;//3 because this is a tringle
    int vertexStride=vertexCount*4;
    //This variable will store the total no of colors
    int colorCount=3;
    int colorStride=colorCount*4;
    //This variable will be used as the vertex buffer
    FloatBuffer vertexBuffer;
    //This variable will be used as the color buffer
    FloatBuffer colorBuffer;
    //This variable will store the vertex shader code
    String vertexShaderCode;
    //This variable will store the fragmentShaderCode
    String fragmentShaderCode;
    //These are the handles
    int programHandle;
    int vertexShaderHandle;
    int fragmentShaderHandle;
    //Variable Handles
    int mPositionHandle;
    int mMVPMatrixHandle;
    int mColorHandle;


    public TwoDimentionalObject(){
        //**********specifying the triangle co-ordinates using a floating array starts**************//
        triangleVertices=
                new float[]{0, 1, 1,
                        -1, -1, 1,
                        1, -1, 1};

        //**********specifying the triangle co-ordinates using a floating array ends**************//

        //*******Specifying the vertex color using the floating array starts************//
        vertexColors=
                new float[]{
                        1, 0, 0,
                        0, 1, 0,
                        0, 0, 1,
                };
        //*******Specifying the vertex color using the floating array ends************//

        //****************Setting up the projection matrix starts*****************//
        Matrix.frustumM(projectionMatrix, 0,
                -1, 1,
                -1, 1,
                2, 9);
        Matrix.setLookAtM(viewMatrix, 0,
                0, 3, -4,
                0, 0, 0,
                0, 1, 0);
        Matrix.multiplyMM(productMatrix, 0,
                projectionMatrix, 0,
                viewMatrix, 0);
        //******************Setting up the projection matrix ends*****************//

        //******Going to make a vertex buffer placing the co-ordinates starts**********//
        //It is going to store the point values in float size spaces and hence making spaces
        ByteBuffer byteBuffer=ByteBuffer.allocateDirect(triangleVertices.length*4);
        //Ordering the contents of the byte buffer
        byteBuffer.order(ByteOrder.nativeOrder());
        //Turning the vertex buffer into a float buffer
        vertexBuffer =byteBuffer.asFloatBuffer();
        //Placing the floating point array in the vertex buffer
        vertexBuffer.put(triangleVertices);
        //Repositioning the buffer contents
        vertexBuffer.position(0);
        //******Going to make a vertex buffer placing the co-ordinates ends**********//

        //******Going to make a vertex buffer for placing the color starts**********//
        //It is going to store the point values in float size spaces and hence making spaces
        ByteBuffer byteBuffer2=ByteBuffer.allocateDirect(vertexColors.length*4);
        //Ordering the contents of the byte buffer
        byteBuffer2.order(ByteOrder.nativeOrder());
        //Turning the vertex buffer into a float buffer
        colorBuffer =byteBuffer2.asFloatBuffer();
        //Placing the floating point array in the vertex buffer
        colorBuffer.put(vertexColors);
        //Repositioning the buffer contents
        colorBuffer.position(0);
        //******Going to make a vertex buffer for placing the color ends**********//


        //********************Preparing the Shading Programs Start********************//
        vertexShaderCode =
                "attribute vec3 aVertexPosition;"+
                        "uniform mat4 uMVPMatrix;"+
                        "attribute vec4 vertexColorFromMainProgram;"+
                        "varying vec4 vColor;" +
                        "void main() {"+
                        "gl_Position = uMVPMatrix *vec4(aVertexPosition,1.0);" +
                        "vColor=vertexColorFromMainProgram;}";
        fragmentShaderCode =
                "precision mediump float;"+
                "varying vec4 vColor; "+
                "void main() {"+
                        "gl_FragColor = vColor;}";
        //********************Preparing the Shading Programs Ends********************//

        //************************COMPILING THE SHADER PROGRAMS STARTS*********************//
        //Also I am obtaining handler to the shader programs

        vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertexShaderHandle, vertexShaderCode);

        fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShaderHandle, fragmentShaderCode);

        GLES20.glCompileShader(vertexShaderHandle);
        GLES20.glCompileShader(fragmentShaderHandle);
        //************************COMPILING THE SHADER PROGRAMS ENDS*********************//

        //********CREATING THE MAIN PROGRAM AND ATTACHING IT WITH SHADERS STARTS****//
        programHandle=GLES20.glCreateProgram();
        //ATTACHING
        GLES20.glAttachShader(programHandle,vertexShaderHandle);
        GLES20.glAttachShader(programHandle,fragmentShaderHandle);
        //LINKING
        GLES20.glLinkProgram(programHandle);
        GLES20.glUseProgram(programHandle);
        //********CREATING THE MAIN PROGRAM AND ATTACHING IT WITH SHADERS ENDS****//

        //**********GETTING HANDLE TO THE VARIABLES STARTS*************//
        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "aVertexPosition");
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        Log.d("TAG1",mPositionHandle+"<- position handle");
        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "uMVPMatrix");
        //Enable the handle
        GLES20.glEnableVertexAttribArray(mMVPMatrixHandle);
        Log.d("TAG1",mMVPMatrixHandle+"<- matrix handle");
        //get handle to the color variable
        mColorHandle= GLES20.glGetAttribLocation(programHandle, "vertexColorFromMainProgram");
        //Enable the handle
        GLES20.glEnableVertexAttribArray(mColorHandle);
        Log.d("TAG1",mColorHandle+"<- color handle");
        //**********GETTING HANDLE TO THE VARIABLES STARTS*************//
    }

    void drawTraingle(){
        //Placing the product matrix values into shader matrix variable
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, productMatrix, 0);
        //Placing the position buffer into the shader position variable
        GLES20.glVertexAttribPointer(mPositionHandle, vertexCount,
                GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        //Placing the position buffer into the shader position variable
        GLES20.glVertexAttribPointer(mColorHandle, colorCount,
                GLES20.GL_FLOAT, false, colorStride, colorBuffer);
        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

    }
}
