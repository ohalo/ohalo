package com.ohalo.test.readOnline;

import com.sun.jna.win32.StdCallLibrary;

/**
 * @author 夜明的孤行灯
 * @date 2012-7-5
 */

public interface Msp_errors extends StdCallLibrary {
	public static final int MSP_SUCCESS = 0;
	public static final int ERROR_FAIL = -1;
	public static final int ERROR_EXCEPTION = -2;
	public static final int ERROR_GENERAL = 10100; /* 0x2774 */
	public static final int ERROR_OUT_OF_MEMORY = 10101; /* 0x2775 */
	public static final int ERROR_FILE_NOT_FOUND = 10102; /* 0x2776 */
	public static final int ERROR_NOT_SUPPORT = 10103; /* 0x2777 */
	public static final int ERROR_NOT_IMPLEMENT = 10104; /* 0x2778 */
	public static final int ERROR_ACCESS = 10105; /* 0x2779 */
	public static final int ERROR_INVALID_PARA = 10106; /* 0x277A */
	public static final int ERROR_INVALID_PARA_VALUE = 10107; /* 0x277B */
	public static final int ERROR_INVALID_HANDLE = 10108; /* 0x277C */
	public static final int ERROR_INVALID_DATA = 10109; /* 0x277D */
	public static final int ERROR_NO_LICENSE = 10110; /* 0x277E */
	public static final int ERROR_NOT_INIT = 10111; /* 0x277F */
	public static final int ERROR_NULL_HANDLE = 10112; /* 0x2780 */
	public static final int ERROR_OVERFLOW = 10113; /* 0x2781 */
	public static final int ERROR_TIME_OUT = 10114; /* 0x2782 */
	public static final int ERROR_OPEN_FILE = 10115; /* 0x2783 */
	public static final int ERROR_NOT_FOUND = 10116; /* 0x2784 */
	public static final int ERROR_NO_ENOUGH_BUFFER = 10117; /* 0x2785 */
	public static final int ERROR_NO_DATA = 10118; /* 0x2786 */
	public static final int ERROR_NO_MORE_DATA = 10119; /* 0x2787 */
	public static final int ERROR_NO_RESPONSE_DATA = 10120; /* 0x2788 */
	public static final int ERROR_ALREADY_EXIST = 10121; /* 0x2789 */
	public static final int ERROR_LOAD_MODULE = 10122; /* 0x278A */
	public static final int ERROR_BUSY = 10123; /* 0x278B */
	public static final int ERROR_INVALID_CONFIG = 10124; /* 0x278C */
	public static final int ERROR_VERSION_CHECK = 10125; /* 0x278D */
	public static final int ERROR_CANCELED = 10126; /* 0x278E */
	public static final int ERROR_INVALID_MEDIA_TYPE = 10127; /* 0x278F */
	public static final int ERROR_CONFIG_INITIALIZE = 10128; /* 0x2790 */
	public static final int ERROR_CREATE_HANDLE = 10129; /* 0x2791 */
	public static final int ERROR_CODING_LIB_NOT_LOAD = 10130; /* 0x2792 */

