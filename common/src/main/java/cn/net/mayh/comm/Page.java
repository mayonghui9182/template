package cn.net.mayh.comm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "Page", description = "分页信息")
@Data
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前第几页")
    private Integer currentPage = 1;

    @ApiModelProperty(value = "每页多少条")
    private Integer size = 10;

    @ApiModelProperty(value = "总条数")
    private Integer total = 0;

    @ApiModelProperty(value = "总页数")
    private Integer totalPage;

    @ApiModelProperty(value = "分页数据")
    private List<T> list;

    public Page() {
    }

    public Page(List<T> dataList, Integer allRow, Integer size, Integer currentPage) {
        this.total = allRow;
        this.size = size;
        this.currentPage = currentPage;
        this.list = dataList;
        if (allRow == 0) {
            this.totalPage = 1;
        } else if (allRow % size == 0) {
            this.totalPage = allRow / size;
        } else {
            this.totalPage = allRow / size + 1;
        }
    }

    public Page(Integer size) {
        this.size = size;
    }

    public void setAllRow(Integer allRow) {
        this.totalPage = (allRow + size - 1) / size;
        this.total = allRow;
    }

    public Integer getCurrentPage() {
        if (currentPage < 1) return 1;
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        if (currentPage != null) this.currentPage = currentPage;
    }

    public void setStrCurrentPage(String str) {
        try {
            this.currentPage = Integer.parseInt(str);
        } catch (Exception e) {
            this.currentPage = 1;
        }
        if (this.currentPage < 1) this.currentPage = 1;
    }

    public Integer getStart() {
        return this.getSize() * (this.getCurrentPage() - 1);
    }
}
