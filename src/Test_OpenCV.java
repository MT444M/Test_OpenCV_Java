import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.*;

import java.util.ArrayList;
import java.util.List;


public class Test_OpenCV {
    public static void main(String[] args) {
        // Charger la bibliothèque OpenCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // Charger une image
        Mat image = Imgcodecs.imread("C:\\Users\\mthia\\Desktop\\TP_JAVA\\OpenCV_Test\\src\\Lenna.png");
    }

    // Méthode pour convertir l'image en niveaux de gris
    public static Mat convertToGray(Mat image) {
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        return grayImage;
    }


    // Méthode pour calculer l'histogramme de l'image
    public static Mat computeHistogram(Mat image) {
        List<Mat> images = new ArrayList<>();
        images.add(image);
        Mat histogram = new Mat();
        MatOfInt channels = new MatOfInt(0);
        MatOfInt histSize = new MatOfInt(256);
        MatOfFloat range = new MatOfFloat(0, 256);

        Imgproc.calcHist(images, channels, new Mat(), histogram, histSize, range);

        return histogram;
    }
}

