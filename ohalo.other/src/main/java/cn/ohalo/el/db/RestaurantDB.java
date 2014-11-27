package cn.ohalo.el.db;

import cn.ohalo.db.mongodb.BaseDb;
import cn.ohalo.el.entity.Restaurant;

/**
 * 饭店餐饮信息
 * 
 * @author halo
 * 
 */
public class RestaurantDB extends BaseDb<Restaurant> {

	private static RestaurantDB resDb;

	public static RestaurantDB getInstance() {
		if (resDb == null) {
			resDb = new RestaurantDB();
		}

		return resDb;
	}
}