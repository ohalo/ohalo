/**
 * 
 */
package cn.ohalo.db;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @author halo<halo26812@yeah.net>
 * @since 2014年4月19日
 */
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = -1063188008194990237L;


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