	/* Error codes of network 10200(0x27D8) */
	public static final int ERROR_NET_GENERAL = 10200; /* 0x27D8 */
	public static final int ERROR_NET_OPENSOCK = 10201; /* 0x27D9 *//*
																	 * Open
																	 * socket
																	 */
	public static final int ERROR_NET_CONNECTSOCK = 10202; /* 0x27DA *//*
																		 * Connect
																		 * socket
																		 */
	public static final int ERROR_NET_ACCEPTSOCK = 10203; /* 0x27DB *//*
																	 * Accept
																	 * socket
																	 */
	public static final int ERROR_NET_SENDSOCK = 10204; /* 0x27DC *//*
																	 * Send
																	 * socket
																	 * data
																	 */
	public static final int ERROR_NET_RECVSOCK = 10205; /* 0x27DD *//*
																	 * Recv
																	 * socket
																	 * data
																	 */
	public static final int ERROR_NET_INVALIDSOCK = 10206; /* 0x27DE *//*
																		 * Invalid
																		 * socket
																		 * handle
																		 */
	public static final int ERROR_NET_BADADDRESS = 10207; /* 0x27EF *//*
																	 * Bad
																	 * network
																	 * address
																	 */
	public static final int ERROR_NET_BINDSEQUENCE = 10208; /* 0x27E0 *//*
																		 * Bind
																		 * after
																		 * listen
																		 * /
																		 * connect
																		 */
	public static final int ERROR_NET_NOTOPENSOCK = 10209; /* 0x27E1 *//*
																		 * Socket
																		 * is
																		 * not
																		 * opened
																		 */
	public static final int ERROR_NET_NOTBIND = 10210; /* 0x27E2 *//*
																	 * Socket is
																	 * not bind
																	 * to an
																	 * address
																	 */
	public static final int ERROR_NET_NOTLISTEN = 10211; /* 0x27E3 *//*
																	 * Socket is
																	 * not
																	 * listening
																	 */
	public static final int ERROR_NET_CONNECTCLOSE = 10212; /* 0x27E4 *//*
																		 * The
																		 * other
																		 * side
																		 * of
																		 * connection
																		 * is
																		 * closed
																		 */
	public static final int ERROR_NET_NOTDGRAMSOCK = 10213; /* 0x27E5 *//*
																		 * The
																		 * socket
																		 * is
																		 * not
																		 * datagram
																		 * type
																		 */
	public static final int ERROR_NET_DNS = 10214; /* 0x27E6 *//*
																 * domain name
																 * is invalid or
																 * dns server
																 * does not
																 * function well
																 */

	/* Error codes of mssp message 10300(0x283C) */
	public static final int ERROR_MSG_GENERAL = 10300; /* 0x283C */
	public static final int ERROR_MSG_PARSE_ERROR = 10301; /* 0x283D */
	public static final int ERROR_MSG_BUILD_ERROR = 10302; /* 0x283E */
	public static final int ERROR_MSG_PARAM_ERROR = 10303; /* 0x283F */
	public static final int ERROR_MSG_CONTENT_EMPTY = 10304; /* 0x2840 */
	public static final int ERROR_MSG_INVALID_CONTENT_TYPE = 10305; /* 0x2841 */
	public static final int ERROR_MSG_INVALID_CONTENT_LENGTH = 10306; /* 0x2842 */
	public static final int ERROR_MSG_INVALID_CONTENT_ENCODE = 10307; /* 0x2843 */
	public static final int ERROR_MSG_INVALID_KEY = 10308; /* 0x2844 */
	public static final int ERROR_MSG_KEY_EMPTY = 10309; /* 0x2845 */
	public static final int ERROR_MSG_SESSION_ID_EMPTY = 10310; /* 0x2846 */
	public static final int ERROR_MSG_LOGIN_ID_EMPTY = 10311; /* 0x2847 */
	public static final int ERROR_MSG_SYNC_ID_EMPTY = 10312; /* 0x2848 */
	public static final int ERROR_MSG_APP_ID_EMPTY = 10313; /* 0x2849 */
	public static final int ERROR_MSG_EXTERN_ID_EMPTY = 10314; /* 0x284A */
	public static final int ERROR_MSG_INVALID_CMD = 10315; /* 0x284B */
	public static final int ERROR_MSG_INVALID_SUBJECT = 10316; /* 0x284C */
	public static final int ERROR_MSG_INVALID_VERSION = 10317; /* 0x284D */
	public static final int ERROR_MSG_NO_CMD = 10318; /* 0x284E */
	public static final int ERROR_MSG_NO_SUBJECT = 10319; /* 0x284F */
	public static final int ERROR_MSG_NO_VERSION = 10320; /* 0x2850 */
	public static final int ERROR_MSG_MSSP_EMPTY = 10321; /* 0x2851 */
	public static final int ERROR_MSG_NEW_RESPONSE = 10322; /* 0x2852 */
	public static final int ERROR_MSG_NEW_CONTENT = 10323; /* 0x2853 */
	public static final int ERROR_MSG_INVALID_SESSION_ID = 10324; /* 0x2854 */

