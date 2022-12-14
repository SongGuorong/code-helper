package com.sgr.easy;

import java.util.HashMap;
import java.util.Map;

public class _01_twoSum {

    /**
     * 暴力枚举
     */
    public int[] twoSum(int[] nums, int target) {
        for (int i=0; i<nums.length; i++) {
            for (int j=i+1; j<nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i,j};
                }
            }
        }
        return new int[0];
    }

    /**
     * 哈希表 O(N)
     * 保留之前查询结果信息
     */
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i=0; i<nums.length; i++) {
            if (map.containsKey(target-nums[i])) {
                return new int[] {map.get(target-nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }

}
