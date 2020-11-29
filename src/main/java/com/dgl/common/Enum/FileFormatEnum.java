package cn.tzecc.common.Enum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 所有支持上传的文件后缀
 */
@ToString
public enum FileFormatEnum {

    image1(0, "jpg"),
    image2(1, "jpeg"),
    image3(2, "png"),
    image4(3, "gif"),
    file1(4, "doc"),
    file2(5, "docx"),
    file3(6, "pdf"),
    file4(7, "txt"),
    file5(8, "xlsx"),
    file6(9, "xls")
    ;
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String format;

    FileFormatEnum(int code, String format) {
        this.code = code;
        this.format = format;
    }
}