	/* Error codes of DataBase 10400(0x28A0) */
	public static final int ERROR_DB_GENERAL = 10400; /* 0x28A0 */
	public static final int ERROR_DB_EXCEPTION = 10401; /* 0x28A1 */
	public static final int ERROR_DB_NO_RESULT = 10402; /* 0x28A2 */
	public static final int ERROR_DB_INVALID_USER = 10403; /* 0x28A3 */
	public static final int ERROR_DB_INVALID_PWD = 10404; /* 0x28A4 */
	public static final int ERROR_DB_CONNECT = 10405; /* 0x28A5 */
	public static final int ERROR_DB_INVALID_SQL = 10406; /* 0x28A6 */
	public static final int ERROR_DB_INVALID_APPID = 10407; /* 0x28A7 */

	/* Error codes of Resource 10500(0x2904) */
	public static final int ERROR_RES_GENERAL = 10500; /* 0x2904 */
	public static final int ERROR_RES_LOAD = 10501; /* 0x2905 *//* Load resource */
	public static final int ERROR_RES_FREE = 10502; /* 0x2906 *//* Free resource */
	public static final int ERROR_RES_MISSING = 10503; /* 0x2907 *//*
																	 * Resource
																	 * File
																	 * Missing
																	 */
	public static final int ERROR_RES_INVALID_NAME = 10504; /* 0x2908 *//*
																		 * Invalid
																		 * resource
																		 * file
																		 * name
																		 */
	public static final int ERROR_RES_INVALID_ID = 10505; /* 0x2909 *//*
																	 * Invalid
																	 * resource
																	 * ID
																	 */
	public static final int ERROR_RES_INVALID_IMG = 10506; /* 0x290A *//*
																		 * Invalid
																		 * resource
																		 * image
																		 * pointer
																		 */
	public static final int ERROR_RES_WRITE = 10507; /* 0x290B *//*
																 * Write
																 * read-only
																 * resource
																 */
	public static final int ERROR_RES_LEAK = 10508; /* 0x290C *//*
																 * Resource leak
																 * out
																 */
	public static final int ERROR_RES_HEAD = 10509; /* 0x290D *//*
																 * Resource head
																 * currupt
																 */
	public static final int ERROR_RES_DATA = 10510; /* 0x290E *//*
																 * Resource data
																 * currupt
																 */
	public static final int ERROR_RES_SKIP = 10511; /* 0x290F *//*
																 * Resource file
																 * skipped
																 */

	/* Error codes of TTS 10600(0x2968) */
	public static final int ERROR_TTS_GENERAL = 10600; /* 0x2968 */
	public static final int ERROR_TTS_TEXTEND = 10601; /* 0x2969 *//*
																	 * Meet text
																	 * end
																	 */
	public static final int ERROR_TTS_TEXT_EMPTY = 10602; /* 0x296A *//*
																	 * no synth
																	 * text
																	 */

	/* Error codes of Recognizer 10700(0x29CC) */
	public static final int ERROR_REC_GENERAL = 10700; /* 0x29CC */
	public static final int ERROR_REC_INACTIVE = 10701; /* 0x29CD */
	public static final int ERROR_REC_GRAMMAR_ERROR = 10702; /* 0x29CE */
	public static final int ERROR_REC_NO_ACTIVE_GRAMMARS = 10703; /* 0x29CF */
	public static final int ERROR_REC_DUPLICATE_GRAMMAR = 10704; /* 0x29D0 */
	public static final int ERROR_REC_INVALID_MEDIA_TYPE = 10705; /* 0x29D1 */
	public static final int ERROR_REC_INVALID_LANGUAGE = 10706; /* 0x29D2 */
	public static final int ERROR_REC_URI_NOT_FOUND = 10707; /* 0x29D3 */
	public static final int ERROR_REC_URI_TIMEOUT = 10708; /* 0x29D4 */
	public static final int ERROR_REC_URI_FETCH_ERROR = 10709; /* 0x29D5 */

