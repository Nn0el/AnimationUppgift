import com.sun.org.apache.regexp.internal.RE;
import sun.font.TrueTypeGlyphMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class AnimationUppgift extends Canvas implements Runnable {
    private BufferStrategy bs;

    private boolean running = false;
    private Thread thread;
    int x = 0;
    int y = 0;
    Rectangle wall = new Rectangle(225, 175, 160, 80);
    private BufferedImage Haunt;
    private int HauntX = 100;
    private int HauntY = 100;
    private int HauntVX = 0;
    private int HauntVY = 0;

    /*public Grafik() {
        try {
            Haunt = ImageIO.read(new File("PixelHaunter.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(600, 400);
        JFrame frame = new JFrame();
        frame.add(this);
        frame.addKeyListener(new MyKeyListener());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }*/

    public AnimationUppgift() {
        try {
            Haunt = ImageIO.read(new File("PixelHaunter.png"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setSize(600, 400);
        JFrame frame = new JFrame();
        frame.add(this);
        frame.addKeyListener(new MyKeyListener());
        this.addMouseMotionListener(new MyMouseMotionListener());
        this.addMouseListener(new MyMouseListener());
        requestFocus();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


    public void render() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        // Rita ut den nya bilden
        draw(g);

        g.dispose();
        bs.show();
    }


    public void draw(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.fillRect(0, 0, 600, 60);
        g.fillRect(0, 350, 600, 60);
        g.fillRect(0, 0, 60, 350);
        g.fillRect(540, 0, 60, 350);
        g.fillRect(225, 175, 160, 80);
        g.drawImage(Haunt, HauntX, HauntY, Haunt.getWidth() / 4, Haunt.getHeight() / 4, null);
    }


    public static void main(String[] args) {
        AnimationUppgift minGrafik = new AnimationUppgift();
        minGrafik.start();
    }

    public synchronized void start() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        double ns = 1000000000.0 / 25.0;
        double delta = 0;
        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                // Uppdatera koordinaterna
                update();
                // Rita ut bilden med updaterad data
                render();
                delta--;
            }
        }
        stop();
    }


    private void update() {
        HauntX += HauntVX;
        HauntY += HauntVY;
    }
        public class MyMouseMotionListener implements MouseMotionListener {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        }

        public class MyMouseListener implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        }

        public class MyKeyListener implements KeyListener {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            HauntVX = -3;
        }
        if (e.getKeyChar() == 'd') {
            HauntVX = 3;
        }
        if (e.getKeyChar() == 'w') {
            HauntVY = -3;
        }
        if (e.getKeyChar() == 's') {
            HauntVY = 3;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() == 'a') {
            HauntVX = 0;
        }
        if (e.getKeyChar() == 'd') {
            HauntVX = 0;
        }
        if (e.getKeyChar() == 'w') {
            HauntVY = 0;
        }
        if (e.getKeyChar() == 's') {
            HauntVY = 0;
        }
    }
}


