package com.ruoyi.project.file.controller;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.security.service.TokenService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.file.domain.FileRecord;
import com.ruoyi.project.file.domain.FileRecordVo;
import com.ruoyi.project.file.service.FileRecordService;
import com.ruoyi.project.system.domain.SysNotice;
import com.ruoyi.project.system.service.ISysNoticeService;
import com.ruoyi.project.system.service.ISysUserService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class FileRecordController extends BaseController {
    @Autowired
    private FileRecordService fileRecordService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ServerConfig serverConfig;

    @GetMapping("/list")
    public TableDataInfo list(FileRecord fileRecord) {
        startPage();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        boolean admin = SecurityUtils.isAdmin(loginUser.getUserId());
        if (!admin) {
            fileRecord.setStudentId(loginUser.getUserId());
        }
        List<FileRecord> list = fileRecordService.selectFileRecordList(fileRecord);
        List<FileRecordVo> fileRecordList = list.stream()
                .map(file -> {
                    FileRecordVo fileRecordVo = new FileRecordVo();
                    BeanUtils.copyProperties(file, fileRecordVo);
                    fileRecordVo.setUploadTime(DateFormatUtils.format(file.getUploadTime(), "yyyy-MM-dd HH:mm:ss"));
                    return fileRecordVo;
                })
                .collect(Collectors.toList());
        return getDataTable(fileRecordList);
    }

    @Log(title = "上传文件", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public AjaxResult add(MultipartFile file) throws IOException {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Long classId = loginUser.getUser().getDeptId();
        Long studentId = loginUser.getUser().getUserId();
        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.toLowerCase().endsWith(".doc")
                && !originalFilename.toLowerCase().endsWith(".docx")
                && !originalFilename.toLowerCase().endsWith(".ppt")
                && !originalFilename.toLowerCase().endsWith(".pptx")
                && !originalFilename.toLowerCase().endsWith(".xls")
                && !originalFilename.toLowerCase().endsWith(".xlsx")
        ) {
            return AjaxResult.error("文件类型不正确");
        }
        // 上传文件路径
        String filePath = RuoYiConfig.getUploadPath();
        // 上传并返回新文件名称
        String fileName = FileUploadUtils.upload(filePath, file);
        String url = serverConfig.getUrl() + fileName;
        AjaxResult ajax = AjaxResult.success();
        ajax.put("url", url);
        ajax.put("fileName", fileName);
        ajax.put("newFileName", FileUtils.getName(fileName));
        ajax.put("originalFilename", file.getOriginalFilename());
        FileRecord fileRecord = new FileRecord();
        fileRecord.setFileName(file.getOriginalFilename());
        fileRecord.setFilePath(url);
        fileRecord.setUploadTime(System.currentTimeMillis());
        fileRecord.setClassId(classId);
        fileRecord.setStudentId(studentId);

        fileRecordService.insertFileRecord(fileRecord);
        return ajax;
    }
}
