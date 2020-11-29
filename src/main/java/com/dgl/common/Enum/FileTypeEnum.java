package cn.tzecc.common.Enum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 所有文件类型
 */
@ToString
public enum FileTypeEnum {

    NORMALFILE (1, "NormalFile"),
    CAROUSEL   (2, "Carousel"),
    USERHEAD   (3, "UserHead"),
    TITTLEFILE (4, "TittleFile"),
    NAVIGATOR  (5, "Navigator"),
    CONTENFILE (6, "ContenFile"),
    STYLETITTLE(7, "StyleTittleImage"),
    STYLEIMAGE1(8, "StyleImage1"),
    STYLEIMAGE2(9, "StyleImage2"),
    STYLEIMAGE3(10, "StyleImage3"),
    ;

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String fileType;

    FileTypeEnum(int code, String fileType) {
        this.code = code;
        this.fileType = fileType;
    }
    public static String getFileType(int code){
        String fileType="";
        switch (code){
            case 1:fileType= NORMALFILE .fileType; break;
            case 2:fileType= CAROUSEL   .fileType; break;
            case 3:fileType= USERHEAD   .fileType; break;
            case 4:fileType= TITTLEFILE .fileType; break;
            case 5:fileType= NAVIGATOR  .fileType; break;
            case 6:fileType= CONTENFILE .fileType; break;
            case 7:fileType= STYLETITTLE.fileType; break;
            case 8:fileType= STYLEIMAGE1.fileType; break;
            case 9:fileType= STYLEIMAGE2.fileType; break;
            case 10:fileType= STYLEIMAGE3.fileType; break;
        }
        return fileType;
    }
}
