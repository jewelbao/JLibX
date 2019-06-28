package com.jewel.libx;

import org.junit.Test;

import java.util.Arrays;

/**
 * 集合源码理解
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/6/20
 */
public class ArrayUnitTest{

	/**
	 * ArrayList.add(E)函数的逻辑类似于下列逻辑
	 */
	@Test
	public void arrayList2Add(){
		Integer[] elementData = {0, 1, 2, 3, 4};
		int size = elementData.length;

		// 此处有个数组扩容算法。如果ArrayList的大小已经不满足需求时，那么就将数组变为原长度的1.5倍。
		// 关键代码为【新数组长度 = 旧数组长度 + 旧数组长度>>2】

		elementData = Arrays.copyOf(elementData, size + 1);
		System.out.println(Arrays.asList(elementData)); // 数组扩容变为[0, 1, 2, 3, 4, null]

		elementData[size] = 9;  // 在需要修改的位置更新数据
		System.out.println(Arrays.asList(elementData)); // 最终数组变成[0, 1, 2, 3, 4, 9]
	}

	/**
	 * ArrayList.add(int, E)函数的逻辑类似于下列逻辑
	 */
	@Test
	public void arrayList2AddIndex(){

		Integer[] elementData = {0, 1, 2, 3, 4};
		int index = 2;
		int size = elementData.length;

		// 此处有个数组扩容算法。如果ArrayList的大小已经不满足需求时，那么就将数组变为原长度的1.5倍。
		// 关键代码为【新数组长度 = 旧数组长度 + 旧数组长度>>2】

		elementData = Arrays.copyOf(elementData, size + 1);
		System.out.println(Arrays.asList(elementData)); // 数组扩容变为[0, 1, 2, 3, 4, null]

		// 从该数组的第三个位置开始拷贝长度为size-index的数组，从该数组第四个位置开始替换数组。
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		System.out.println(Arrays.asList(elementData)); // 经过拷贝数组变为[0, 1, 2, 2, 3, 4]
		elementData[index] = 9;  // 在需要修改的位置更新数据
		System.out.println(Arrays.asList(elementData)); // 最终数组变成[0, 1, 9, 2, 3, 4]
	}

}
