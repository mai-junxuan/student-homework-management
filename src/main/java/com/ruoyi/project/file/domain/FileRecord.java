package com.ruoyi.project.file.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author maijunxuan
 * @date 2024-05-06
 */
@Data
public class FileRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 上传时间
     */
    private Long uploadTime;

    /**
     * 班级ID
     */
    private Long classId;

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;
}
