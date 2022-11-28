package com.kartikey.hackerrank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int n = 7;
        int[] array = {1, 2, 1, 2, 1, 3, 2};

        // array = new int[]{10, 20, 20, 10, 10, 30, 50, 10, 20};
        List<Integer> ar = new ArrayList<>();
        for (int j : array) {
            ar.add(j);
        }

        System.out.println(sockMerchant(n, ar));
    }

    public static int sockMerchant(int n, List<Integer> ar) {
        int pairCount = 0;
        HashMap<String, List<Integer>> colors = new HashMap<>();

        for (Integer i : ar) {
            String key = i.toString();
            if (!colors.containsKey(key)) {
                colors.put(key, new ArrayList<>());
            } else {
                List<Integer> values = colors.get(key);
                if (values.size() > 1) {
                    colors.put(key, new ArrayList<>());
                    pairCount++;
                } else {
                    values.add(i);
                    colors.put(key, values);
                }
            }
        }

        return pairCount;
    }
}
