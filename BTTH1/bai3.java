import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Scanner;

class Point implements Comparable<Point>{
    int x, y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int compareTo(Point other) {
        if (this.y == other.y) {
            return Integer.compare(this.x, other.x);
        }
        return Integer.compare(this.y, other.y);
    }
}

public class bai3 {
    
    public static int crossProduct(Point a, Point b, Point c){
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }

    public static double distance(Point a, Point b){
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    public static List<Point> convexHull(List<Point> points) {
        if (points.size() < 3) return points; // Ít hơn 3 điểm thì tất cả đều là bao lồi

        // Tìm điểm có y nhỏ nhất (hoặc x nhỏ nhất nếu trùng y)
        Collections.sort(points);
        Point pivot = points.get(0);

        // Sắp xếp các điểm theo góc cực với pivot
        points.sort((p1, p2) -> {
            int cp = crossProduct(pivot, p1, p2);
            if (cp == 0) { // Nếu cùng góc, chọn điểm gần hơn
                return Double.compare(distance(pivot, p1), distance(pivot, p2));
            }
            return -Integer.compare(cp, 0);
        });

        // Dùng Stack để xác định bao lồi
        Stack<Point> hull = new Stack<>();
        for (Point p : points) {
            while (hull.size() >= 2 && crossProduct(hull.get(hull.size() - 2), hull.peek(), p) <= 0) {
                hull.pop();
            }
            hull.push(p);
        }

        return new ArrayList<>(hull);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // Số lượng trạm phát
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            points.add(new Point(x, y));
        }

        List<Point> hull = convexHull(points);

        for (Point p : hull) {
            System.out.println(p.x + " " + p.y);
        }

        scanner.close();
    }
}
