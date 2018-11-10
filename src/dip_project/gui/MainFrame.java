package dip_project.gui;

import dip_project.ImageLoader;
//import dip_project.color.ARGBChannels;
//import dip_project.color.ColorChannel;
//import dip_project.color.GrayScaleChannel;
import dip_project.processing.Processing;
import dip_project.transform.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    private JPanel jContentPane = null;
    private JDesktopPane dskCenter = null;
    private JMenuBar mbMenu = null;
    private JMenu mnuFile = null;
    private JMenu mnuEdit = null;

    private JMenu mnuHelp = null;
    private JMenuItem mniOpen = null;
    private JMenuItem mniSaveAs = null;
    private JMenuItem mniAbout = null;
    private JMenuItem mniExit = null;
    private JFileChooser openDialog = null;
    private JFileChooser saveDialog = null;

    private ImageLoader loader = new ImageLoader(); 
    private static final String[] SUPPORTED_FORMATS = {"png", "jpg", "jpeg",
            "png", "gif", "tiff", "tif", "bmp"};
    
    private JMenuItem mniNegative;
    private JMenuItem mniRotate90;
    private JMenuItem mniRotate180;
    private JMenuItem mniRotate270;
    private JMenuItem mniFlipVertical;
    private JMenuItem mniFlipHorizontal;
    private JMenu mnuRotate;
    private JMenuItem mniFlipBoth;
    private JMenu mnuFlip;
    private JMenu mnuGrow;
    private JMenu mnuShrink;
    private JMenuItem mniShrink2x;
    private JMenuItem mniShrink4x;
    private JMenuItem mniShrink8x;
    private JMenuItem mniGrow2x;
    private JMenuItem mniGrow4x;
    private JMenuItem mniGrow8x;
    private JMenuItem mniSubtract;
    private JMenuItem mniCropToBorder;

    private JDesktopPane getDskCenter() {
        if (dskCenter == null) {
            dskCenter = new JDesktopPane();
        }
        return dskCenter;
    }

    private JMenuBar getMbMenu() {
        if (mbMenu == null) {
            mbMenu = new JMenuBar();
            mbMenu.add(getMnuFile());
            mbMenu.add(getMnuEdit());
            mbMenu.add(getMnuHelp());
        }
        return mbMenu;
    }

    private JMenu getMnuFile() {
        if (mnuFile == null) {
            mnuFile = new JMenu();
            mnuFile.setText("File");
            mnuFile.setMnemonic(KeyEvent.VK_F);
            mnuFile.add(getMniOpen());
            mnuFile.add(getMniSaveAs());
            mnuFile.addSeparator();
            mnuFile.add(getMniExit());
        }
        return mnuFile;
    }

    private JMenu getMnuEdit() {
        if (mnuEdit == null) {
            mnuEdit = new JMenu();
            mnuEdit.setText("Edit");
            mnuEdit.setMnemonic(KeyEvent.VK_E);
            mnuEdit.add(getMnuRotate());
            mnuEdit.add(getMnuFlip());
            mnuEdit.addSeparator();
            mnuEdit.add(getMnuShrink());
            mnuEdit.add(getMnuGrow());
            mnuEdit.addSeparator();
            mnuEdit.add(getMniCropToBorder());
        }
        return mnuEdit;
    }

    private JMenu getMnuRotate() {
        if (mnuRotate == null) {
            mnuRotate = new JMenu();
            mnuRotate.setText("Rotate");
            mnuRotate.setMnemonic(KeyEvent.VK_R);
            mnuRotate.add(getMniRotate90());
            mnuRotate.add(getMniRotate180());
            mnuRotate.add(getMniRotate270());
        }
        return mnuRotate;
    }

    private JMenu getMnuFlip() {
        if (mnuFlip == null) {
            mnuFlip = new JMenu();
            mnuFlip.setText("Flip");
            mnuFlip.setMnemonic(KeyEvent.VK_F);
            mnuFlip.add(getMniFlipVertical());
            mnuFlip.add(getMniFlipHorizontal());
            mnuFlip.add(getMniFlipBoth());
        }
        return mnuFlip;
    }

    private JMenu getMnuGrow() {
        if (mnuGrow == null) {
            mnuGrow = new JMenu();
            mnuGrow.setText("Enlarge");
            mnuGrow.setMnemonic(KeyEvent.VK_E);
            mnuGrow.add(getMniGrow2x());
            mnuGrow.add(getMniGrow4x());
            mnuGrow.add(getMniGrow8x());
        }
        return mnuGrow;
    }

    private JMenu getMnuShrink() {
        if (mnuShrink == null) {
            mnuShrink = new JMenu();
            mnuShrink.setText("Shrink");
            mnuShrink.add(getMniShrink2x());
            mnuShrink.add(getMniShrink4x());
            mnuShrink.add(getMniShrink8x());
            mnuShrink.setMnemonic(KeyEvent.VK_H);
        }
        return mnuShrink;
    }

    private JMenuItem getMniShrink2x() {
        if (mniShrink2x == null) {
            mniShrink2x = new JMenuItem();
            mniShrink2x.setText("1/2");
            mniShrink2x.setMnemonic(KeyEvent.VK_2);
            mniShrink2x.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    applyProcessing("Shrink to 1/2", new TransformImage(getSelectedFrame()
                            .getImage(), 0.5));
                }
            });
        }
        return mniShrink2x;
    }

    private JMenuItem getMniShrink4x() {
        if (mniShrink4x == null) {
            mniShrink4x = new JMenuItem();
            mniShrink4x.setText("1/4");
            mniShrink4x.setMnemonic(KeyEvent.VK_4);
            mniShrink4x.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    applyProcessing("Shrink to 1/4", new TransformImage(getSelectedFrame()
                            .getImage(), 0.25));
                }
            });
        }
        return mniShrink4x;
    }

    private JMenuItem getMniShrink8x() {
        if (mniShrink8x == null) {
            mniShrink8x = new JMenuItem();
            mniShrink8x.setText("1/8");
            mniShrink8x.setMnemonic(KeyEvent.VK_8);
            mniShrink8x.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    applyProcessing("Shrink to 1/8", new TransformImage(getSelectedFrame()
                            .getImage(), 0.125));
                }
            });
        }
        return mniShrink8x;
    }

    private JMenuItem getMniGrow2x() {
        if (mniGrow2x == null) {
            mniGrow2x = new JMenuItem();
            mniGrow2x.setText("2x");
            mniGrow2x.setMnemonic(KeyEvent.VK_2);
            mniGrow2x.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    applyProcessing("Enlarge 2x", new TransformImage(getSelectedFrame()
                            .getImage(), 2));
                }
            });
        }
        return mniGrow2x;
    }

    private JMenuItem getMniGrow4x() {
        if (mniGrow4x == null) {
            mniGrow4x = new JMenuItem();
            mniGrow4x.setText("4x");
            mniGrow4x.setMnemonic(KeyEvent.VK_4);
            mniGrow4x.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    applyProcessing("Enlarge 4x", new TransformImage(getSelectedFrame()
                            .getImage(), 4));
                }
            });
        }
        return mniGrow4x;
    }

    private JMenuItem getMniGrow8x() {
        if (mniGrow8x == null) {
            mniGrow8x = new JMenuItem();
            mniGrow8x.setText("8x");
            mniGrow8x.setMnemonic(KeyEvent.VK_8);
            mniGrow8x.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    applyProcessing("Enlarge 8x", new TransformImage(getSelectedFrame()
                            .getImage(), 8));
                }
            });
        }
        return mniGrow8x;
    }

    private JMenuItem getMniRotate90() {
        if (mniRotate90 == null) {
            mniRotate90 = new JMenuItem();
            mniRotate90.setText("Rotate 90 degrees");
            mniRotate90.setMnemonic(KeyEvent.VK_9);
            mniRotate90.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    applyProcessing("Rota��o 90 graus", new RotateImage(Rotation.R90));
                }
            });
        }
        return mniRotate90;
    }

    private JMenuItem getMniRotate180() {
        if (mniRotate180 == null) {
            mniRotate180 = new JMenuItem();
            mniRotate180.setText("Rotate 180 degrees");
            mniRotate180.setMnemonic(KeyEvent.VK_1);
            mniRotate180.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    applyProcessing("Rotate 180 degrees", new RotateImage(Rotation.R180));
                }
            });
        }
        return mniRotate180;
    }

    private JMenuItem getMniRotate270() {
        if (mniRotate270 == null) {
            mniRotate270 = new JMenuItem();
            mniRotate270.setText("Rotate 270 degrees");
            mniRotate270.setMnemonic(KeyEvent.VK_2);
            mniRotate270.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    applyProcessing("Rotate 270 degrees", new RotateImage(Rotation.R270));
                }
            });
        }
        return mniRotate270;
    }

    private JMenuItem getMniFlipVertical() {
        if (mniFlipVertical == null) {
            mniFlipVertical = new JMenuItem();
            mniFlipVertical.setText("Vertical");
            mniFlipVertical.setMnemonic(KeyEvent.VK_V);
            mniFlipVertical
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            applyProcessing("Flip vertical", new TransformImage(true, false));
                        }
                    });
        }
        return mniFlipVertical;
    }

    private JMenuItem getMniFlipHorizontal() {
        if (mniFlipHorizontal == null) {
            mniFlipHorizontal = new JMenuItem();
            mniFlipHorizontal.setText("Horizontal");
            mniFlipHorizontal.setMnemonic(KeyEvent.VK_H);
            mniFlipHorizontal
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            applyProcessing("Flip horizontal", new TransformImage(false, true));
                        }
                    });
        }
        return mniFlipHorizontal;
    }

    private JMenuItem getMniFlipBoth() {
        if (mniFlipBoth == null) {
            mniFlipBoth = new JMenuItem();
            mniFlipBoth.setText("Both");
            mniFlipBoth.setMnemonic(KeyEvent.VK_H);
            mniFlipBoth.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    applyProcessing("Flip both", new TransformImage(true, true));
                }
            });
        }
        return mniFlipBoth;
    }

    private JMenuItem getMniCropToBorder() {
        if (mniCropToBorder == null) {
            mniCropToBorder = new JMenuItem();
            mniCropToBorder.setText("Just borders");
            mniCropToBorder.setMnemonic(KeyEvent.VK_B);
            mniCropToBorder
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            applyProcessing("Just borders", new CropToBorder());
                        }
                    });
        }
        return mniCropToBorder;
    }


    private List<String> getImageNames(boolean sameSize) {
        List<String> imagens = new ArrayList<String>();
        for (JInternalFrame frame : getDskCenter().getAllFrames())
            if (frame instanceof ImagemIFrame) {
                if (sameSize) {
                    BufferedImage selected = getSelectedFrame().getImage();
                    BufferedImage other = ((ImagemIFrame) frame).getImage();
                    if (other.getWidth() != selected.getWidth()
                            || other.getHeight() != selected.getHeight())
                        continue;
                }
                imagens.add(frame.getTitle());
            }
        return imagens;
    }

    private BufferedImage chooseImage(String title, String text,
                                      boolean sameSize) {
        List<String> imagens = getImageNames(sameSize);
        imagens.remove(getSelectedFrame().getTitle());

        if (imagens.size() == 0)
            return null;

        String imgName = (String) JOptionPane.showInputDialog(MainFrame.this,
                text, title, JOptionPane.QUESTION_MESSAGE, null,
                imagens.toArray(), imagens.get(0));

        if (imgName == null)
            return null;

        BufferedImage img = null;
        for (JInternalFrame frame : getDskCenter().getAllFrames())
            if (frame instanceof ImagemIFrame
                    && frame.getTitle().equals(imgName))
                img = ((ImagemIFrame) frame).getImage();
        return img;
    }

    private JMenu getMnuHelp() {
        if (mnuHelp == null) {
            mnuHelp = new JMenu();
            mnuHelp.setText("Help");
            mnuHelp.setMnemonic(KeyEvent.VK_H);
            mnuHelp.add(getMniAbout());
        }
        return mnuHelp;
    }

    private JMenuItem getMniOpen() {
        if (mniOpen == null) {
            mniOpen = new JMenuItem();
            mniOpen.setText("Open...");
            mniOpen.setMnemonic(KeyEvent.VK_O);
            mniOpen.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    onOpen();
                }
            });
        }
        return mniOpen;
    }

    public JFileChooser getOpenDialog() {
        if (openDialog == null) {
            openDialog = new JFileChooser();
            openDialog.setAcceptAllFileFilterUsed(false);
            openDialog.setFileFilter(new FileNameExtensionFilter("Image files",
                    SUPPORTED_FORMATS));
        }
        return openDialog;
    }

    protected void onOpen() {
        if (getOpenDialog().showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
            return;

        openFile(getOpenDialog().getSelectedFile(), true);
    }

    public JFileChooser getSaveDialog() {
        if (saveDialog == null) {
            saveDialog = new JFileChooser();
            saveDialog.setAcceptAllFileFilterUsed(false);
            saveDialog.setFileFilter(new FileNameExtensionFilter("Image files",
                    SUPPORTED_FORMATS));
        }
        return saveDialog;
    }

    private ImagemIFrame getSelectedFrame() {
        return (ImagemIFrame) getDskCenter().getSelectedFrame();
    }

    private void onSaveAs() {
        ImagemIFrame frame = getSelectedFrame();

        if (frame == null)
            return;

        if (frame.getFile() != null)
            getSaveDialog().setSelectedFile(frame.getFile());

        if (getSaveDialog().showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
            return;

        try {
            String fileName = getSaveDialog().getSelectedFile().getName();
            String format;
            int index = fileName.lastIndexOf(".");
            if (index == -1) {
                fileName = fileName + ".png";
                format = "png";
            } else {
                format = fileName.substring(index + 1);
            }

            File file = new File(getSaveDialog().getSelectedFile()
                    .getParentFile(), fileName);
            ImageIO.write(frame.getImage(), format, file);
            frame.setFile(file);
            setTitle(frame);
        } catch (IOException e) {
            showError("Could not save file!");
        }

    }

    private void openFile(File file, boolean showError) {
        try {
            BufferedImage img = loader.load(file);
            openWindow("", file, img);
        } catch (Exception e) {
            if (showError)
                showError("Could not open the selected file.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void setTitle(JInternalFrame frame) {
        setTitle("Image editor - " + frame.getTitle());
    }

    private void openWindow(String name, File selectedFile, BufferedImage img) {
        Point p = getSelectedFrame() == null ? new Point(0, 0)
                : getSelectedFrame().getLocation();
        ImagemIFrame frame = new ImagemIFrame(name, selectedFile, img);
        frame.setLocation(p.x + 20, p.y + 20);
        frame.setVisible(true);
        getDskCenter().add(frame);
        frame.moveToFront();
        try {
            frame.setSelected(true);
        } catch (PropertyVetoException e) {
            throw new RuntimeException(e);
        }
        setTitle(frame);

        frame.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                getDskCenter().remove(e.getInternalFrame());
            }

            @Override
            public void internalFrameActivated(InternalFrameEvent e) {
                setTitle(e.getInternalFrame());
            }
        });
    }

    private JMenuItem getMniSaveAs() {
        if (mniSaveAs == null) {
            mniSaveAs = new JMenuItem();
            mniSaveAs.setText("Save as...");
            mniSaveAs.setMnemonic(KeyEvent.VK_A);
            mniSaveAs.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    onSaveAs();
                }
            });
        }
        return mniSaveAs;
    }

    private JMenuItem getMniAbout() {
        if (mniAbout == null) {
            mniAbout = new JMenuItem();
            mniAbout.setText("About...");
            mniAbout.setMnemonic(KeyEvent.VK_A);
            mniAbout.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    new AboutDialog(MainFrame.this).setVisible(true);
                }
            });
        }
        return mniAbout;
    }

    private JMenuItem getMniExit() {
        if (mniExit == null) {
            mniExit = new JMenuItem();
            mniExit.setText("Exit");
            mniExit.setMnemonic(KeyEvent.VK_R);
            mniExit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    onClose();
                }
            });
        }
        return mniExit;
    }

    protected void onClose() {
        if (JOptionPane
                .showConfirmDialog(
                        this,
                        "<html><body>Unsaved data can be lost<br>Are you sure?",
                        "Close software", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_NO_OPTION)
            return;

        System.exit(0);
    }

    public static boolean isImage(String fileName) {
        for (String format : SUPPORTED_FORMATS)
            if (fileName.endsWith("." + format))
                return true;
        return false;
    }

    protected void applyProcessing(String name, Processing filter) {
        if (getSelectedFrame() == null)
            return;
        openWindow(name, null, new FilterProgressDialog(this).showModal(filter,
                getSelectedFrame().getImage()));
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame mainFrame = new MainFrame();
                for (String arg : args) {
                    if (!isImage(arg))
                        continue;

                    File f = new File(arg);
                    if (f.exists() && f.isFile())
                        mainFrame.openFile(f, false);
                }
                mainFrame.setVisible(true);
            }
        });
    }

    public MainFrame() {
        super();
        initialize();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initialize() {
        this.setSize(800, 600);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setJMenuBar(getMbMenu());
        this.setContentPane(getJContentPane());
        this.setTitle("Image Editor");
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                onClose();
            }
        });
    }

    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getDskCenter(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

}
