package dip_project.gui;

import dip_project.ImageLoader;
import dip_project.processing.Processing;
import dip_project.transform.*;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
    private JPanel jContentPane = null;
    private JDesktopPane dskCenter = null;
    private JMenuBar mbMenu = null;
    private JMenu mnuFile = null;
    private JMenu mnuEdit = null;

    private JMenu mnuAbout = null;
    private JMenuItem mniOpen = null;
    private JMenuItem mniAbout = null;
    private JMenuItem mniExit = null;
    private JFileChooser openDialog = null;
    private JFileChooser saveDialog = null;

    private ImageLoader loader = new ImageLoader(); 
    private static final String[] SUPPORTED_FORMATS = {"png", "jpg", "jpeg",
            "png", "gif", "tiff", "tif", "bmp"};
    
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
            mbMenu.add(getMnuAbout());
        }
        return mbMenu;
    }

    private JMenu getMnuFile() {
        if (mnuFile == null) {
            mnuFile = new JMenu();
            mnuFile.setText("File");
            mnuFile.add(getMniOpen());
            mnuFile.addSeparator();
            mnuFile.add(getMniExit());
        }
        return mnuFile;
    }

    private JMenu getMnuEdit() {
        if (mnuEdit == null) {
            mnuEdit = new JMenu();
            mnuEdit.setText("Edit");
            mnuEdit.add(getMnuFlip());
            mnuEdit.addSeparator();
            mnuEdit.add(getMnuShrink());
            mnuEdit.add(getMnuGrow());
            mnuEdit.addSeparator();
            mnuEdit.add(getMniCropToBorder());
        }
        return mnuEdit;
    }

    private JMenu getMnuFlip() {
        if (mnuFlip == null) {
            mnuFlip = new JMenu();
            mnuFlip.setText("Flip");
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
        }
        return mnuShrink;
    }

    private JMenuItem getMniShrink2x() {
        if (mniShrink2x == null) {
            mniShrink2x = new JMenuItem();
            mniShrink2x.setText("1/2");
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

    private JMenuItem getMniFlipVertical() {
        if (mniFlipVertical == null) {
            mniFlipVertical = new JMenuItem();
            mniFlipVertical.setText("Vertical");
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
            mniCropToBorder
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            applyProcessing("Just borders", new CropToBorder());
                        }
                    });
        }
        return mniCropToBorder;
    }

    private JMenu getMnuAbout() {
        if (mnuAbout == null) {
            mnuAbout = new JMenu();
            mnuAbout.setText("About");
            mnuAbout.add(getMniAbout());
        }
        return mnuAbout;
    }

    private JMenuItem getMniOpen() {
        if (mniOpen == null) {
            mniOpen = new JMenuItem();
            mniOpen.setText("Open...");
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

    private JMenuItem getMniAbout() {
        if (mniAbout == null) {
            mniAbout = new JMenuItem();
            mniAbout.setText("About...");
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
            mniExit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    onClose();
                }
            });
        }
        return mniExit;
    }

    protected void onClose() {
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