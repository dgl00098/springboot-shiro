package cn.tzecc.common.Enum;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

public enum EnumReqOrderBy {
    DATE_ASC(1, "时间升序"),
    DATE_DESC(2, "时间降序"),
    PRICE_ASC(3, "价格升序"),
    PRICE_DESC(4, "价格降序");

    @Getter
    @Setter
     int type;
    @Getter
    @Setter
     String msg;

    EnumReqOrderBy(int code, String msg) {
        this.type = code;
        this.msg = msg;
    }

    public static EnumReqOrderBy getType(int type) {
        EnumReqOrderBy[] types = values();
        for (EnumReqOrderBy type1 : types) {
            if (type==type1.getType()) {
                return type1;
            }
        }
        return null;
    }

    public  static Sort getContractSort(int type){
        EnumReqOrderBy enumReqOrderBy=EnumReqOrderBy.getType(type);
        Sort.Direction tmp= Sort.Direction.ASC;
        String sortStr="updateTime";
        //参数1:排序方式  参数2:排序字段
        Sort sort = Sort.by(tmp, sortStr);
        //如果是null默认按照更新时间升序排列,否则按照传入的参数进行排列
        if(enumReqOrderBy==null){
            return sort;
        }
        switch (enumReqOrderBy){
            case DATE_ASC:
                tmp= Sort.Direction.ASC;
                sortStr="updateTime";
                break;
            case DATE_DESC:
                tmp= Sort.Direction.DESC;
                sortStr="updateTime";
                break;
            case PRICE_ASC:
                tmp= Sort.Direction.ASC;
                sortStr="listedPrice";
                break;
            case PRICE_DESC:
                tmp= Sort.Direction.DESC;
                sortStr="listedPrice";
                break;
        }
        sort = Sort.by(tmp, sortStr);
        return  sort;
    }




}
