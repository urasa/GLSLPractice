package glslpractice;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

/**
 * 平面上に立方体を表示するデモ
 */
public class Cube extends JFrame implements GLEventListener {
    private GLUT glut = new GLUT();
    private GLU  glu  = new GLU();
    private int fps = 30;
    private FPSAnimator animator;
    private Camera camera = new Camera(0d, 30d, -45d, 0d, 0d, 0d);

	public Cube() {
		GLJPanel panel = new GLJPanel();
		GLEventListener listener = this;
		panel.addGLEventListener(listener);
        panel.addMouseListener(camera);
        panel.addMouseMotionListener(camera);
        this.addKeyListener(camera);

		getContentPane().add(panel);

		setSize(400, 400);
		setTitle(this.getClass().getName());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

        animator = new FPSAnimator(panel, fps);
        animator.start();
	}
    private int program = 0;

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        // glu.gluLookAt(30f*eyeX, 30f, 30f*eyeZ, 0f, 0f, 0f, 0f, 1f, 0f);
        camera.look(glu);

        gl.glUseProgram(program);
        drawFloor(gl);
        draw(gl);
        gl.glUseProgram(0);
        gl.glFlush();
    }
    private void drawFloor(GL2 gl) {
        gl.glColor3f(0.8f, 0.8f, 0.8f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-50f, 0f, -50f);
        gl.glVertex3f(-50f, 0f,  50f);
        gl.glVertex3f( 50f, 0f,  50f);
        gl.glVertex3f( 50f, 0f, -50f);
        gl.glEnd();
    }
    private void draw(GL2 gl) {
        gl.glColor3f(1f,1f,1f);
        gl.glPushMatrix();
        gl.glTranslatef(0f, 5f, 0f);
        glut.glutSolidCube(10f);
        gl.glPopMatrix();
    }

    public void dispose(GLAutoDrawable drawable) {}

    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.2f, 0.2f, 0.2f, 0.0f);
        // TODO シェーダープログラムの読み込み
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(30f, (float)width/(float)height, 1f, 100f);
    }

	public static void main(String[] args) {
		new Cube();
	}
}

