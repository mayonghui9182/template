package cn.net.mayh.comm;

import cn.net.mayh.dto.DictDto;

import java.util.List;

public interface AsDict {
    List<DictDto> findListAsDict(DictDto condition);
}
