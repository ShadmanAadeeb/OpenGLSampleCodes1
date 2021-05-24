# OpenGLSampleCodes1
In this repository, the code for drawing a <strong>simple shape with colors</strong> have been presented.<br>
Code Snippets from the class TwoDimentionalObject.java can be utilized for future uses

The work flow of the code is as follows:
<ol>
<li>Floating point arrays are created for storing vertices and color values</li>
<li>The Projection Matrix is created</li>
<li>The contents of floating point arrays are placed in buffers</li>
<li>Fragment Shading and Vertex Shading programs are created as strings</li>
<li>The main program is created and the shader programs are attached to it. A handle to the main program is obtained</li>
<li>The handles to the variables in the shader programs are obtained</li>
<li>In the draw function, the values are passed from the java class to the shader program variables</li>
<li>Here glDrawArray function has been used. This function is used when there is no index buffer</li>
</ol>

<br>
You will find lots of comments in the code snippets that will be in unison with the above work flow
