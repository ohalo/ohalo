package org.ohalo.base.utils;

import java.util.Hashtable;
import java.util.Map;

/**
 * 
 * <pre>
 * 功能：SequenceUtil 获取seq序列的util工具类
 * 作者：赵辉亮
 * 日期：2013-4-18上午10:33:35
 * </pre>
 */
public class SequenceUtil {

	private static Map<String, Integer> SEQTABLE = new Hashtable<String, Integer>();

	/**
	 * 
	 * <pre>
	 * 方法体说明：创建一个类型序列，这个序列的起始值为startValue，每次使用+1操作，
	 * 作者：赵辉亮
	 * 日期：2013-4-18. 上午10:11:37
	 * </pre>
	 * 
	 * @param seqName
	 * @param startValue
	 * @param flag
	 *            是否进行覆盖，序列中可能存在重复数据是否进行覆盖true标识覆盖，false标识不覆盖
	 */
	public static void createTypeSeq(String seqName, Integer startValue,
			boolean flag) {
		if (!flag && SEQTABLE.get(seqName) != null) {
			return;
		}
		SEQTABLE.put(seqName, startValue);
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：根据类型名称获取序列的值，每次自加1
	 * 作者：赵辉亮
	 * 日期：2013-4-18. 上午10:20:49
	 * </pre>
	 * 
	 * @param seqName
	 * @return
	 */
	public static synchronized int nextval(String seqName) {
		int nextval = 1;
		try {
			nextval = SEQTABLE.get(seqName) == null ? nextval : SEQTABLE.get(seqName);
			return nextval;
		} finally {
			SEQTABLE.put(seqName, ++nextval);
		}
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：获取序列的当前值
	 * 作者：赵辉亮
	 * 日期：2013-4-18. 上午10:29:51
	 * </pre>
	 * 
	 * @param seqName
	 * @return
	 */
	public static int currval(String seqName) {
		int nextval = SEQTABLE.get(seqName) == null ? 0 : SEQTABLE.get(seqName);
		return nextval;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：查看这个序列是否为空
	 * 作者：赵辉亮
	 * 日期：2013-4-18. 上午10:42:39
	 * </pre>
	 * 
	 * @return
	 */
	public static SeqType isEmpty(String seqName) {
		if (SEQTABLE.isEmpty()) {
			return SeqType.ALLEMPTY;
		}
		if (!SEQTABLE.containsKey(seqName)) {
			return SeqType.CURRENTSEQISEMPTY;
		}
		return SeqType.NOEMPTY;
	}

	public enum SeqType {
		ALLEMPTY /* 在序列空间中全部为空 */, CURRENTSEQISEMPTY/* 当前序列为空 */, NOEMPTY/* 没有序列为空 */
	}
}
