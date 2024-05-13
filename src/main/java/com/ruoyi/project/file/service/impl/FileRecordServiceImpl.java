package com.ruoyi.project.file.service.impl;

import com.ruoyi.project.file.domain.FileRecord;
import com.ruoyi.project.file.mapper.FileRecordMapper;
import com.ruoyi.project.file.service.FileRecordService;
import com.ruoyi.project.system.domain.SysNotice;
import com.ruoyi.project.system.mapper.SysNoticeMapper;
import com.ruoyi.project.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class FileRecordServiceImpl implements FileRecordService {
    @Autowired
    private FileRecordMapper fileRecordMapper;

    @Override
    public List<FileRecord> selectFileRecordList(FileRecord fileRecord) {
        List<FileRecord> fileRecords = fileRecordMapper.selectFileRecordList(fileRecord);
        return fileRecords;
    }

    @Override
    public int insertFileRecord(FileRecord fileRecord) {
        return fileRecordMapper.insertFileRecord(fileRecord);
    }
}
