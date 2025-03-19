import java.util.Random;

//  Monte Carlo dùng tỉ lệ diện tích với r = 1: (số điểm trong đường tròn / tổng số điểm ≈ (π * r^2) / 4 )
public class bai2 {
    public static double pi(int numSamples) {
        Random rand = new Random();
        int insideCircle = 0;

        for (int i = 0; i < numSamples; i++) {
            double x = rand.nextDouble() * 2 - 1; // Random từ -1 đến 1
            double y = rand.nextDouble() * 2 - 1; // Random từ -1 đến 1

            if (x * x + y * y <= 1) { 
                insideCircle++;
            }
        }

        return 4.0 * insideCircle / numSamples; 
    }

    public static void main(String[] args) {
        int numSamples = 1000000; 

        System.out.println("Giá trị xấp xỉ của π: " + pi(numSamples));
    }
}