	/* Error codes of Speech Detector 10800(0x2A30) */
	public static final int ERROR_EP_GENERAL = 10800; /* 0x2A30 */
	public static final int ERROR_EP_NO_SESSION_NAME = 10801; /* 0x2A31 */
	public static final int ERROR_EP_INACTIVE = 10802; /* 0x2A32 */
	public static final int ERROR_EP_INITIALIZED = 10803; /* 0x2A33 */

	/* Error codes of TUV */
	public static final int ERROR_TUV_GENERAL = 10900; /* 0x2A94 */
	public static final int ERROR_TUV_GETHIDPARAM = 10901; /* 0x2A95 *//*
																		 * Get
																		 * Busin
																		 * Param
																		 * huanid
																		 */
	public static final int ERROR_TUV_TOKEN = 10902; /* 0x2A96 *//* Get Token */
	public static final int ERROR_TUV_CFGFILE = 10903; /* 0x2A97 *//*
																	 * Open cfg
																	 * file
																	 */
	public static final int ERROR_TUV_RECV_CONTENT = 10904; /* 0x2A98 *//*
																		 * received
																		 * content
																		 * is
																		 * error
																		 */
	public static final int ERROR_TUV_VERFAIL = 10905; /* 0x2A99 *//*
																	 * Verify
																	 * failure
																	 */

	/* Error codes of IMTV */
	public static final int ERROR_LOGIN_SUCCESS = 11000; /* 0x2AF8 *//* 成功 */
	public static final int ERROR_LOGIN_NO_LICENSE = 11001; /* 0x2AF9 *//*
																		 * 试用次数结束，
																		 * 用户需要付费
																		 */
	public static final int ERROR_LOGIN_SESSIONID_INVALID = 11002; /* 0x2AFA *//*
																				 * SessionId失效
																				 * ，
																				 * 需要重新登录通行证
																				 */
	public static final int ERROR_LOGIN_SESSIONID_ERROR = 11003; /* 0x2AFB *//*
																			 * SessionId为空
																			 * ，
																			 * 或者非法
																			 */
	public static final int ERROR_LOGIN_UNLOGIN = 11004; /* 0x2AFC *//* 未登录通行证 */
	public static final int ERROR_LOGIN_INVALID_USER = 11005; /* 0x2AFD *//* 用户ID无效 */
	public static final int ERROR_LOGIN_INVALID_PWD = 11006; /* 0x2AFE *//* 用户密码无效 */
	public static final int ERROR_LOGIN_SYSTEM_ERROR = 11099; /* 0x2B5B *//* 系统错误 */

	/* Error codes of HCR */
	public static final int ERROR_HCR_GENERAL = 11100;
	public static final int ERROR_HCR_RESOURCE_NOT_EXIST = 11101;
	public static final int ERROR_HCR_CREATE = 11102;
	public static final int ERROR_HCR_DESTROY = 11103;
	public static final int ERROR_HCR_START = 11104;
	public static final int ERROR_HCR_APPEND_STROKES = 11105;
	public static final int ERROR_HCR_GET_RESULT = 11106;
	public static final int ERROR_HCR_SET_PREDICT_DATA = 11107;
	public static final int ERROR_HCR_GET_PREDICT_RESULT = 11108;

	/* Error codes of http 12000(0x2EE0) */
	public static final int ERROR_HTTP_BASE = 12000; /* 0x2EE0 */

	/* Error codes of ISV */
	public static final int ERROR_ISV_NO_USER = 13000; /* 32C8 *//*
																 * the user
																 * doesn't exist
																 */

	/* Error codes of Lua scripts */
	public static final int ERROR_LUA_BASE = 14000; /* 0x36B0 */
	public static final int ERROR_LUA_YIELD = 14001; /* 0x36B1 */
	public static final int ERROR_LUA_ERRRUN = 14002; /* 0x36B2 */
	public static final int ERROR_LUA_ERRSYNTAX = 14003; /* 0x36B3 */
	public static final int ERROR_LUA_ERRMEM = 14004; /* 0x36B4 */
	public static final int ERROR_LUA_ERRERR = 14005; /* 0x36B5 */
}
