package com.neu.demo.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
public class UpFile {


    @RequestMapping(value = "/upFile",method = RequestMethod.POST)
    public void up(@RequestParam("file")MultipartFile file){
        if (file.isEmpty()){
            System.out.println("上传的内容为空");
            return;
        }
        try {
//        真实的文件名
            String originalFilename = file.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
//        后缀名
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            String path = "D:\\First\\";
            File endFile = new File(path+(uuid+substring));
            file.transferTo(endFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/downLoad")
    public void downLoad( HttpServletResponse response) {
        FileInputStream fis = null;
        ServletOutputStream outputStream = null;
        try {
            String fileName = "D:\\测试照片\\1.jpg";
            fis = new FileInputStream(fileName);
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);
            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];

            int length;
            while ( (length = fis.read(bytes)) != -1 ){
                outputStream.write(bytes,0,length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null ){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
