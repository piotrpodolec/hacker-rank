import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(firstMultipleInput[0]);
        int n = Integer.parseInt(firstMultipleInput[1]);
        int r = Integer.parseInt(firstMultipleInput[2]);

        List<List<Integer>> matrix = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                matrix.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        Result.matrixRotation(matrix, r, m, n);

        bufferedReader.close();
    }
}

class Result {
    public static void matrixRotation(List<List<Integer>> matrix, int r, int m, int n) {
        for (int i = 0; i < m; i++){
            for (int j = 0; j < n; j++){
                int depth = Math.min(j, Math.min(i, Math.min(m-i-1, n-j-1)));
                int vMax = m - 1 - depth;
                int hMax = n - 1 - depth;
                int toMove = r % (2 * (n + m  - 2) - 8 * depth); // Removing excessive interations in the while loop;
                int currentV = i;
                int currentH = j;
                while (toMove != 0){
                    if (currentV == depth && currentH != hMax) { // on top of the ring
                        if (toMove <= hMax - currentH) {
                            // moving right with all that left and breaking the loop;
                            currentH += toMove;
                            break;
                        } else {
                            // moving right to the edge;
                            toMove -= hMax - currentH;
                            currentH = hMax;
                        }
                    }
                    if (currentH == hMax && currentV != vMax) { // on the right of the ring
                        if (toMove <= vMax - currentV) {
                            // moving down with all that left and breaking the loop;
                            currentV += toMove;
                            break;
                        } else {
                            // moving down to the edge;
                            toMove -= vMax - currentV;
                            currentV = vMax;
                        }
                    }
                    if (currentV == vMax && currentH != depth) {// on the bottom of the ring
                        if (toMove <= currentH - depth) {
                            // moving left with all that left and breaking the loop;
                            currentH -= toMove;
                            break;
                        } else {
                            // moving left to the edge;
                            toMove -= currentH - depth;
                            currentH = depth;
                        }
                    }
                    if (currentH == depth && currentV != depth) { // on the left of the ring
                        if (toMove <= currentV - depth) {
                            // moving up with all that left and breaking the loop;
                            currentV -= toMove;
                            break;
                        } else {
                            // moving up to the edge;
                            toMove -= currentV - depth;
                            currentV = depth;
                        }
                    }
                }
                System.out.print(matrix.get(currentV).get(currentH));
                if (j != n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.printf("\n");
        }
    }
// subtracting depth from m and n, at the beginning and adding it at the end could be prettier but it will be harder to get in to the logic;
}
