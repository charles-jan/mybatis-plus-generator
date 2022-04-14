package com.baomidou.mybatisplus.generator.samples;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;

public class CustomerGeneratorTest {

    public static void main(String[] args) {
        final DataSourceConfig.Builder customerDbConfig = new DataSourceConfig.Builder("jdbc:mysql://rm-bp1g80jq8nh0q629hco.mysql.rds.aliyuncs.com/bczstaff", "fin_staging", "Fintest_2021");
        final DataSourceConfig.Builder misDbConfig = new DataSourceConfig.Builder("jdbc:mysql://rm-bp19n231z8k5pacn92o.mysql.rds.aliyuncs.com/bcz_staff_dev", "xiazijian", "xiazijian2020");


        FastAutoGenerator.create(misDbConfig)
            .globalConfig(builder -> {
                builder.author("xiazijian")
                    .fileOverride()
                    .outputDir("/Users/xiazijian/Work/mis-financial/src/main/java");
            })
            .strategyConfig(builder -> {
                builder.likeTable(new LikeTable("contract", SqlLike.RIGHT))
                    .entityBuilder()
                    .enableLombok()
                    .naming(NamingStrategy.active_record_pattern)
                    .columnNaming(NamingStrategy.underline_to_camel)
                    .addTableFills(new Column("created_at", FieldFill.INSERT),
                        new Column("updated_at", FieldFill.INSERT_UPDATE));
            })
            .packageConfig(builder -> {
                builder.parent("com.baicizhan.cyamis.financial.module")
                    .moduleName("contract")
                    .entity("model");
            })
            .templateConfig(builder -> {
                builder.disable(TemplateType.XML, TemplateType.SERVICE, TemplateType.SERVICEIMPL, TemplateType.CONTROLLER);
            })
            .execute();


    }
}
