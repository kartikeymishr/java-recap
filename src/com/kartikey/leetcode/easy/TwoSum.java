package com.kartikey.leetcode.easy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. Two Sum
 */
public class TwoSum {

    /**
     * Brute Force approach
     * Time complexity - O(n^2)
     * Space complexity - O(1)
     */
    public static int[] twoSumBF(int[] nums, int target) {
        int[] result = new int[2];

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

        return result;
    }

    /**
     * 1 Pass Hash Table
     * Time complexity - O(n)
     * Space complexity - O(n)
     */
    public static int[] twoSum1PassHT(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int compliment = target - nums[i];
            if (map.containsKey(compliment)) {
                return new int[]{map.get(compliment), i};
            }

            map.put(nums[i], i);
        }

        return null;
    }
}
