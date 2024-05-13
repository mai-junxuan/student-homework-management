package com.ruoyi.project.file.service;

import com.ruoyi.project.file.domain.FileRecord;
import com.ruoyi.project.system.domain.SysNotice;

import java.util.List;

/**
 * 公告 服务层
 *
 * @author ruoyi
 */
public interface FileRecordService {

    List<FileRecord> selectFileRecordList(FileRecord fileRecord);


    int insertFileRecord(FileRecord fileRecord);

}
