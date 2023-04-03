package com.scheduler.n11.demo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinarySearchSubset {

    public static int[] binarySearchSubset(int[] a, int targetSum) {
        // Sort the array a in ascending order
        Arrays.sort(a);

        // Define a helper function to find the closest subset
        int[] closestSubset = a.clone();
        int closestSum = Arrays.stream(a).sum();

        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j <= n; j++) {
                int[] subset = Arrays.copyOfRange(a, i, j);
                int subsetSum = Arrays.stream(subset).sum();
                if (Math.abs(subsetSum - targetSum) < Math.abs(closestSum - targetSum)) {
                    closestSubset = subset;
                    closestSum = subsetSum;
                }
            }
        }

        // Perform binary search to find the subset
        int left = 0;
        int right = n - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int[] leftSubset = closestSubset(a, 0, mid, targetSum);
            int[] rightSubset = closestSubset(a, mid+1, n-1, targetSum);
            int leftSum = Arrays.stream(leftSubset).sum();
            int rightSum = Arrays.stream(rightSubset).sum();
            if (leftSum == targetSum) {
                return leftSubset;
            } else if (rightSum == targetSum) {
                return rightSubset;
            } else if (Math.abs(leftSum - targetSum) < Math.abs(rightSum - targetSum)) {
                closestSubset = leftSubset;
                right = mid - 1;
            } else {
                closestSubset = rightSubset;
                left = mid + 1;
            }
        }

        // Return the closest subset
        return closestSubset;
    }

    private static int[] closestSubset(int[] a, int left, int right, int targetSum) {
        int[] closestSubset = Arrays.copyOfRange(a, left, right+1);
        int closestSum = Arrays.stream(closestSubset).sum();
        int n = right - left + 1;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j <= n; j++) {
                int[] subset = Arrays.copyOfRange(a, left+i, left+j);
                int subsetSum = Arrays.stream(subset).sum();
                if (Math.abs(subsetSum - targetSum) < Math.abs(closestSum - targetSum)) {
                    closestSubset = subset;
                    closestSum = subsetSum;
                }
            }
        }
        return closestSubset;
    }

    public List<Integer> binarySearch(List<Integer> durationList,int targetSum) {
        int[] durationArray = durationList.stream().mapToInt(Integer::intValue).toArray();
        int[] subset = binarySearchSubset(durationArray, targetSum);
        return new ArrayList<>(Arrays.stream(subset).boxed().toList());
    }
}


