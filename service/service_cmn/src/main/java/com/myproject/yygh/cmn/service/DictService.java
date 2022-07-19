package com.myproject.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myproject.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    //根据数据id查询子数据列表
    List<Dict> findChildData(Long id);

    //导出数据字典接口
    void exportDictData(HttpServletResponse response);

    //导入数据字典
    void importDictData(MultipartFile file);
}
