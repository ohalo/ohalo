/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.ohalo.cn.test;

/*********************************************************************************
 * CPUTest.java JNI 接口对象 1999 April 20 by 王森
 **********************************************************************************/
// 加入my.cpu这个package之中
public class CPUTest {

	/* 以下定义每种处理器所代表的常数 */
	static public final int i386 = 0; // 不支持CPUID的处理器(可辨识)
	static public final int Pentium = 1; // 最早期的Pentium处理器(可辨识)
	static public final int Pentium_M = 2; // Pentium with MMX 处理器(可辨识)
	static public final int Pentium_2 = 3; // Pentium II 处理器(可辨识)
	static public final int Pentium_3 = 4; // Pentium III处理器(可辨识)
	static public final int Pentium_P = 5; // Pentium Pro 处理器(可辨识)
	static public final int K6 = 11; // 同Pentium with MMX
	static public final int K6_2 = 12; // K6-2处理器((可辨识)
	static public final int K6_3 = 13; // 同K6-2

	/* 以下定义所有会藉由JNI来叫用的函式 */

	// 测试CPU是否支持CPUID指令,如果支持则传回true,否则传回false
	public native boolean CheckCPUID();

	// ^^^^^^ 注意,所有的JNI函式都必须在函式宣告里加上native这个修饰字
	// 辨识处理器是否支持MMX,如果支持则传回true,否则传回false
	public native boolean CheckMMX();

	// 辨识处理器是否支持Stream SIMD Extension(即KNI),如果支持则传回true,否则传回false
	public native boolean CheckSSIMD();

	// 辨识处理器是否支持AMD 3DNow,如果支持则传回true,否则传回false
	public native boolean Check3DNOW();

	// 辨识CPU的等级,并传回一个整数代表CPU的等级
	public native int CheckCPUTYPE();

	// 印出CPU的相关信息
	public native void PrintCPUInfo();

	// note:使用此函数之前,请先呼叫前面的所有函式,因为前面的函式,除了传回真伪之外,也会设定DLL文件之中的全域变量而PrintCPUInfo会利用这些全域变量来做判定的工作.

	static {
		// 我们把实做CPU侦测函式的模块做成DLL(动态连结函式库)檔,
		// 取名叫CPUDTestDll.dll,所以在这里要加载此DLL
		System.loadLibrary("CPUTestDll");
	}
}