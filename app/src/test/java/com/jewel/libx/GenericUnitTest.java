package com.jewel.libx;

import org.junit.Test;

/**
 * 泛型源码理解
 * @author jewel
 * @email jewelbao88@gmail.com
 * @gitsite https://github.com/jewelbao
 * @since 2019/6/20
 */
public class GenericUnitTest{

	@Test
	public void main() {

	}

	static class Clazz<T> {

		private T data;

		public <E> Clazz(E e, T data) {
			System.out.println(e);
			this.data = data;
		}

		public <F> void func(F f) {
			System.out.println(f);
		}

		public void get() {
			System.out.println(data);
		}
	}
}
