package com.zhide.dtsystem.mapper;

import com.zhide.dtsystem.models.attachmentDetail;
import com.zhide.dtsystem.models.attachmentMain;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AttachmentMainMapper {
    @Select(value = "Select [ID],replace([Version],'.','') as Version,[Descript],[AttID],[CreateTime],[PublishTime]," +
            "[CanUse] from DTSystem.dbo.AttachmentMain")
    public List<attachmentMain> getList();

    @Select(value = "Select [ID],replace([Version],'.','') as Version,[Descript],[AttID],[CreateTime],[PublishTime]," +
            "[CanUse] from DTSystem.dbo.AttachmentMain  Where AttID=#{AttID}")
    public attachmentMain getbyAttID(String AttID);

    @Select(value = "Select * from DTSystem.dbo.AttachmentDetail Where MainID=#{MainID} And CompanyID=#{CompanyID}")
    public attachmentDetail getbyMainIDAndCompanyID(Integer MainID, String CompanyID);

    @Insert(value = "Insert into DTSystem.dbo.AttachmentDetail(mainId,companyId,downloadTime,updateTime,updateResult)" +
            " " +
            "values (#{mainId},#{companyId},#{downloadTime},#{updateTime},#{updateResult})")
    public int addDetail(attachmentDetail one);

    @Update(value = "Update DTSystem.dbo.AttachmentDetail Set UpdateTime=#{updateTime},UpdateResult=#{updateResult} " +
            "Where ID=#{id}")
    public int updateDetail(attachmentDetail one);
}
