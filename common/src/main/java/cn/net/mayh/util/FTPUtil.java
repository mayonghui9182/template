package cn.net.mayh.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.SocketException;

/**
 * <li>创建人：zhaoruilin
 * <li>创建时间：2019/1/14 14:13
 * <li>创建目的：【】
 * <li>修改目的：【修改人：修改目的，修改时间】
 * <li>参数列表：
 * <li>返回值类型：
 **/
@Slf4j
public class FTPUtil {
/*
    public static FTPClient getFTPClient(String ftpAddress,int ftpHost,String ftpUserName,String ftpPassword){
        FTPClient ftpClient = new FTPClient();
        try {
            //设置传输超时时间60秒
            ftpClient.setDataTimeout(60000);
            //连接服务器
            ftpClient.setConnectTimeout(60000);
            ftpClient.connect(ftpAddress,ftpHost);
            //登陆服务器
            ftpClient.login(ftpUserName,ftpPassword);
            if(!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                log.info("未连接到ftp，用户名或密码错误");
                ftpClient.disconnect();
            }else{
                log.info("ftp连接成功");
            }
        } catch (SocketException e) {
            log.info("FTP服务器ip地址有误，请正确配置。");
            e.printStackTrace();
        }catch (IOException e){
            log.info("FTP端口错误，请正确配置。");
            e.printStackTrace();
        }

        return ftpClient;
    }*/


//    public static FTPClient connection(){
//        String ftpAddress = systemConfig.getSealFtpIp();
//        String ftpPort = systemConfig.getSealFtpPort();
//        String user = systemConfig.getSealFtpUser();
//        String pwd = systemConfig.getSealFtpPwd();
//        return getFTPClient(ftpAddress, Integer.parseInt(ftpPort),user,pwd);
//    }


//    public static void zipDownloadFile(OutputStream out, String zipname, List<String> ftpFilePathList){
//        //连接ftp服务器
//        FTPClient ftp = connection();
//        byte[] buf = new byte[1024];
//        try {
//            ftp.setControlEncoding("utf-8");
//            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
//            ftp.enterLocalPassiveMode();
//            //根目录下创建临时文件
//            ftp.changeWorkingDirectory(systemConfig.getSealRoot());
//            File zipFile = new File("电子签章报告");
//            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
//            for(int i=0;i<ftpFilePathList.size();i++){
//                ZipEntry entry = new ZipEntry((i+1)+"_"+ftpFilePathList.get(i));
//                zipOut.putNextEntry(entry);
//                InputStream ins = ftp.retrieveFileStream(ftpFilePathList.get(i));
//                if(ins != null){
//                    int readLen = -1;
//                    while ((readLen = ins.read(buf,0,1024)) != -1){
//                        zipOut.write(buf,0,readLen);
//                    }
//                    zipOut.closeEntry();
//                    ins.close();
//                    ftp.completePendingCommand();
//                }
//            }
//            zipOut.close();
//            ftp.logout();
//            //下载
//            int len;
//            FileInputStream zipInput = new FileInputStream(zipFile);
//            while((len = zipInput.read(buf)) != -1){
//                out.write(buf,0,len);
//            }
//            zipInput.close();
//            out.flush();
//            out.close();
//            //删除压缩包
//            zipFile.delete();
//        } catch (IOException e) {
//            log.info("文件打包下载出错：" + e.getLocalizedMessage());
//            e.printStackTrace();
//        }
//
//    }
}
