/**
 * 
 */
package cn.ohalo.utils;

/**
 * @author halo<halo26812@yeah.net>
 * @since 2014年4月19日
 */
public class FunIMsgFormat {
    public static enum MsgStyle {
        DEFAULT_LOG("FUNI-/-DEFAULT_LOG-/-{}"),
        ERROR_REPORT("FUNI-/-ERROR_REPORT-/-{}-/-{}");

        private String format;


        private MsgStyle() {
            this.format = "FUNI-/-{}";
        }


        private MsgStyle(String format) {
            this.format = format;
        }


        public String getFormat() {
            return this.format;
        }


        public String getFormat(String selfFormat) {
            return "FUNI-/-DEFAULT_LOG-/-" + selfFormat;
        }
    }
}
