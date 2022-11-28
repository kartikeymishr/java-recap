package com.kartikey.leetcode.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * 3. Longest Substring Without Repeating Characters
 */
public class LongestSubstringWithoutRepeatingCharacters {

    /**
     * Sliding Window
     * Time complexity O(2n) = O(n) | In worst case
     * Space complexity O(min(n, m))
     */
    public static int lengthOfLongestSubstringSW(String s) {
        Map<Character, Integer> chars = new HashMap<>();
        int left = 0;
        int right = 0;

        int res = 0;
        while (right < s.length()) {
            char r = s.charAt(right);
            chars.put(r, chars.getOrDefault(r, 0) + 1);

            while (chars.get(r) > 1) {
                char l = s.charAt(left);
                chars.put(l, chars.get(l) - 1);
                left++;
            }

            res = Math.max(res, right - left + 1);
            right++;
        }

        return res;
    }

    /**
     * Brute Force
     * Time complexity O(n^3)
     * Space complexity O(min(n, m))
     */
    public static int lengthOfLongestSubstringBF(String s) {
        int n = s.length();
        int res = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (check(s, i, j)) res = Math.max(res, j - i + 1);
            }
        }

        return res;
    }

    private static boolean check(String s, int start, int end) {
        int[] chars = new int[128];

        for (int i = start; i < end; i++) {
            char c = s.charAt(i);
            chars[c]++;
            if (chars[c] > 1) return false;
        }

        return true;
    }
}
