package org.ohalo.resource.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ohalo.base.utils.SpringContextUtil;
import org.ohalo.resource.pojo.AttachmentConfig;

/**
 * 
 * @author z.halo
 * @since 2013年10月12日 1.0
 */
public class AttachmentRootPath {

	// 日志打印
	public static Log logger = LogFactory.getLog(AttachmentRootPath.class);

	// linux server path
	private static String linuxServerpath;

	/**
	 * 
	 * <pre>
	 * 方法体说明：获取附件上传的root path
	 *  首先获取这个平台所属的systemos,
	 *  如果该os是linux则上传附件到系统文件中配置的path上面
	 *  如果该os是windows则上传附件到this.class.getClassLoader().getResource("")
	 *           .getPath();
	 *  上面
	 *  
	 *  
	 *  
	 *  
	 * 作者：赵辉亮
	 * 日期：2013-7-12. 上午9:25:04
	 * </pre>
	 * 
	 * @return
	 */
	public static String getAttachRootPath() {
		// 判断该linuxpath是否存在，如果不存在则执行初始化配置
		if (StringUtils.isBlank(linuxServerpath)) {
			// 初始化配置信息
			initConfig();
		}
		// 获取本系统的os名称
		String osname = System.getProperty("os.name");
		// 获取root的路径
		String rootPath = "";
		// 如果这个系统是windows的，则获取本class所在的路径
		if (osname.contains("Windows")) {
			// 获取root路径信息
			rootPath = AttachmentRootPath.class.getClassLoader()
					.getResource("").getPath();
			// 取WEB-INF上层目录路径
			rootPath = rootPath.substring(0, rootPath.lastIndexOf("WEB-INF"));
		} else {
			// 如果不是则去linuxserverpath路径
			rootPath = linuxServerpath;
		}
		// 打印存储目录路径
		logger.info("存储附件目录：" + rootPath);
		return rootPath;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：初始化配置信息
	 * 作者：赵辉亮
	 * 日期：2013-7-13. 下午1:56:54
	 * </pre>
	 */
	public static void initConfig() {
		// 附件配置实体类
		AttachmentConfig config = (AttachmentConfig) SpringContextUtil
				.getBean("attachmentConfig");
		// 获取linux server path 的路径信息
		linuxServerpath = config.getLinuxServerPath();
	}

}
