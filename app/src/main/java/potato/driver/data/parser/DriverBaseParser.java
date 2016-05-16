package potato.driver.data.parser;

import potato.driver.chips.base.BaseParser;

/**
 * @author ztw 这个类只提供基础的解析方法，每个接口对应的解析方法在.parse.api包下面。
 */
public class DriverBaseParser extends BaseParser {
    // 错误信息

    public DriverBaseParser(String jsonStr) {
        super(jsonStr);
        code = root.optString("code");
        msg = root.optString("msg");
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public boolean isSucc() {
        return code.equals("10000");
    }


}
