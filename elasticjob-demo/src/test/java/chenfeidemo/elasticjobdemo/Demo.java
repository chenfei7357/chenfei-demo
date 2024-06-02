package chenfeidemo.elasticjobdemo;

import com.google.common.collect.Maps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class Demo {

	public  static    boolean finishFlag = false;


	static ThreadLocal<String> local1 = new ThreadLocal<>();

	static ThreadLocal<String> local2 = new ThreadLocal<>();


	public static void main(String[] args) throws InterruptedException {

		int [] a ={1,9,1,1,5,6,2,9,4,2,1};
		int length=a.length;
		int result =0;
		TreeSet<Integer> set = new TreeSet<>();
		for (int i=0;i<length;i++) {
			result+=a[i];
			for (int j = i+1; j < length; j++) {
				result+=a[j];
				if(result>=12){
					result=0;
					System.out.println(j - i + 1);
					set.add(j - i + 1);
					break;
				}
			}
		}
		System.out.println("最小的："+set.first());
	}


	public static    String reverse(String str) {
		System.out.println("str:"+str);
		if (str == null || str.length() <= 1) {
			return str;
		}
		char charAt = str.charAt(0);
		System.out.println("charAt:"+charAt);
		String re = reverse(str.substring(1)) ;
		System.out.println("re:"+re);
		String s = re + charAt;
		System.out.println("s:"+s);
		return s;
	}

	//bca
	//


	public int getMiddle(int[] list, int low, int high) {

		int tmp = list[low];    //数组的第一个作为中轴

		while (low < high) {

			while (low < high && list[high] >= tmp) {

				high--;

			}

			list[low] = list[high];   //比中轴小的记录移到低端

			while (low < high && list[low] <= tmp) {

				low++;

			}

			list[high] = list[low];   //比中轴大的记录移到高端

		}

		list[low] = tmp;              //中轴记录到尾

		return low;                   //返回中轴的位置

	}


}
