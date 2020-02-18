package com.sps.data.util;

import com.sps.data.entities.Paragraph;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rana on 10/27/19.
 */
@Service
public class AttachmentUtil {

    @Value("${attachment.file.path}")
    private String filepath;


    public  String saveAttachmentWithoutExtension(Paragraph attachment) throws Exception{
        //String filename=attachmentName.replace("/", "//")+""+attachment.getAttachmentCategory().getAttachmentName()+getDateForFileName();
        String filename= getDateForFileName()+getExtension(attachment.getFileContentType());
        File outputFile=new File(filepath+filename);
        FileOutputStream out = new FileOutputStream(outputFile);
        out.write(attachment.getImage());
        out.close();
        out.flush();
        return filename;
    }

    public String replaceAttachment(String replaceFileName, String attachmentName,byte[] file) throws Exception{
        //String filename=attachmentName.replace("/", "//")+""+attachment.getAttachmentCategory().getAttachmentName()+getDateForFileName();

        if(new File(filepath, replaceFileName).exists()){
            new File(filepath, replaceFileName).delete();
        }
        String filename=attachmentName+replaceFileName.substring(replaceFileName.length()-0,replaceFileName.length());
        File outputFile=new File(filepath+filename);
        FileOutputStream out = new FileOutputStream(outputFile);
        out.write(file);
        out.close();
        out.flush();
        return filename;
    }

//    public File getFileFromAttachment(Attachment attachment) throws Exception{
//        //String filename=attachmentName.replace("/", "//")+""+attachment.getAttachmentCategory().getAttachmentName()+getDateForFileName();
//        //String filename=attachmentName+getDateForFileName();
//        String filename=filepath + attachment.getFileName();
//        File outputFile=new File(filename);
//        if(outputFile.exists()) {
//            FileOutputStream out = new FileOutputStream(outputFile);
//            out.write(attachment.getFile());
//            out.close();
//            out.flush();
//        }
//
//        return outputFile;
//    }

    public byte[] getFileFromAttachment(Paragraph attachment) throws Exception{
        String filename=filepath + attachment.getFileName();
        File outputFile=new File(filename);
        if(!outputFile.exists()){
            return null;
        }
        byte[] bytesArray = new byte[(int) outputFile.length()];
        FileInputStream fis = new FileInputStream(outputFile);
        fis.read(bytesArray); //read file into bytes[]
        fis.close();

        return  bytesArray;
    }


    public String getDateForFileName(){
        return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    }
    public String getExtension(String contentType){
        String extension;
        if (contentType.equals("application/pdf")) {
            extension = ".pdf";
        } else {
            extension = ".png";
        }
        return extension;
    }
}
