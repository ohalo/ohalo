package cn.ohalo.stock.test;

import junit.framework.TestCase;
import cn.ohalo.stock.rule.StockRule;
import cn.ohalo.stock.rule.StockRuleDown;

public class StockRuleTest extends TestCase {

	private StockRule sr;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		sr = new StockRuleDown();
	}

	public void testA() {
		sr.judgeStockTrend("000002", 1.7, 100.00);
		sr.judgeStockTrend("000002", 1.7, -10.00);
		sr.judgeStockTrend("000002", 1.7, -100.00);
		sr.judgeStockTrend("000002", 1.7, 100.00);

	}

	public void testRound() {
		System.out.println((100 / Math.round(10.89)) * 100);
	}

}
