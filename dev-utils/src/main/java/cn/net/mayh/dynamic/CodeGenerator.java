package cn.net.mayh.dynamic;

import cn.net.mayh.exception.BizException;
import cn.net.mayh.exception.ExceptionEnum;
import cn.net.mayh.util.JSONUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author mayh
 * @version : 1.0
 * @date 2020/12/27
 **/
@Data
@Slf4j
public class CodeGenerator {
    public static void gen() {
        List<TableInfo> tableInfos = loadTableInitInfo();
        tableInfos.forEach(CodeGenerator::genFile);
    }

    public static List<TableInfo> loadTableInitInfo() {
        Resource resource = new ClassPathResource("info/tableInfo.json");
        return JSONUtils.parseArray(resource, TableInfo.class);
    }

    public static void genFile(TableInfo tableInfo) {
        //初始化framework配置信息
        Resource resource = new ClassPathResource("freemarker");
        Configuration conf = new Configuration(Configuration.VERSION_2_3_28);
        //加载模板文件(模板的路径)
        try {
            conf.setDirectoryForTemplateLoading(resource.getFile());
        } catch (IOException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR, "文件路径不正确！", e);
        }
        conf.setDefaultEncoding("UTF-8");
        conf.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        conf.setLogTemplateExceptions(false);
        conf.setWrapUncheckedExceptions(true);
        //加载表信息
        TableInfoLoader.loadTableInfo(tableInfo);
        //生成对象的信息
        generator(conf, tableInfo, "mapper", "mapper-pfrs-" + tableInfo.getTableName() + "_mapper.xml");
        List<ColumnInfo> columnInfoList = tableInfo.getColumnInfoList();
        tableInfo.setColumnInfoList(columnInfoList.stream().filter(CodeGenerator::isBaseColumn).collect(Collectors.toList()));
        generator(conf, tableInfo, "po", tableInfo.getBeanName() + "PO.java");
        generator(conf, tableInfo, "qo", tableInfo.getBeanName() + "QO.java");
        generator(conf, tableInfo, "dto", tableInfo.getBeanName() + "Dto.java");
        generator(conf, tableInfo, "dao", "I" + tableInfo.getBeanName() + "Dao.java");
        generator(conf, tableInfo, "service", "I" + tableInfo.getBeanName() + "Service.java");
        generator(conf, tableInfo, "serviceImpl", tableInfo.getBeanName() + "ServiceImpl.java");
        generator(conf, tableInfo, "controller", tableInfo.getBeanName() + "Controller.java");
        generator(conf, tableInfo, "test", tableInfo.getBeanName() + "ControllerTest.java");
    }

    private static boolean isBaseColumn(ColumnInfo columnInfo){
        String columnName = columnInfo.getColumnName();
        boolean isBaseColumn = "id".equals(columnName)
                || "lcu".equals(columnName)
                || "del_flag".equals(columnName)
                || "lcd".equals(columnName)
                || "fcu".equals(columnName)
                || "fcd".equals(columnName);
        return !isBaseColumn;

    }
    private static void generator(Configuration conf, TableInfo tableInfo, String name, String fileName) {
        // 加载模板
        Template template = null;
        try {
            template = conf.getTemplate("/" + name + ".ftl");
            Map root = new HashMap();
            root.put("tableInfo", tableInfo);
            // 定义输出
            String packge = getPackge(tableInfo,name);
            log.info("生成文件：{}",packge + fileName);
            File file = new File(packge + fileName);
            /*Path path = new ClassPathResource("/").getFile().toPath().resolve();
            File file = path.toFile();*/
            File parentFile = new File(packge);
            if(!parentFile.exists()){
                parentFile.mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            Writer dest = new FileWriter(file);
            //处理并生成文件
            template.process(root, dest);
        } catch (IOException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR, "文件路径不正确！", e);
        } catch (TemplateException e) {
            throw BizException.builBizException(ExceptionEnum.PARAMETER_ERROR, "处理模板失败！", e);
        }
    }

    private static String getPackge(TableInfo tableInfo,String name) {
        String packge = null;
        switch (name){
            case "controller":packge = tableInfo.getPath()+ "/rest/";break;
            case "test": packge =  tableInfo.getPath().replaceAll("main", "test")+"/rest/";break;
            case "dao": packge =  tableInfo.getPath()+"/dao/";break;
            case "dto": packge =  tableInfo.getPath()+"/dto/";break;
            case "mapper": {
                String path = tableInfo.getPath();
                int i = path.indexOf("\\java\\");
                if(i == -1){
                    i = path.indexOf("/java/");
                }
                packge = path.substring(0,i) + "/resources/mapper/";
                break;
            }
            case "po": packge =  tableInfo.getPath()+"/po/";break;
            case "qo": packge =  tableInfo.getPath()+"/qo/";break;
            case "service": packge =  tableInfo.getPath()+"/service/";break;
            case "serviceImpl": packge = tableInfo.getPath()+ "/service/impl/";break;
            default:packge = tableInfo.getPath()+ "/";
        }
        return packge;
    }

}
