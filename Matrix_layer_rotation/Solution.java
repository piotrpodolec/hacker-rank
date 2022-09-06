import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.toList;

public class Solution {
    public static void main(String[] args) throws IOException {
        InputParser inputParser = new InputParser();
        inputParser.readInputStream();

        Rotator rotator = new Rotator();
        rotator.rotateMatrix(
                inputParser.getMatrix(),
                inputParser.getRotations(),
                inputParser.getMatrixHeight(),
                inputParser.getMatrixWidth()
        );
    }
}

class InputParser {
    private List<List<Integer>> matrix;
    private int matrixHeight;
    private int matrixWidth;
    private int rotations;
    public void readInputStream() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        matrixHeight = Integer.parseInt(firstMultipleInput[0]);
        matrixWidth = Integer.parseInt(firstMultipleInput[1]);
        rotations = Integer.parseInt(firstMultipleInput[2]);
        matrix = new ArrayList<>();

        bufferedReader.close();

        IntStream.range(0, matrixHeight).forEach(i -> {
            try {
                matrix.add(
                        Stream.of(bufferedReader
                                .readLine()
                                .replaceAll("\\s+$", "")
                                .split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public int getMatrixHeight() {
        return matrixHeight;
    }

    public int getMatrixWidth() {
        return matrixWidth;
    }

    public int getRotations() {
        return rotations;
    }

    public List<List<Integer>> getMatrix() {
        return matrix;
    }
}

class Rotator {
    private int currentRing;
    private int ringHeight;
    private int ringWidth;
    private int ringMovement;
    private int HeightPointer;
    private int WidthPointer;
    public void rotateMatrix(List<List<Integer>> matrix, int rotations, int height, int width) {
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                currentRing = Math.min(j, Math.min(i, Math.min(height-i-1, width-j-1)));
                ringHeight = height - 1 - currentRing;
                ringWidth = width - 1 - currentRing;
                ringMovement = rotations % (2 * (width + height  - 2) - 8 * currentRing);
                calculateCellSource(i, j);
                System.out.print(matrix.get(HeightPointer).get(WidthPointer));
                if (j != width - 1) {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    private void calculateCellSource(int i, int j) {
        HeightPointer = i;
        WidthPointer = j;
        while (ringMovement != 0){
            // on the top of the ring
            if (HeightPointer == currentRing && WidthPointer != ringWidth) {
                if (ringMovement <= ringWidth - WidthPointer) {
                    // move right with all that left and break the loop;
                    WidthPointer += ringMovement;
                    break;
                } else {
                    // move right to the edge and continue down;
                    ringMovement -= ringWidth - WidthPointer;
                    WidthPointer = ringWidth;
                }
            }
            // on the right side of the ring
            if (WidthPointer == ringWidth && HeightPointer != ringHeight) {
                if (ringMovement <= ringHeight - HeightPointer) {
                    // moving down with all that left and breaking the loop;
                    HeightPointer += ringMovement;
                    break;
                } else {
                    // moving down to the edge and continue left;
                    ringMovement -= ringHeight - HeightPointer;
                    HeightPointer = ringHeight;
                }
            }
            // on the bottom of the ring
            if (HeightPointer == ringHeight && WidthPointer != currentRing) {
                if (ringMovement <= WidthPointer - currentRing) {
                    // moving left with all that left and breaking the loop;
                    WidthPointer -= ringMovement;
                    break;
                } else {
                    // moving left to the edge and continue up;
                    ringMovement -= WidthPointer - currentRing;
                    WidthPointer = currentRing;
                }
            }
            // on the left side of the ring
            if (WidthPointer == currentRing && HeightPointer != currentRing) {
                if (ringMovement <= HeightPointer - currentRing) {
                    // moving up with all that left and breaking the loop;
                    HeightPointer -= ringMovement;
                    break;
                } else {
                    // moving up to the edge and continue right in the next iteration of while loop;
                    ringMovement -= HeightPointer - currentRing;
                    HeightPointer = currentRing;
                }
            }
        }
    }
}
