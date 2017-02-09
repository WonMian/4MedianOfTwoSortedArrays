/**
 * Created by wangmian on 16/12/25.
 */
/**思路
题目要求O(log(m+n))的时间复杂度，一般来说都是分治法或者二分搜索。首先我们先分析下题目，假设两个有序序列共有n个元素（根据中位数的定义我们要分两种情况考虑），当n为奇数时，搜寻第(n/2+1)个元素，当n为偶数时，搜寻第(n/2+1)和第(n/2)个元素，然后取他们的均值。进一步的，我们可以把这题抽象为“搜索两个有序序列的第k个元素”。如果我们解决了这个k元素问题，那中位数不过是k的取值不同罢了。
那如何搜索两个有序序列中第k个元素呢，这里又有个技巧。假设序列都是从小到大排列，对于第一个序列中前p个元素和第二个序列中前q个元素，我们想要的最终结果是：p+q等于k-1,且一序列第p个元素和二序列第q个元素都小于总序列第k个元素。因为总序列中，必然有k-1个元素小于等于第k个元素。这样第p+1个元素或者第q+1个元素就是我们要找的第k个元素。
所以，我们可以通过二分法将问题规模缩小，假设p=k/2-1，则q=k-p-1，且p+q=k-1。如果第一个序列第p个元素小于第二个序列第q个元素，我们不确定二序列第q个元素是大了还是小了，但一序列的前p个元素肯定都小于目标，所以我们将第一个序列前p个元素全部抛弃，形成一个较短的新序列。然后，用新序列替代原先的第一个序列，再找其中的第k-p个元素（因为我们已经排除了p个元素，k需要更新为k-p），依次递归。同理，如果第一个序列第p个元素大于第二个序列第q个元素，我们则抛弃第二个序列的前q个元素。递归的终止条件有如下几种：
较短序列所有元素都被抛弃，则返回较长序列的第k个元素（在数组中下标是k-1）
一序列第p个元素等于二序列第q个元素，此时总序列第p+q=k-1个元素的后一个元素，也就是总序列的第k个元素
注意
每次递归不仅要更新数组起始位置（起始位置之前的元素被抛弃），也要更新k的大小（扣除被抛弃的元素）*/
public class Solution {
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int k = (m + n) / 2;
        if((m+n)%2==0){
            return (findKth(nums1,nums2,0,0,m,n,k)+findKth(nums1,nums2,0,0,m,n,k+1))/2;
        }   else {
            return findKth(nums1,nums2,0,0,m,n,k+1);
        }

    }

    private static double findKth(int[] arr1, int[] arr2, int start1, int start2, int len1, int len2, int k){
        if(len1>len2){
            return findKth(arr2,arr1,start2,start1,len2,len1,k);
        }
        if(len1==0){
            return arr2[start2 + k - 1];
        }
        if(k==1){
            return Math.min(arr1[start1],arr2[start2]);
        }
        int p1 = Math.min(k/2,len1) ;
        int p2 = k - p1;
        if(arr1[start1 + p1-1]<arr2[start2 + p2-1]){
            return findKth(arr1,arr2,start1 + p1,start2,len1-p1,len2,k-p1);
        } else if(arr1[start1 + p1-1]>arr2[start2 + p2-1]){
            return findKth(arr1,arr2,start1,start2 + p2,len1,len2-p2,k-p2);
        } else {
            return arr1[start1 + p1-1];
        }
    }

    public static void main(String[] args) {
        int[] arr1 = {1,2,3,4};
        int[] arr2 = {2,3,5};
        System.out.println(findMedianSortedArrays(arr1,arr2));
    }
}

