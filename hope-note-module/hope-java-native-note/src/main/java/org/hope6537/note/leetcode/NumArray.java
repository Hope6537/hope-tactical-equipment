package org.hope6537.note.leetcode;

public class NumArray {

    public int [] nums;

    public NumArray(int[] nums) {
        this.nums = nums;
    }

    public int sumRange(int i, int j) {
        int sum = nums[j];
        while(i!=j){
            sum += nums[i];
            i++;
        }
        return sum;
    }

    public static void main(String[] args) {
        NumArray numArray = new NumArray(new int[]{-2,0,3,-5,2,-1});
        System.out.println(numArray.sumRange(0, 1));
        System.out.println(numArray.sumRange(1, 2));
        System.out.println(numArray.sumRange(0, 2));
        System.out.println(numArray.sumRange(2, 5));
        System.out.println(numArray.sumRange(0, 5));

    }
}