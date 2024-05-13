package com.ruoyi.project.file.mapper;

import com.ruoyi.project.file.domain.FileRecord;

import java.util.List;

/**
 * @author maijunxuan
 * @date 2024-05-06
 */
public interface FileRecordMapper {

    List<FileRecord> selectFileRecordList(FileRecord fileRecord);


    int insertFileRecord(FileRecord fileRecord);
}
