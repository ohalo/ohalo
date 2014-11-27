package com.ohalo.test.socket.client.playcard;

import java.io.IOException;

public class RunTimeExe {

	private static String OPENPLAYCARDURL = "http://hr.deppon.com:9080/eos-default/dip.integrateorg.oaAttence.oaAttence.flow";

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		// Runtime.getRuntime()
		// .exec("cmd /c start  http://hr.deppon.com:9080/eos-default/dip.integrateorg.oaAttence.oaAttence.flow");

		Process process = Runtime.getRuntime()
				.exec("C:\\Program Files\\Internet Explorer\\IEXPLORE.EXE " + OPENPLAYCARDURL);

	}
}
