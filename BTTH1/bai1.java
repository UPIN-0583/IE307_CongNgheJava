import java.util.Random;
import java.util.Scanner;


// Monte Carlo dùng tỉ lệ diện tích : (số điểm trong đường tròn / tổng số điểm ) ≈ (diện tích tròn / diện tích vuông)
public class bai1 {
    public static double CircleArea(double r, int numSamples) {
        Random rand = new Random();
        int insideCircle = 0;

        for (int i = 0; i < numSamples; i++) {
            double x = rand.nextDouble() * 2 * r - r; // Random từ -r đến r
            double y = rand.nextDouble() * 2 * r - r; // Random từ -r đến r

            if (x * x + y * y <= r * r) { 
                insideCircle++;
            }
        }

        return 4 * r * r * ((double) insideCircle / numSamples); 
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập bán kính r: ");
        double r = scanner.nextDouble();
        
        int numSamples = 1000000; 

        System.out.println("Diện tích xấp xỉ của hình tròn: " + CircleArea(r, numSamples));
        
        scanner.close();
    }
}
