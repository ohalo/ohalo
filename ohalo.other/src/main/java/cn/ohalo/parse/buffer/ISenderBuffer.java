package cn.ohalo.parse.buffer;

import java.util.List;

/**
 * 
 * @author zhaohl
 *
 */
public interface ISenderBuffer<T> {

	public void send(List<T> list);

}
