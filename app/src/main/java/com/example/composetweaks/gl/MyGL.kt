package com.example.composetweaks.gl

import android.content.Context
import android.opengl.GLES31
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer : GLSurfaceView.Renderer {

    private lateinit var triangle: Triangle

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val rotationMatrix = FloatArray(16)


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES31.glClearColor(0f, 0f, 0f, 1f)
        triangle = Triangle()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES31.glViewport(0, 0, width, height)
        val ratio: Float = width.toFloat() / height.toFloat()

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT)
        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        val scratch = FloatArray(16)

        // Create a rotation transformation for the triangle
        val time = SystemClock.uptimeMillis() % 4000L
        val angle = 0.2f * time.toInt()
        Matrix.setRotateM(rotationMatrix, 0, angle, 0f, 0f, -1.0f)

        // Combine the rotation matrix with the projection and camera view
        // Note that the vPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0)

        // Draw triangle
        triangle.draw(scratch)
    }


}

class MyGlSurfaceView(context: Context) : GLSurfaceView(context) {
    private val renderer: MyGLRenderer



    override fun performClick(): Boolean {
        val temp = true
        return super.performClick()
    }

    init {
        setEGLContextClientVersion(3)
        renderer = MyGLRenderer()
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
    }
}

fun loadShader(type: Int, shaderCode: String): Int {

    // create a vertex shader type (GLES31.GL_VERTEX_SHADER)
    // or a fragment shader type (GLES31.GL_FRAGMENT_SHADER)
    return GLES31.glCreateShader(type).also { shader ->
        // add the source code to the shader and compile it
        GLES31.glShaderSource(shader, shaderCode)
        GLES31.glCompileShader(shader)
    }
}

class Triangle {
    private var mProgram: Int = 0
    private val vertexShaderCode = // This matrix member variable provides a hook to manipulate
        // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                // the matrix must be included as a modifier of gl_Position
                // Note that the uMVPMatrix factor *must be first* in order
                // for the matrix multiplication product to be correct.
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}"

    // Use to access and set the view transformation
    private var vPMatrixHandle: Int = 0

    private val fragmentShaderCode =
        "precision mediump float;" +
                "uniform vec4 vColor;" +
                "void main() {" +
                "  gl_FragColor = vColor;" +
                "}"
    // number of coordinates per vertex in this array
    private val COORDS_PER_VERTEX = 3
    private var triangleCoords = floatArrayOf(     // in counterclockwise order:
        0.0f, 0.5f, 0.0f,      // top
        -0.5f, -0.5f, 0.0f,    // bottom left
        0.5f, -0.5f, 0.0f      // bottom right
    )

    // Set color with red, green, blue and alpha (opacity) values
    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

    private var positionHandle: Int = 0
    private var mColorHandle: Int = 0

    private val vertexCount: Int = triangleCoords.size / COORDS_PER_VERTEX
    private val vertexStride: Int = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    init {

        val vertexShader: Int = loadShader(GLES31.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES31.GL_FRAGMENT_SHADER, fragmentShaderCode)

        // create empty OpenGL ES Program
        mProgram = GLES31.glCreateProgram().also {

            // add the vertex shader to program
            GLES31.glAttachShader(it, vertexShader)

            // add the fragment shader to program
            GLES31.glAttachShader(it, fragmentShader)

            // creates OpenGL ES program executables
            GLES31.glLinkProgram(it)
        }
    }

    private var vertexBuffer: FloatBuffer =
        // (number of coordinate values * 4 bytes per float)
        ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
            // use the device hardware's native byte order
            order(ByteOrder.nativeOrder())

            // create a floating point buffer from the ByteBuffer
            asFloatBuffer().apply {
                // add the coordinates to the FloatBuffer
                put(triangleCoords)
                // set the buffer to read the first coordinate
                position(0)
            }
        }

    fun draw(mvpMatrix: FloatArray) {
        // Add program to OpenGL ES environment
        GLES31.glUseProgram(mProgram)

        // get handle to vertex shader's vPosition member
        positionHandle = GLES31.glGetAttribLocation(mProgram, "vPosition").also {

            // Enable a handle to the triangle vertices
            GLES31.glEnableVertexAttribArray(it)

            // Prepare the triangle coordinate data
            GLES31.glVertexAttribPointer(
                it,
                COORDS_PER_VERTEX,
                GLES31.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )

            // get handle to fragment shader's vColor member
            mColorHandle = GLES31.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->

                // Set color for drawing the triangle
                GLES31.glUniform4fv(colorHandle, 1, color, 0)
            }

            // Draw the triangle
            GLES31.glDrawArrays(GLES31.GL_TRIANGLES, 0, vertexCount)

            // Disable vertex array
            GLES31.glDisableVertexAttribArray(it)
        }
        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES31.glGetUniformLocation(mProgram, "uMVPMatrix")

        // Pass the projection and view transformation to the shader
        GLES31.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)

        // Draw the triangle
        GLES31.glDrawArrays(GLES31.GL_TRIANGLES, 0, vertexCount)

        // Disable vertex array
        GLES31.glDisableVertexAttribArray(positionHandle)
    }
}

@Composable
fun OpenGLComposable(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            JupiterShaderSurfaceView(context)
        }
    )
}