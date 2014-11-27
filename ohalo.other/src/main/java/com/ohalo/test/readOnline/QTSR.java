package com.ohalo.test.readOnline;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * @author 夜明的孤行灯
 * @date 2012-7-5
 */

public interface QTSR extends Library {
	QTSR INSTANCE = (QTSR) Native.loadLibrary("msc", QTSR.class);

	/**
	 * 初始化MSC的ISR部分
	 * 
	 * @param configs
	 *            初始化时传入的字符串，以指定合成用到的一些配置参数，各个参数以“参数名=参数值”的形式出现，大小写不敏感，不同的参数之间以“
	 *            ,”或“\n”隔开，不设置任何值时可以传入NULL或空串：
	 * @return 如果函数调用成功返回MSP_SUCCESS，否则返回错误代码，错误代码参见msp_errors
	 */
	public int QISRInit(String configs);

	/**
	 * 开始一个ISR会话
	 * 
	 * @param grammarList
	 *            uri-list格式的语法，可以是一个语法文件的URL或者一个引擎内置语法列表。可以同时指定多个语法，不同的语法之间以“,”
	 *            隔开。进行语音听写时不需要语法，此参数设定为NULL或空串即可；进行语音识别时则需要语法，语法可以在此参数中指定，
	 *            也可以随后调用QISRGrammarActivate指定识别所用的语法。
	 * @param params
	 *            本路ISR会话使用的参数，可设置的参数及其取值范围请参考《可设置参数列表_MSP20.xls》，各个参数以“参数名=参数值”
	 *            的形式出现，不同的参数之间以“,”或者“\n”隔开。
	 * @param errorCode
	 *            如果函数调用成功则其值为MSP_SUCCESS，否则返回错误代码，错误代码参见msp_errors。几个主要的返回值：
	 *            MSP_ERROR_NOT_INIT 未初始化 MSP_ERROR_INVALID_PARA 无效的参数
	 *            MSP_ERROR_NO_LICENSE 开始一路会话失败
	 * @return MSC为本路会话建立的ID，用来唯一的标识本路会话，供以后调用其他函数时使用。函数调用失败则会返回NULL。
	 */
	public String QISRSessionBegin(String grammarList, String params,
			IntByReference errorCode);

	/**
	 * 传入语法
	 * 
	 * @param sessionID
	 *            由QISRSessionBegin返回过来的会话ID。
	 * @param grammar
	 *            语法字符串
	 * @param type
	 *            语法类型，可以是uri-list、abnf、xml等
	 * @param weight
	 *            本次传入语法的权重，本参数在MSP 2.0中会被忽略。
	 * @return 如果函数调用成功返回MSP_SUCCESS，否则返回错误代码，错误代码参见msp_errors
	 */
	public int QISRGrammarActivate(String sessionID, String grammar,
			String type, int weight);

	/**
	 * 写入用来识别的语音
	 * 
	 * @param sessionID
	 *            由QISRSessionBegin返回过来的会话ID。
	 * @param waveData
	 *            音频数据缓冲区起始地址
	 * @param waveLen
	 *            音频数据长度，其大小不能超过设定的max_audio_size
	 * @param audioStatus
	 *            用来指明用户本次识别的音频是否发送完毕，可能值如下： 
	 *            MSP_AUDIO_SAMPLE_FIRST = 1 第一块音频
	 *            MSP_AUDIO_SAMPLE_CONTINUE = 2 还有后继音频 
	 *            MSP_AUDIO_SAMPLE_LAST = 4 最后一块音频
	 * @param epStatus
	 *            端点检测（End-point detected）器所处的状态，可能的值如下： 
	 *            MSP _EP_LOOKING_FOR_SPEECH = 0 还没有检测到音频的前端点。 
	 *            MSP _EP_IN_SPEECH = 1 已经检测到了音频前端点，正在进行正常的音频处理。 
	 *            MSP _EP_AFTER_SPEECH = 3 检测到音频的后端点，后继的音频会被MSC忽略。 
	 *            MSP _EP_TIMEOUT = 4 超时。 
	 *            MSP _EP_ERROR= 5 出现错误。 
	 *            MSP _EP_MAX_SPEECH = 6 音频过大。
	 * @param recogStatus
	 *            识别器所处的状态
	 * @return
	 */
	public int QISRAudioWrite(String sessionID, Pointer waveData, int waveLen,
			int audioStatus, IntByReference epStatus, IntByReference recogStatus);

	/**
	 * 获取识别结果
	 * 
	 * @param sessionID 由QISRSessionBegin返回过来的会话ID。
	 * @param rsltStatus 识别结果的状态，其取值范围和含义请参考QISRAudioWrite的参数recogStatus
	 * @param waitTime 与服务器交互的间隔时间，可以控制和服务器的交互频度。单位为ms，建议取值为5000。
	 * @param errorCode 如果函数调用成功返回MSP_SUCCESS，否则返回错误代码，错误代码参见msp_errors
	 * @return 函数执行成功并且获取到识别结果时返回识别结果，函数执行成功没有获取到识别结果时返回NULL
	 */
	public String QISRGetResult(String sessionID, IntByReference rsltStatus,
			int waitTime, IntByReference errorCode);

	/**
	 * 结束一路会话
	 * 
	 * @param sessionID 由QISRSessionBegin返回过来的会话ID。
	 * @param hints 结束本次会话的原因描述，用于记录日志，便于用户查阅或者跟踪某些问题。
	 * @return
	 */
	public int QISRSessionEnd(String sessionID, String hints);

	/**
	 * 获取与识别交互相关的参数
	 * 
	 * @param sessionID 由QISRSessionBegin返回过来的会话ID。
	 * @param paramName 要获取的参数名称；支持同时查询多个参数，查询多个参数时，参数名称按“,” 或“\n”分隔开来。
	 * @param paramValue 获取的参数值，以字符串形式返回；查询多个参数时，参数值之间以“;”分开，不支持的参数将返回空的值。
	 * @param valueLen 参数值的长度。
	 * @return
	 */
	public int QISRGetParam(String sessionID, String paramName,
			String paramValue, IntByReference valueLen);

	/**
	 * 逆初始化MSC的ISR部分
	 * 
	 * @return
	 */
	public int QISRFini();
}
