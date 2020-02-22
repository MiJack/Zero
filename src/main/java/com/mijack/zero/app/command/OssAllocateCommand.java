package com.mijack.zero.app.command;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Mi&amp;Jack
 */
@Data
public class OssAllocateCommand implements Serializable {
    private static final long serialVersionUID = 8888871808078032567L;
    private String ossKey;
    private String contentType;
    private String contentMD5;
    private Long contentLength;
    private Integer overwriteStrategy;
}
