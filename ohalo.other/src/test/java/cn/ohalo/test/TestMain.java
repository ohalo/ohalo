package cn.ohalo.test;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

public class TestMain extends TestCase {


	public void aRondDom() {

		// 1是红球，2是篮球

		int[] hab = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 };

		int i = hab.length;

		while (i / 2 > 0) {

			// System.out.println("i:" + i);

			int a = RandomUtils.nextInt(i);
			int b = RandomUtils.nextInt(i - 1);

			if (a == b) {
				continue;
			}

			System.out.println(a + "," + b + ",i:" + i + ",球：" + hab[a] + ",球："
					+ hab[b]);
			if (hab[a] == hab[b]) {
				hab = removeArray(hab, a, b);
			} else {
				hab = removeLqiu(hab);
			}
			i = hab.length;
		}

		for (int ai : hab) {
			System.out.print(" 所有数字:" + ai);
		}

	}
	
	@Test
	public void testa() throws Exception {
		System.out.println("testa");
	}

	public int[] removeArray(int[] array, int a, int b) {
		int[] array1 = new int[array.length - 2];

		int j = 0;

		for (int i = 0; i < array.length; i++) {
			if (i == a || i == b) {
				continue;
			}

			array1[j] = array[i];
			j++;
		}

		return array1;
	}

	public int[] removeLqiu(int[] array) {
		int[] array1 = new int[array.length - 1];

		int j = 0;

		boolean flag = false;

		for (int i = 0; i < array.length; i++) {

			if (!flag && array[i] == 2) {
				flag = true;
				continue;
			}

			array1[j] = array[i];
			j++;
		}

		return array1;
	}

	public void aNumThree() {
		int[] childs = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int site = 3, f = 0 + 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
		System.out.println(clac(childs, site, f));
	}

	public int clac(int[] childs, int startSite, int site) {
		for (int s = 1; s <= 3 * (childs.length - 1); s++) {
			site = s % 3 == 0 ? site - startSite : site;
			startSite = startSite > childs.length - 2 ? 0 : ++startSite;
		}
		return childs[site];
	}

	/**
	 * 1 1,2 3 1 3,2 1 1 3,3 4 1 4,3 3 <br>
	 * 
	 * 
	 */
	public void aSort() {

		int a[] = new int[100000];

		for (int i = 0; i < a.length; i++) {
			a[i] = (int) (10000 + Math.random() * (100000 + 1));
		}

		System.out.println("开始时间：" + new Date().getTime());

		bubbleSort(a);
		System.out
				.println("结束时间：" + new Date().getTime() + ",a的大小：" + a.length);

		System.out.println(a[0] + "," + a[1]);
	}

	/**
	 * 冒泡排序
	 * 
	 * 
	 * 100000 条记录 for * for = 2390 - 0585 ms <br>
	 * 100000 条记录 for * for 排序 = 29178 - 13960 ms <br>
	 * 100000 条记录 加入temp变量 for * for 排序 = 37393 - 23810 ms
	 * 
	 * @param a
	 *            数组a
	 */
	public void bubbleSort(int a[]) {
		int temp = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[i] < a[j]) {
					temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
	}

	/**
	 * 选择排序
	 * 
	 * 
	 * 100000 条记录 for * for = 2390 - 0585 ms <br>
	 * 100000 条记录 for * for 排序 = 6229 - 2624 ms
	 * 
	 * @param a
	 */
	public void selectSort(int[] a) {
		for (int i = 0; i < a.length; i++) {
			int index = i;
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] < a[index]) {
					index = j;
				}
			}

			a[i] = a[index] + a[i];
			a[index] = a[i] - a[index];
			a[i] = a[i] - a[index];
		}
	}

	public void insertSort(int[] a) {
		int endsite = 1, currentsite, temp;

		for (int i = 0; i < a.length; i++) {
		}
	}
}
