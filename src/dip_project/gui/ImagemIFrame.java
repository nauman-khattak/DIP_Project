package dip_project.gui;

import dip_project.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;

@SuppressWarnings("serial")
public class ImagemIFrame extends JInternalFrame {
    private double zoomRate[] = {0.125, 0.25, 0.5, 0.75, 1, 1.25, 1.5, 2.0,
            4.0};
    private String zoomTitle[] = {"1/8", "1/4", "1/2", "3/4", "", "125%",
            "1.5x", "2x", "4x"};
    private JPanel jContentPane = null;
    private JLabel lblImagem = null;
    private File file;
    private BufferedImage image;
    private JScrollPane srclImagem = null;
    private static int counter = 1;
    private int zoom = 4;
    
    public ImagemIFrame(String title, File file, BufferedImage image) {
        super();
        initialize();
        if (file != null)
            title = file.getName();
        else {
            title = String.format("%03d - %s", counter, title);
            counter++;
        }
        setTitle(title);

        lblImagem.setIcon(new ImageIcon(image));
        this.file = file;
        this.image = image;
        lblImagem.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (!e.isControlDown())
                    return;
                if (e.getWheelRotation() < 0)
                    setZoomLevel(zoom + 1);
                else if (e.getWheelRotation() > 0)
                    setZoomLevel(zoom - 1);

            }
        });
        pack();
    }

    public void setZoomLevel(int zoomLevel) {
        zoomLevel = Math.min(zoomRate.length - 1, zoomLevel);
        zoomLevel = Math.max(0, zoomLevel);
        zoom = zoomLevel;

        BufferedImage img = ImagemIFrame.this.image;
        lblImagem.setIcon(new ImageIcon(Util.newTransformedImage(img,
                (int) (img.getWidth() * zoomRate[zoom]),
                (int) (img.getHeight() * zoomRate[zoom]), false, false)));
        setTitle(title + " " + zoomTitle[zoom]);

        pack();
    }

    private void initialize() {
        this.setSize(300, 200);
        this.setClosable(true);
        this.setIconifiable(true);
        this.setMaximizable(true);
        this.setResizable(true);
        this.setContentPane(getJContentPane());
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {

            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getSrclImagem(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    public File getFile() {
        return file;
    }

    public BufferedImage getImage() {
        return image;
    }

    private JScrollPane getSrclImagem() {
        if (srclImagem == null) {
            srclImagem = new JScrollPane();
            lblImagem = new JLabel();
            lblImagem.setText("");
            lblImagem.setBackground(Color.white);
            lblImagem.setOpaque(true);
            lblImagem.setHorizontalAlignment(SwingConstants.CENTER);
            srclImagem.setViewportView(lblImagem);

        }
        return srclImagem;
    }

    public void setFile(File file) {
        this.file = file;
        setTitle(file.getName());
    }
}
