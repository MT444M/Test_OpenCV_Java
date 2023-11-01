import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import org.opencv.core.*;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;



public class OpnCV_GUI extends JDialog {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton to_GrayButton;
    private JButton contourButton;
    private JPanel Display;

    private JLabel image;
    private JButton imageOrigButton;

    public OpnCV_GUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        to_GrayButton.addActionListener(e -> {
            // Load the original image
            Mat originalImage = Imgcodecs.imread("Lenna.png");

            // Convert the image to grayscale
            Mat grayImage = new Mat();
            Imgproc.cvtColor(originalImage, grayImage, Imgproc.COLOR_BGR2GRAY);

            // Convert the grayscale image to a BufferedImage
            BufferedImage grayBufferedImage = Mat2BufferedImage(grayImage);

            // Update the JLabel with the grayscale image
            ImageIcon icon = new ImageIcon(grayBufferedImage);
            image.setIcon(icon);
        });

        contourButton.addActionListener(e -> {
            // Load the grayscale image
            Mat grayImage = Imgcodecs.imread("Lenna.png",Imgcodecs.IMREAD_GRAYSCALE);

            // Detect edges in the grayscale image
            Mat edges = new Mat();
            Imgproc.Canny(grayImage, edges, 100, 100);

            // Convert the edge image to a BufferedImage
            BufferedImage edgeBufferedImage = Mat2BufferedImage(edges);

            // Update the JLabel with the edge image
            ImageIcon icon = new ImageIcon(edgeBufferedImage);
            image.setIcon(icon);
        });


        imageOrigButton.addActionListener(e -> {
            // Load the original image
            Mat oriImage = Imgcodecs.imread("Lenna.png");

            // Update the JLabel with the original image
            ImageIcon icon = new ImageIcon(Mat2BufferedImage2(oriImage));
            image.setIcon(icon);

        });



    }
    // Convert a Mat to a BufferedImage
    private BufferedImage Mat2BufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        int dataSize = mat.cols() * mat.rows() * (int) mat.elemSize();
        byte[] data = new byte[dataSize];
        mat.get(0, 0, data);
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
        return image;
    }
    private BufferedImage Mat2BufferedImage2(Mat mat) {
        int type = BufferedImage.TYPE_3BYTE_BGR; // For BGR color images
        int width = mat.cols();
        int height = mat.rows();
        int channels = mat.channels();
        byte[] data = new byte[width * height * channels];
        mat.get(0, 0, data);
        BufferedImage image = new BufferedImage(width, height, type);

        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int b = data[index] & 0xFF;
                int g = data[index + 1] & 0xFF;
                int r = data[index + 2] & 0xFF;
                int rgb = (r << 16) | (g << 8) | b;
                image.setRGB(x, y, rgb);
                index += channels;
            }
        }
        return image;
    }



    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    public static void main(String[] args) {
        OpnCV_GUI dialog = new OpnCV_GUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
