package cn.net.mayh.sheet;

import java.util.Map;

public class TableHeader extends TableCell{
    String headerName;
    String headerCode;
    InputTypeEnum type = InputTypeEnum.TEXT;
    Map<String,String> thAttribute;
    Map<String,String> headerParas;

}
