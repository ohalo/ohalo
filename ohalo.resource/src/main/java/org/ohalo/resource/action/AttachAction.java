package org.ohalo.resource.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.ohalo.base.action.BaseController;
import org.ohalo.base.data.Result;
import org.ohalo.base.utils.Guid;
import org.ohalo.base.utils.LogUtils;
import org.ohalo.resource.bo.QueryData;
import org.ohalo.resource.pojo.AttachResource;
import org.ohalo.resource.service.IAttachmentService;
import org.ohalo.resource.util.AttachUtil;
import org.ohalo.resource.util.AttachmentRootPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 附件管理
 * 
 * @author z.halo
 * @since 2013年10月12日 1.0
 */
@RequestMapping(value = "/attach")
@Controller
public class AttachAction extends BaseController {

	@Autowired
	private IAttachmentService attachmentService;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	Result handleFormUpload(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "relUid", required = false) String relUid) {

		Result result = new Result();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件：
		List<MultipartFile> files = multipartRequest.getFiles("files");

		if (files == null || files.size() < 1) {
			result.setResultCode(Result.BUSINESS_FAILURE_CODE);
			result.setResultMsg("上传附件为空,请重新上传!");
			return result;
		}

		try {
			for (MultipartFile multipartFile : files) {
				// 获得文件名：
				String filename = multipartFile.getOriginalFilename();
				if(StringUtils.isBlank(filename)){continue;}
				String filepath = AttachUtil.uploadFile(
						multipartFile.getInputStream(), filename);
				/**** 封装附件资源信息 ****/
				AttachResource resource = new AttachResource();
				resource.setFid(Guid.newDCGuid("attach"));
				String fileType = filename.substring(filename.lastIndexOf("."));
				resource.setAttachguid(relUid);
				// 文件名称
				resource.setAttachname(filename);
				// 文件类型
				resource.setAttachtype(fileType);
				// 封装附件信息 附件路径
				resource.setAttachpath(filepath);
				// 文件大小
				String fileSize = FileUtils
						.byteCountToDisplaySize(multipartFile.getSize());
				// 封装附件信息 附件大小
				resource.setAttachsize(fileSize);
				// 封装附件信息 插入数据库
				attachmentService.insertAttach(resource);
			}
		} catch (IOException e) {
			LogUtils.errorMsg("AttachAction", "handleFormUpload",
					"读写文件出现异常![files.length=" + files.size() + ",relUid="
							+ relUid + "]", e);
			result.setResultCode(Result.BUSINESS_FAILURE_CODE);
			result.setResultMsg("上传附件错误,请重新上传!");
			return result;
		}
		result.setResultCode(Result.SUCCESS_CODE);
		result.setResultMsg("上传附件成功!");
		return result;
	}

	/**
	 * 
	 * <pre>
	 *  方法体说明：删除文件信息
	 * 1.从数据库中删除文件信息
	 * 2.从附件资源服务器中删除文件信息
	 *  作者：赵辉亮
	 *  日期：2013-5-16. 下午4:44:52
	 * </pre>
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deleteFile")
	public String delete(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "relUid", required = false) String relUid,
			@RequestParam(value = "fileId", required = false) String fileId) {
		QueryData queryData = new QueryData();
		queryData.setAttachguid(relUid);
		queryData.setFileId(fileId);
		// 根据相应的参数信息，查询所属附件信息
		List<AttachResource> resources = attachmentService
				.queryAttachResources(queryData);

		// 如果附加信息为空的话,则直接返回
		if (resources.isEmpty()) {
			returnSuccess(null, "删除失败!", response);
			return null;
		}

		// 删除文件，如果传过来的参数中包含外键id，但是不包含文件id，则
		// 直接删除所属外键的所有附件信息
		if (StringUtils.isNotBlank(queryData.getAttachguid())
				&& StringUtils.isBlank(queryData.getFileId())) {
			attachmentService.deleteAttachByRelID(queryData.getAttachguid());
			// 如果传过来的参数中不包含外键id和文件id，则直接返回
		} else if (StringUtils.isBlank(queryData.getAttachguid())
				&& StringUtils.isBlank(queryData.getFileId())) {
			returnSuccess(null, "删除失败!", response);
			return null;
			// 剩余的直接走删除文件的service方法请求
		} else {
			attachmentService.deleteAttachById(queryData.getFileId());
		}

		// 如果查询出的附件信息不为空的话则删除所在目录的附件
		for (AttachResource resource : resources) {
			AttachUtil.deleteFile(resource.getAttachpath());
		}

		returnSuccess(null, "删除成功!", response);
		return null;
	}

	/**
	 * @Description 下载文件 ， 我们采用的是 通过浏览器可以直接进行下载的，下载到本地桌面，
	 * 
	 */
	@RequestMapping(value = "/downloadFile")
	public String downloadFile(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "attachName", required = false) String attachName,
			@RequestParam(value = "attachPath", required = false) String attachPath) {
		// 首先判断，需要下载的附件名称是否为空，如果不为空则直接给filename赋值

		String fileName = "";
		if (StringUtils.isNotBlank(attachName)) {
			fileName = attachName;
			// 如果为空则自定义一个文件名称给这个附件
		} else {
			int index = attachPath.lastIndexOf("/");
			fileName = attachPath.substring(index + 1);
		}

		try {
			// 给附件名称一个编码格式，格式一般都是UTF8的，因为浏览器和服务器支持的只有UTF8的编码
			// 或者是给名字一个英文名称，那就不需要编码了
			fileName = encodeFilename(fileName);
		} catch (UnsupportedEncodingException e) {
			return null;
		}

		// 文件上传根目录
		String rootPath = AttachmentRootPath.getAttachRootPath();
		File file = new File(rootPath + attachPath);

		// 文件上传根目录

		InputStream inputName = null;
		BufferedOutputStream bos = null;

		if (file.isFile()) {
			try {
				// 把文件写入流，供客户端下载
				inputName = new FileInputStream(file);
				bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = inputName.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}

			} catch (FileNotFoundException e) {
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {

					if (inputName != null) {
						inputName.close();
					}
					if (bos != null) {
						bos.close();
					}
				} catch (IOException e) {
					LogUtils.errorMsg("AttachAction", "downloadFile",
							"关闭流出现异常!", e);
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * <pre>
	 * 方法体说明：为附件名称转码
	 * 作者：赵辉亮
	 * 日期：2013-7-25
	 * </pre>
	 * 
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String encodeFilename(String fileName)
			throws UnsupportedEncodingException {
		// 首先获取这个浏览器的请求信息USER-AGENT
		String agent = "";// Struts2Utils.getRequest().getHeader("USER-AGENT");
		String fName = "";
		try {
			// IE 如果请求的浏览器是IE的话，则执行以下步骤，转换编码为UTF8s
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				fName = URLEncoder.encode(fileName, "UTF8");
				// Firefox 如果浏览的型号是Firefox的话，则转化编码格式为火狐可以识别的UTF8编码
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				String fileNameTemp = fileName.replace(" ", "");
				fName = new String(fileNameTemp.getBytes("GB2312"),
						"ISO-8859-1");// MimeUtility.encodeText(fileName,
										// "UTF8", "B");
			} else {
				fName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			}
			// 如果在转换编码的时候出现异常了，则执行下面的转换操作。把UTF8的编码转换成iso-8859-1
		} catch (UnsupportedEncodingException e) {
			try {
				//
				fName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
				// 抛出异常
			} catch (UnsupportedEncodingException e1) {
				throw new UnsupportedEncodingException();
			}
			throw new UnsupportedEncodingException();
		}
		return fName;
	}
}
