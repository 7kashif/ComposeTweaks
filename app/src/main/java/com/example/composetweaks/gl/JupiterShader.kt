package com.example.composetweaks.gl

import android.content.Context
import android.opengl.GLES31
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class JupiterShaderRenderer(
    private val context: Context
) : GLSurfaceView.Renderer {
    private var program: Int = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES31.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        program = loadShaders("vertex_shader.glsl", "fragment_shader.glsl")
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES31.glViewport(0, 0, width, height)
        // Set the iResolution uniform
        val resolutionLocation = GLES31.glGetUniformLocation(program, "iResolution")
        GLES31.glUniform2f(resolutionLocation, width.toFloat(), height.toFloat())

        // Set the iTime uniform (use System.nanoTime() or another method for time)
        val timeLocation = GLES31.glGetUniformLocation(program, "iTime")
        GLES31.glUniform1f(timeLocation, (System.currentTimeMillis() % 10000L).toFloat() / 1000f)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT)

        // Use the shader program
        GLES31.glUseProgram(program)

        // Render the scene
        GLES31.glDrawArrays(GLES31.GL_TRIANGLES, 0, 6)  // Rendering a full-screen quad
    }

    private fun loadShaders(vertexCode: String, fragmentCode: String): Int {
        val vertexShader = GLES31.glCreateShader(GLES31.GL_VERTEX_SHADER)
        GLES31.glShaderSource(vertexShader, context.readFromAssets(vertexCode))
        GLES31.glCompileShader(vertexShader)

        val fragmentShader = GLES31.glCreateShader(GLES31.GL_FRAGMENT_SHADER)
        GLES31.glShaderSource(fragmentShader, context.readFromAssets(fragmentCode))
        GLES31.glCompileShader(fragmentShader)

        val program = GLES31.glCreateProgram()
        GLES31.glAttachShader(program, vertexShader)
        GLES31.glAttachShader(program, fragmentShader)
        GLES31.glLinkProgram(program)

        return program
    }
}

class JupiterShaderSurfaceView(context: Context) : GLSurfaceView(context) {
    private val renderer: JupiterShaderRenderer

    init {
        setEGLContextClientVersion(3)
        renderer = JupiterShaderRenderer(context)
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
    }
}

//fun compileShader(context: Context, type: Int, fileName: String): Int {
//    val shader = GLES31.glCreateShader(type)
//    GLES31.glShaderSource(shader, context.readFromAssets(fileName))
//    GLES31.glCompileShader(shader)
//
//    val compileStatus = IntArray(1)
//    GLES31.glGetShaderiv(shader, GLES31.GL_COMPILE_STATUS, compileStatus, 0)
//    if (compileStatus[0] == 0) {
//        GLES31.glDeleteShader(shader)
//        throw RuntimeException("Error compiling shader: " + GLES31.glGetShaderInfoLog(shader))
//    }
//
//    return shader
//}

private fun Context.readFromAssets(fileName: String): String {
    try {
        val inputStream = assets.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()

        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            stringBuilder.append(line).append("\n")
        }

        bufferedReader.close()
        inputStream.close()

        return stringBuilder.toString()
    } catch (e: IOException) {
        throw RuntimeException(e)
    }
}

