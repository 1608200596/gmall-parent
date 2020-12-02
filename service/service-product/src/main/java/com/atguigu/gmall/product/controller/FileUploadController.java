package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import org.apache.commons.io.FilenameUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author shkstart
 * @create 2020-12-01 23:10:55
 */
@RestController
@RequestMapping("admin/product/")
public class FileUploadController {
    /*
    * 1,读取trakcer.conf 文件
    * 2，创建一个trackerClient
    * 3，创建一个trackerServer
    * 4，创建一个StroageClient
    * 5，上传
    * 6，将上传的文件url 放到result
    * */
    //  文件上传的时候：注意文件服务器有可能会更改ip 地址。就不能将ip 写死在代码中，应该放在配置文件中：软编码。
    @Value("${fileServer.url}")
    private String fileServerUrl;  // fileServerUrl = http://192.168.200.128:8080/
    /*http://api.gmall.com/admin/product/fileUpload*/
    @PostMapping("fileUpload")
    public Result fileUpload(MultipartFile file) throws IOException, MyException{
        String configFile = this.getClass().getResource("/tracker.conf").getFile();

        String path = null;
        //判断
        if (configFile != null){
            //初始化
            ClientGlobal.init(configFile);
            //创建trackerClient
            TrackerClient trackerClient = new TrackerClient();
            //创建trackerServer
            TrackerServer connection = trackerClient.getConnection();
            //创建StroageClient
            StorageClient1 storageClient1 = new StorageClient1(connection,null);
            //上传
            //第一个参数表示上传文件的字节数组，第二个参数后缀名
            String originalFilename = file.getOriginalFilename();
            String extname = FilenameUtils.getExtension(originalFilename);
            //  获取上传之后的url group1/M00/00/01/wKjIgF9zVUOEAQ_2AAAAAPzxDAI115.png
            path =storageClient1.upload_appender_file1(file.getBytes(),extname,null);

            System.out.println(fileServerUrl+path);
        }
        return Result.ok(fileServerUrl+path);
    }
}
