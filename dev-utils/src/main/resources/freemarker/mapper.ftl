<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE mapper PUBLIC
        "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${tableInfo.packageName}.dao.I${tableInfo.beanName}Dao">
    <resultMap id="${tableInfo.beanNameOfLowerFirst}${tableInfo.RESULT}" type="${tableInfo.packageName}.po.${tableInfo.beanName}${tableInfo.PO}">
        <#list tableInfo.columnInfoList as column>
            <result property="${column.fieldName}" column="${column.columnName}"/>
        </#list>
    </resultMap>

    <sql id="sql_query_column_list">
        <#list tableInfo.columnInfoList as column>
            ,a.${column.columnName} as ${column.fieldName}
        </#list>
    </sql>

    <sql id="sql_insert_column_list">
        <trim prefix="" prefixOverrides=",">
            <#list tableInfo.columnInfoList as column>
                ,${column.columnName}
            </#list>
        </trim>
    </sql>

    <sql id="sql_insert_property_list">
        <trim prefix="" prefixOverrides=",">
            <#list tableInfo.columnInfoList as column>
                <choose>
                    <#if 'String' == column.javaType>
                    <when test="@cn.net.mayh.util.MyStringUtils@isNotBlank(${column.fieldName})">
                    <#else>
                    <when test="${column.fieldName} != null">
                    </#if>
                        ,${"#"}{${column.fieldName}}
                    </when>
                    <otherwise>
                        ,null
                    </otherwise>
                </choose>
            </#list>
        </trim>
    </sql>

    <sql id="sql_update_all_list">
        <set>
            <#list tableInfo.columnInfoList as column>
                ,${column.columnName} = ${"#"}{${column.fieldName}}
            </#list>
        </set>
    </sql>


    <sql id="sql_update_selective_list">
        <set>
            <#list tableInfo.columnInfoList as column>
                <#if 'String' == column.javaType>
                    <if test="@cn.net.mayh.util.MyStringUtils@isNotBlank(po.${column.fieldName})">
                <#else>
                    <if test="po.${column.fieldName} != null">
                </#if>
                    ,${column.columnName} = ${"#"}{po.${column.fieldName}}
                </if>
            </#list>
        </set>
    </sql>


    <sql id="sql_condition">
        <#list tableInfo.columnInfoList as column>
            <#if 'String' == column.javaType>
                <if test="@cn.net.mayh.util.MyStringUtils@isNotBlank(qo.${column.fieldName})">
            <#else>
                <if test="qo.${column.fieldName} != null">
            </#if>
                AND a.${column.columnName} = ${"#"}{qo.${column.fieldName}}
            </if>
        </#list>
    </sql>

    <select id="getById" resultType="${tableInfo.packageName}.po.${tableInfo.beanName}${tableInfo.PO}">
        select
        <trim prefix="" prefixOverrides=",">
            <include refid="sql_query_column_list"/>
        </trim>
        from ${tableInfo.tableName} a
        where id = ${"#"}{id}
        <#--and a.del_flag = ${"#"}{@cn.net.mayh.Constants@DEL_FLAG_NORMAL}-->
    </select>

    <select id="get" resultType="${tableInfo.packageName}.po.${tableInfo.beanName}${tableInfo.PO}">
        select
        <trim prefix="" prefixOverrides=",">
            <include refid="sql_query_column_list"/>
        </trim>
        from ${tableInfo.tableName} a
        <where>
            <include refid="sql_condition"/>
        </where>
    </select>


    <select id="findList" resultType="${tableInfo.packageName}.po.${tableInfo.beanName}${tableInfo.PO}">
        select
        <trim prefix="" prefixOverrides=",">
            <include refid="sql_query_column_list"/>
        </trim>
        from ${tableInfo.tableName} a
        <where>
            <include refid="sql_condition"/>
            and a.del_flag = ${"#"}{@cn.net.mayh.Constants@DEL_FLAG_NORMAL}
        </where>
    </select>

    <select id="findAllList" resultType="${tableInfo.packageName}.po.${tableInfo.beanName}${tableInfo.PO}">
        select
        <trim prefix="" prefixOverrides=",">
            <include refid="sql_query_column_list"/>
        </trim>
        from ${tableInfo.tableName} a
        <where>
            <include refid="sql_condition"/>
        </where>
    </select>

    <insert id="insert">
        INSERT INTO ${tableInfo.tableName} (
        <include refid="sql_insert_column_list"/>
        ) VALUES (
        <include refid="sql_insert_property_list"/>
        )
    </insert>

    <insert id="insertBatch">
        insert into ${tableInfo.tableName} (
        <include refid="sql_insert_column_list"/>
        ) VALUES
        <foreach collection="poList" item="qo" index="index" separator=",">
            (
            <include refid="sql_insert_property_list"/>
            )
        </foreach>
    </insert>


    <update id="updateByPk">
        UPDATE ${tableInfo.tableName} a
        <include refid="sql_update_selective_list"/>
        where id = po.${"#"}{id}
    </update>


    <update id="update">
        UPDATE ${tableInfo.tableName} a
        <include refid="sql_update_selective_list"/>
        <where>
            <include refid="sql_condition"/>
        </where>
    </update>

    <update id="updateNullAbleById">
        UPDATE ${tableInfo.tableName} a
        <include refid="sql_update_all_list"/>
        where id = po.${"#"}{id}
    </update>


    <update id="updateNullAble">
        UPDATE ${tableInfo.tableName} a
        <include refid="sql_update_all_list"/>
        <where>
            <include refid="sql_condition"/>
        </where>
    </update>



    <delete id="deleteLogincById">
        update ${tableInfo.tableName} a
        set del_flag = ${"#"}{@cn.net.mayh.Constants@DEL_FLAG_DELETE}
        where id = ${"#"}{id}
    </delete>

    <delete id="deleteLoginc">
        update ${tableInfo.tableName} a
        set del_flag = ${"#"}{@cn.net.mayh.Constants@DEL_FLAG_DELETE}
        <where>
            <include refid="sql_condition"/>
        </where>
    </delete>

</mapper>
