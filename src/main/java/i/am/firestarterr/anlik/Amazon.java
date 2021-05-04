package i.am.firestarterr.anlik;

import java.util.Arrays;


public class Amazon {

    public static void main(String[] args) {
        Solution solution = new Solution();
// System.out.println(solution.solution(1, 8, 3, 2, 6, 4));
// System.out.println(solution.solution(0, 0, 0, 0, 0, 0));
// System.out.println(solution.solution(2, 4, 5, 9, 5, 9));
// System.out.println(solution.solution(2, 3, 5, 9, 5, 9));
// System.out.println(solution.solution(0, 0, 0, 7, 8, 9));
        int[] A;
        A = new int[]{2, 4, 1, 6, 5, 9, 7};
        System.out.println(solution.solution(A));
        A = new int[]{4, 3, 2, 6, 1};
        System.out.println(solution.solution(A));
        A = new int[]{2, 1, 6, 4, 3, 7};
        System.out.println(solution.solution(A));

    }
}

class Solution {
    public int solution(int[] A) {
        int x = 1;
        int max = Math.max(A[0], A[1]);
        for (int i = 2; i < A.length; i++) {
            if (A[i] > max) {
                for (int j = i + 1; j < A.length; j++) {
                    if (A[j] < max)
                        return x;
                }
                x++;
                if (i > A.length - 2)
                    return x;
                max = Math.max(A[i], A[i + 1]);
                i++;
            }
        }
        return x;
    }
}

class Solution_old {
    public int solution(int[] A) {
        int sliceCount = 1;
        int pointer = 0;
        while (pointer < A.length) {
            int x = A[pointer];
            int y = A[++pointer];
            int max = Math.max(x, y);
            int subPointer = pointer + 1;
            boolean isSmallFound = true;
            while (isSmallFound) {
                isSmallFound = false;
                for (int j = subPointer; j < A.length; j++) {
                    if (A[j] < max) {
                        isSmallFound = true;
                        subPointer = j + 1;
                        break;
                    }
                }
            }
            sliceCount++;
            pointer = subPointer;
        }
        return sliceCount - 1;
    }
}


class Solution2 {
    public String solution(int A, int B, int C, int D, int E, int F) {
        int[] inputs = {A, B, C, D, E, F};
        Arrays.sort(inputs);
        if (inputs[0] < 2 || (inputs[0] == 2 && inputs[1] < 4)) {
            if (inputs[2] < 6) {
                if (inputs[3] < 6) {
                    return "" + inputs[0] + inputs[1] + ":" + inputs[2] + inputs[4] + ":" + inputs[3] + inputs[5];
                } else if (inputs[1] < 6) {
                    return "" + inputs[0] + inputs[3] + ":" + inputs[1] + inputs[4] + ":" + inputs[2] + inputs[5];
                }
            }
        }
        return "NOT POSSIBLE";
    }
}