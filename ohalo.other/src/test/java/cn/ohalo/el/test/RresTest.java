package cn.ohalo.el.test;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import cn.ohalo.el.db.RestaurantDB;
import cn.ohalo.el.entity.Restaurant;

import com.mongodb.BasicDBObject;

public class RresTest extends TestCase {

	RestaurantDB restaurantDB;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		restaurantDB = RestaurantDB.getInstance();
		restaurantDB.setCollectionName("restaurant");
	}

	public void testInsert() {
		Restaurant res = new Restaurant();
		res.setAddress("aaa");
		res.setCreateDate(new Date());
		res.setDesc("cc");
		res.setId("asdas");
		res.setName("nihao");
		restaurantDB.insert(res);

	}

	public void testFind() {
		Restaurant res = restaurantDB.findOne();
		List<Restaurant> ress = restaurantDB.findAllAndSortAndLimit(
				new BasicDBObject(), null, 0, 10);
		System.out.println(ress.size());
		System.out.println(res.toString());
		System.out.println(restaurantDB.count(new BasicDBObject()));
	}

}
